import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class GestorPassageiro {
    //1 - Registar-se como passageiro
    public String adicionarPassageiro() throws IOException {
        HashMap<String, Passageiro> dicPassageiroId = this.lerPassageiroTxt("passageiro.txt");
        String idPassageiro = "", nome, profissao, morada, op, op2 = null;
        int anoNascimento, mesNascimento, diaNascimento, idRota = 0;
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        do { //eu quero que ele peça o NIF e este tem obrigatoriamente de ter 9 números
            System.out.print("\nQual é idPassageiro? Tem de inserir 9 números: ");
            idPassageiro = sc.next();
        } while (idPassageiro.length() != 9);
        if (dicPassageiroId.containsKey(idPassageiro)) {
            System.out.println("O id do Passageiro já existe!");
            this.adicionarPassageiro();
        }else{
            System.out.print("Qual o nome? ");
            nome = sc.next();
            System.out.print("Qual é a profissão? ");
            profissao = sc.next();
            System.out.print("Qual é a morada? ");
            morada = sc.next();
            System.out.print("Qual é o ano de nascimento? ");
            anoNascimento = sc1.nextInt();
            System.out.print("Qual é o mês de nascimento (número)? ");
            mesNascimento = sc1.nextInt();
            System.out.print("Qual é a dia de nascimento? ");
            diaNascimento = sc1.nextInt();
            Passageiro A = new Passageiro(idPassageiro, nome, profissao, morada, anoNascimento, mesNascimento, diaNascimento);
            criarPassageiro(A);
        }
        return idPassageiro;
    }

    public void criarPassageiro(Passageiro Passageiro) throws IOException { //esta função vai escrever no txt do passageiro
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("passageiro.txt", true));
        String linha = "\n" + Passageiro.getIdPassageiro() + "," + Passageiro.getNome() + "," + Passageiro.getProfissao() + "," + Passageiro.getMorada() + "," +
                Passageiro.getAno() + "," + Passageiro.getMes() + "," + Passageiro.getDia();
        buffWrite.append(linha);
        buffWrite.close();
    }


    //2 - Comprar um bilhete efetivo - não havendo vaga será um bilhete suplente (só há 4 bilhetes suplentes em cada voo);
    public void comprarBilheteEfetivo(String idPassageiro) throws IOException {
        Scanner sc = new Scanner(System.in);
        LocalDate VerificarData;
        Bilhete bilhete = null;
        Rota rota = escolherRota();                 //vai à função para escolher uma rota
        Voo voo = escolherVoo(rota.getIdRota());    //dependendo da rota vai dizer quais os voos que existem relacionados com a mesma
        int tipoBilhete = 0;                        //vai ver a função onde vai indicar se é suplente, efetivo ou se não existe (switch abaixo)
        double preco = 0;
        int ano, mes, dia = 0;
        boolean diaEncontrado = false;
        String diaSemana;
        preco = calculoPrecoBilhete(rota, 1, 300, 1000, 25, 50);
        //A data do voo é pedida ao utilizador, onde verificamos posteriormente se o mesmo pertence ao dia de semana do voo
        System.out.print("\nAno da viagem: ");
        ano = sc.nextInt();
        System.out.print("Mês da viagem (número): ");
        mes = sc.nextInt();
        diaSemana = voo.getDiaSemana().toUpperCase(Locale.ROOT);
        while (!diaEncontrado) {
            System.out.print("Dia da viagem: ");
            dia = sc.nextInt();
            VerificarData = LocalDate.of(ano, mes, dia);
            diaEncontrado = diaSemana.equals(VerificarData.getDayOfWeek().name());
            if (!diaEncontrado) {
                System.out.println("Tem de selecionar um dia que corresponda a " + diaSemana);
            } else {
                tipoBilhete = tipoBilhete(voo, rota, ano, mes, dia);
                if (tipoBilhete == 0) {
                    System.out.println("\nNão existem bilhetes disponíveis para o dia pretendido!");
                    diaEncontrado = false;
                } else {
                    LocalDateTime data = LocalDateTime.now();
                    bilhete = new Bilhete(idPassageiro, rota.getIdRota(), voo.getIdVoo(), ano, mes, dia, voo.getHora(), voo.getMinuto(), voo.getSegundo(), data.getYear(), data.getMonth().getValue(), data.getDayOfMonth(), data.getHour(), data.getMinute(), data.getSecond(), preco, tipoBilhete);
                    criarBilhete(bilhete);
                    if (tipoBilhete == 1) {
                        System.out.println("\nFoi comprado um bilhete efetivo.");
                    }
                    if (tipoBilhete == 2) {
                        System.out.println("\nFoi comprado um bilhete suplente.");
                    }
                }
            }
        }
    }

    public Rota escolherRota() throws IOException { //mostra todas as rotas para escolher uma que esteja válida, ex. Lisboa, Leiria, Porto, etc
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Rota> dicRotas = lerRotasTxt("rotas.txt");
        boolean encontrado = false;
        int rota = 0;
        while (!encontrado) {
            System.out.println("\nEscolha uma das seguintes rotas:");
            mostrarRota(dicRotas);
            System.out.print("\nEscolha uma opção: ");
            rota = sc.nextInt();
            encontrado = dicRotas.containsKey(rota); //verifica se a rota escolhida existe
        }
        return dicRotas.get(rota);
    }

    public Voo escolherVoo(int idRota) throws IOException { //após escolher a rota, aparecem os voos programados para essa rota, e tem de se escolher uma
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Voo> dicVoo = lerVoosPorRota(idRota, "voos.txt");
        boolean encontrado = false;
        int voo = 0;
        while (!encontrado) {
            System.out.println("\nEscolha um dos seguintes voos:");
            mostrarVoo(dicVoo);
            System.out.print("\nEscolha uma opção: ");
            voo = sc.nextInt();
            encontrado = dicVoo.containsKey(voo);
        }
        return dicVoo.get(voo);
    }

    public int tipoBilhete(Voo Voo, Rota Rota, int ano, int mes, int dia) throws IOException { //verifica se o bilhete é ou não efetivo
        int maxBilhetes = lerMaxBilhetes(Voo.getMarcaAviao());
        HashMap<Integer, Bilhete> dicBilhetesEfetivos = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 1, Rota.getIdRota(), ano, mes, dia);
        int bilhetesEfetivos = dicBilhetesEfetivos.size();
        HashMap<Integer, Bilhete> dicBilhetesSuplentes = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 2, Rota.getIdRota(), ano, mes, dia);
        int bilhetesSuplentes = dicBilhetesSuplentes.size();
        if (bilhetesEfetivos < maxBilhetes) {
            return 1;
        } else {
            if (bilhetesSuplentes < 4) { //0 é menor que 4, cria o primeiro, etc etc
                return 2;
            }
        }
        return 0;
    }

    public int lerMaxBilhetes(String aviao) throws IOException { //diz qual o máximo de bilhetes efetivos por avião
        switch (aviao) {
            case "Bombardier Challenger 350":
                return 10;
            case "Cessna Citation Latitude":
                return 12;
            case "Embraer Phenom 300":
                return 9;
            case "Bombardier Global 6000":
                return 17;
        }
        return 0;
    }

    public double calculoPrecoBilhete(Rota Rota, double precoKM, double primeiroPrecoDesconto, double segundoPrecoDesconto, double primeiroDescontoPercentagem, double segundoDescontoPercentagem) throws IOException {
        //verifica os Km
        double Preco = Rota.getDistanciaKm() * precoKM;
        if (Preco > segundoPrecoDesconto)                       //se o preço for maior que o segundo desconto então aplica os 50% de desconto
            return Preco * (segundoDescontoPercentagem / 100);
        if (Preco > primeiroPrecoDesconto)                      //se o preço for maior que o primeiro desconto então aplica os 25% de desconto
            return Preco * (primeiroDescontoPercentagem / 100);
        return Preco;                                           // senão ele vai dar o preço que é 1€ (neste caso em concreto, pois pode ser alterado o preço por km) por km
    }

    public void criarBilhete(Bilhete Bilhete) throws IOException { //esta função vai escrever no txt do bilhete, efetivo (1) ou suplente (2)
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("bilhetes.txt", true));
        String linha = "\n" + Bilhete.getIdPassageiro() + "," + Bilhete.getIdRota() + "," + Bilhete.getIdVoo() + "," + Bilhete.getAnoViagem() + "," + Bilhete.getMesViagem() + "," +
                Bilhete.getDiaViagem() + "," + Bilhete.getHoraViagem() + "," + Bilhete.getMinutoViagem() + "," + Bilhete.getSegundoViagem() + "," + Bilhete.getAnoAquisicao() + "," +
                Bilhete.getMesAquisicao() + "," + Bilhete.getDiaAquisicao() + "," + Bilhete.getHoraAquisicao() + "," + Bilhete.getMinutoAquisicao() + "," + Bilhete.getSegundoAquisicao() + "," +
                Bilhete.getPreco() + "," + Bilhete.getTipoBilhete();
        buffWrite.append(linha);
        buffWrite.close();
    }


    //3 - Cancelar um bilhete efetivo e promover automaticamente um bilhete suplente a bilhete efetivo;
    public void cancelarBilheteEfetivo(String idPassageiro) throws IOException {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer,Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente("bilhetes.txt",idPassageiro,1);
        int cancelar;
        if (dicBilhete.isEmpty()) {
            System.out.println("\nO passageiro " + idPassageiro + " não tem bilhetes para cancelar.");
        } else {
            System.out.println("\nBilhetes que podem ser cancelados:");
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println("\nBilhete: " + bilhete.getKey() + "\nId Rota: " + bilhete.getValue().getIdRota() + "\nId Voo: " + bilhete.getValue().getIdVoo() +
                        "\nData do Voo: " + bilhete.getValue().getDiaViagem() + "/" + bilhete.getValue().getMesViagem() + "/" + bilhete.getValue().getAnoViagem() +
                        "\nHora do Voo: " + bilhete.getValue().getHoraViagem() + "h " + bilhete.getValue().getMinutoViagem() + "m " + bilhete.getValue().getSegundoViagem() + "s");
            }
            System.out.print("\nEscolha o bilhete que pretende apagar: ");
            cancelar = sc.nextInt();
            fazerCancelamento(dicBilhete.get(cancelar),true);
        }
    }


    //4 - Cancelar um bilhete suplente
    public void cancelarBilheteSuplente(String idPassageiro) throws IOException {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer,Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente("bilhetes.txt",idPassageiro,2);//buscar os bilhetes suplentes do passageiro por acontecer
        Bilhete cancelar = null;
        if (dicBilhete.isEmpty()) {
            System.out.println("\nO passageiro " + idPassageiro + " não tem bilhetes para cancelar.");
        } else {
            System.out.println("\nBilhetes que podem ser cancelados:");
            for (HashMap.Entry<Integer, Bilhete> bilheteSuplente : dicBilhete.entrySet()) {  //fazer o utilizador escolher um dos bilhetes suplentes
                System.out.println("\nBilhete: " + bilheteSuplente.getKey() + "\nId Rota: " + bilheteSuplente.getValue().getIdRota() + "\nId Voo: " + bilheteSuplente.getValue().getIdVoo() +
                        "\nData do Voo: " + bilheteSuplente.getValue().getDiaViagem() + "/" + bilheteSuplente.getValue().getMesViagem() + "/" + bilheteSuplente.getValue().getAnoViagem() +
                        "\nHora do Voo: " + bilheteSuplente.getValue().getHoraViagem() + "h " + bilheteSuplente.getValue().getMinutoViagem() + "m " + bilheteSuplente.getValue().getSegundoViagem() + "s");
            }
            System.out.print("\nEscolha o bilhete que pretende apagar: ");
            cancelar = dicBilhete.get(sc.nextInt());
            fazerCancelamento(cancelar,false); //escrever no ficheiro sem esse incluido
        }
    }


    //5 - Listar as rotas
    public void lerRotaTxt(String NomeFich) throws IOException {
        HashMap<Integer, Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            idRota = Integer.parseInt(campos[0]);
            quantidadeVoos = Integer.parseInt(campos[1]);
            destino = campos[2];
            distanciaKm = Double.parseDouble(campos[3]);
            Rota r = new Rota(idRota, quantidadeVoos, destino, distanciaKm);
            dicRota.put(idRota, r);
            linha = f.readLine();
        }
        f.close();
        for (HashMap.Entry<Integer, Rota> rotas : dicRota.entrySet()) {
            System.out.println(toStringR(rotas.getValue()));
        }
    }


    //6 - Listar os voos de uma rota
    public void listarVoosPorRota(String NomeFich, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = lerVoosPorRota(idRotas, NomeFich);
        int cont = 0;
        if (dicVoo.isEmpty()) {
            System.out.println("Erro! Não existem voos nesta rota.");
        } else {
            for (HashMap.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
                System.out.println(toStringV(voo.getValue()));
                cont++;
            }
            System.out.println("Existem " + cont + " voos com a rota " + idRotas + ".");
        }
    }


    //7- Listar o historial do passageiro (lista de viagens já realizadas)
    public void lerBilheteTxtPorPassageiro(String NomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroHistorial(NomeFich, idPassageiro, true, false);
        if (dicBilhete.isEmpty()) {
            System.out.println("\nO passageiro " + idPassageiro + " ainda não tem bilhetes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringX(bilhete.getValue().getIdRota(),bilhete.getValue().getIdVoo()));
            }
        }
    }


    //8 - Listar os bilhetes efetivos do passageiro (lista de viagens por realizar)
    public void listarBilheteEfetivos(String NomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente(NomeFich, idPassageiro, 1);
        if (dicBilhete.isEmpty()) {
            System.out.println("\nO passageiro " + idPassageiro + " não tem bilhetes efetivos.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringX(bilhete.getValue().getIdRota(),bilhete.getValue().getIdVoo()));
            }
        }
    }


    //9 - Listar os bilhetes suplentes do passageiro (lista de voos em espera)
    public void listarBilheteSuplentes(String NomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente(NomeFich, idPassageiro, 2);
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " não tem bilhetes suplentes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringX(bilhete.getValue().getIdRota(),bilhete.getValue().getIdVoo()));
            }
        }
    }



    //Auxiliares - leitura de ficheiros e ler strings:

    //usado na main para ver se existe o Id do passageiro
    public HashMap<String, Passageiro> lerPassageiroTxt(String NomeFich) throws IOException {
        HashMap<String, Passageiro> dicPassageiro = new HashMap<>();
        int anoNascimento, mesNascimento, diaNascimento;
        String idPassageiro, nome, profissao, morada;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            idPassageiro = campos[0];
            nome = campos[1];
            profissao = campos[2];
            morada = campos[3];
            anoNascimento = Integer.parseInt(campos[4]);
            mesNascimento = Integer.parseInt(campos[5]);
            diaNascimento = Integer.parseInt(campos[6]);
            Passageiro p = new Passageiro(idPassageiro, nome, profissao, morada, anoNascimento, mesNascimento, diaNascimento);
            dicPassageiro.put(idPassageiro, p);
            linha = f.readLine();
        }
        f.close();
        return dicPassageiro;
    }

    //usado no 1 e no 6
    private String toStringV(Voo value) {
        return "Id Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia da Semana: " + value.getDiaSemana() +"\nHora do Voo: " + value.getHora() + "h " +
                value.getMinuto() + "m " + value.getSegundo() + "s\nMarca do Avião: " + value.getMarcaAviao() + "\n";
    }

    //usado no 2
    public HashMap<Integer, Bilhete> lerBilheteTxt(String NomeFich, int idVooFiltro, int tipoBilheteFiltro, int idRotaFiltro, int ano, int mes, int dia) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao,
                minAquisicao, segAquisicao, tipoBilhete, cont = 1;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            if ((idVooFiltro == 0 || idVooFiltro == Integer.parseInt(campos[2]) && (tipoBilheteFiltro == 0 || tipoBilheteFiltro == Integer.parseInt(campos[16]))
                    && (idRotaFiltro == 0 || idRotaFiltro == Integer.parseInt(campos[1])) && (ano == 0 || ano == Integer.parseInt(campos[3]))
                    && (mes == 0 || mes == Integer.parseInt(campos[4])) && (dia == 0 || dia == Integer.parseInt(campos[5])))) {
                //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
                idPassageiro = campos[0];
                idRota = Integer.parseInt(campos[1]);
                idVoo = Integer.parseInt(campos[2]);
                anoViagem = Integer.parseInt(campos[3]);
                mesViagem = Integer.parseInt(campos[4]);
                diaViagem = Integer.parseInt(campos[5]);
                horaViagem = Integer.parseInt(campos[6]);
                minViagem = Integer.parseInt(campos[7]);
                segViagem = Integer.parseInt(campos[8]);
                anoAquisicao = Integer.parseInt(campos[9]);
                mesAquisicao = Integer.parseInt(campos[10]);
                diaAquisicao = Integer.parseInt(campos[11]);
                horaAquisicao = Integer.parseInt(campos[12]);
                minAquisicao = Integer.parseInt(campos[13]);
                segAquisicao = Integer.parseInt(campos[14]);
                preco = Double.parseDouble(campos[15]);
                tipoBilhete = Integer.parseInt(campos[16]);
                Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao,
                        horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                dicBilhete.put(cont, b);
                cont++;
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }

    //usado no 2
    public HashMap<Integer, Rota> lerRotasTxt(String NomeFich) throws IOException {
        HashMap<Integer, Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            idRota = Integer.parseInt(campos[0]);
            quantidadeVoos = Integer.parseInt(campos[1]);
            destino = campos[2];
            distanciaKm = Double.parseDouble(campos[3]);
            Rota r = new Rota(idRota, quantidadeVoos, destino, distanciaKm);
            dicRota.put(idRota, r);
            linha = f.readLine();
        }
        f.close();
        return dicRota;
    }

    //usado no 2
    public void mostrarRota(HashMap<Integer, Rota> dicRota) throws IOException {
        for (HashMap.Entry<Integer, Rota> Rota : dicRota.entrySet()) {
            System.out.println(Rota.getKey() + ": Destino: " + Rota.getValue().getDestino() + " | Km:" + Rota.getValue().getDistanciaKm());
        }
    }

    //usado no 2
    public void mostrarVoo(HashMap<Integer, Voo> dicVoo) throws IOException {
        LocalTime tempo = null;
        for (HashMap.Entry<Integer, Voo> Voo : dicVoo.entrySet()) {
            tempo = LocalTime.of(Voo.getValue().getHora(), Voo.getValue().getMinuto(), Voo.getValue().getSegundo());
            System.out.println(Voo.getKey() + ": Dia da semana: " + Voo.getValue().getDiaSemana() + " | Hora: " + tempo);
        }
    }

    //usado no 3
    public Bilhete encontrarBilheteParaSubstituir(int idVoo, int idRota, int anoViagem, int mesViagem, int diaViagem) throws IOException { //encontra o bilhete para substituir
        HashMap<Integer, Bilhete> dicBilhetesSuplentes = lerBilheteTxt("bilhetes.txt", idVoo, 2, idRota, anoViagem, mesViagem, diaViagem);
        //vai buscar os bilhetes suplentes daquele voo cujo bilhete vai ser cancelado
        LocalDateTime ultimaData = LocalDateTime.now();
        Bilhete bilheteParaRetornar = null;
        LocalDateTime dataBilhete = null;
        for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhetesSuplentes.entrySet()) { //percorre os bilhetes suplentes
            dataBilhete = LocalDateTime.of(bilhete.getValue().getAnoAquisicao(), bilhete.getValue().getMesAquisicao(), bilhete.getValue().getDiaAquisicao(),
                    bilhete.getValue().getHoraAquisicao(), bilhete.getValue().getMinutoAquisicao(), bilhete.getValue().getSegundoAquisicao()); //recebe a data de aquisição do bilhete
            if (dataBilhete.isBefore(ultimaData)) { //verifica se é o primeiro bilhete adquirido como suplente para não ser sempre o último
                bilheteParaRetornar = bilhete.getValue();
            }
        }
        return bilheteParaRetornar;
    }

    //usado no 3 e no 4
    public void fazerCancelamento(Bilhete bilhete, boolean paraSubstituir) throws IOException {
        Bilhete bilheteSubstituto = null;
        if(paraSubstituir){ //se for necessário substituir para efetivo
            bilheteSubstituto = encontrarBilheteParaSubstituir(bilhete.getIdVoo(), bilhete.getIdRota(), bilhete.getAnoViagem(), bilhete.getMesViagem(), bilhete.getDiaViagem());
        }
        HashMap<Integer, Bilhete> bilhetes = lerBilheteTxt("bilhetes.txt", 0, 0, 0, 0, 0, 0);
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("bilhetes.txt")); //refaz o ficheiro, porque não dá para apagar uma linha
        String linha = "";
        for (HashMap.Entry<Integer, Bilhete> Bilhete : bilhetes.entrySet()) { //corre os bilhetes todos
            if (Bilhete.getValue().getIdVoo() == bilhete.getIdVoo() && Bilhete.getValue().getIdRota() == bilhete.getIdRota() &&
                    Bilhete.getValue().getIdPassageiro().equals(bilhete.getIdPassageiro()) && Bilhete.getValue().getAnoViagem() == bilhete.getAnoViagem() &&
                    Bilhete.getValue().getMesViagem() == bilhete.getMesViagem() && Bilhete.getValue().getDiaViagem() == bilhete.getDiaViagem()) { //vê se é o bilhete para cancelar, se for não escreve
                System.out.println("Bilhete apagado");
            } else {
                if (bilheteSubstituto != null) { //ver se existe bilhete suplente
                    if (Bilhete.getValue().getIdVoo() == bilheteSubstituto.getIdVoo() && Bilhete.getValue().getIdRota() == bilheteSubstituto.getIdRota() &&
                            Bilhete.getValue().getIdPassageiro().equals(bilheteSubstituto.getIdPassageiro()) && Bilhete.getValue().getAnoViagem() == bilheteSubstituto.getAnoViagem() &&
                            Bilhete.getValue().getMesViagem() == bilheteSubstituto.getMesViagem() && Bilhete.getValue().getDiaViagem() == bilheteSubstituto.getDiaViagem()) {
                            //vai comparar o que entra no for, se tudo for igual, o substituto muda para efetivo -> setTipoBilhete
                        Bilhete.getValue().setTipoBilhete(1);
                    }
                }
                linha = linha + Bilhete.getValue().getIdPassageiro() + "," + Bilhete.getValue().getIdRota() + "," + Bilhete.getValue().getIdVoo() + "," + Bilhete.getValue().getAnoViagem() + "," +
                        Bilhete.getValue().getMesViagem() + "," + Bilhete.getValue().getDiaViagem() + "," + Bilhete.getValue().getHoraViagem() + "," + Bilhete.getValue().getMinutoViagem() + "," +
                        Bilhete.getValue().getSegundoViagem() + "," + Bilhete.getValue().getAnoAquisicao() + "," + Bilhete.getValue().getMesAquisicao() + "," + Bilhete.getValue().getDiaAquisicao() + "," +
                        Bilhete.getValue().getHoraAquisicao() + "," + Bilhete.getValue().getMinutoAquisicao() + "," + Bilhete.getValue().getSegundoAquisicao() + "," + Bilhete.getValue().getPreco() + "," +
                        Bilhete.getValue().getTipoBilhete(); //adiciona um bilhete (sempre que entra neste ramo do else, ao percorrer o for)
                buffWrite.append(linha);
                linha = "\n";
            }
        } //o for vai adicionar bilhete a bilhete
        buffWrite.close();
    }

    //usado no 5
    private String toStringR(Rota value) {
        return "Id: " + value.getIdRota() + "\nQuantidade de Voos: " + value.getQuantidadeHorarios() + "\nDestino: " + value.getDestino() + "\nDistancia (km): " + value.getDistanciaKm() + "\n";
    }

    //usado no 6
    public HashMap<Integer, Voo> lerVoosPorRota(int idRota, String NomeFich) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRotas, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            if (Integer.parseInt(campos[0]) == idRota) { //ver se a rota do voo é a rota pedida (se for adiciona ao hashmap, senão lê a próxima linha)
                idRotas = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcadoAviao = campos[6];
                Voo v = new Voo(idRotas, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
                dicVoo.put(idVoo, v);
            }
            linha = f.readLine();
        }
        f.close();
        return dicVoo;
    }

    //usado no 7
    public HashMap<Integer, Bilhete> lerBilhetePassageiroHistorial(String NomeFich, String idPassageiroFiltro, boolean BilheteAnterior, boolean BilhetePosterior) throws IOException {
        //antes a true vai buscar os bilhetes antes da data atual, depois a true vai buscar os bilhetes depois da data atual, ambos a true vai buscar antes e depois da data atual
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete,cont=0;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            if (idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) {
                //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
                idPassageiro = campos[0];
                idRota = Integer.parseInt(campos[1]);
                idVoo = Integer.parseInt(campos[2]);
                anoViagem = Integer.parseInt(campos[3]);
                mesViagem = Integer.parseInt(campos[4]);
                diaViagem = Integer.parseInt(campos[5]);
                horaViagem = Integer.parseInt(campos[6]);
                minViagem = Integer.parseInt(campos[7]);
                segViagem = Integer.parseInt(campos[8]);
                anoAquisicao = Integer.parseInt(campos[9]);
                mesAquisicao = Integer.parseInt(campos[10]);
                diaAquisicao = Integer.parseInt(campos[11]);
                horaAquisicao = Integer.parseInt(campos[12]);
                minAquisicao = Integer.parseInt(campos[13]);
                segAquisicao = Integer.parseInt(campos[14]);
                preco = Double.parseDouble(campos[15]);
                tipoBilhete = Integer.parseInt(campos[16]);
                data = LocalDateTime.of(anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem);
                if (data.isBefore(dataAtual)) {
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao,
                            diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    cont++;
                    dicBilhete.put(cont, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }

    //usado no 7, 8 e 9
    public HashMap<Integer,Voo> lerVooTxt (String NomeFich,int FiltroIdVoo, int FiltroIdRota) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRota, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            if(FiltroIdRota == Integer.parseInt(campos[0]) && FiltroIdVoo == Integer.parseInt(campos[1])) {
                idRota = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcadoAviao = campos[6];
                Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
                dicVoo.put(idVoo, v);
            }
            linha = f.readLine();
        }
        f.close();
        return dicVoo;
    }

    //usado no 7, 8 e 9
    private String toStringX(int IdRota,int IdVoo) throws IOException{
        HashMap<Integer, Voo> dicVoo = lerVooTxt("voos.txt",IdVoo,IdRota);
        String resposta="";
        for (HashMap.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
            resposta = "\nId Rota: " + voo.getValue().getIdRota() + "\nId Voo: " + voo.getValue().getIdVoo() + "\nDia da Semana: " + voo.getValue().getDiaSemana() + "\nHora: " + voo.getValue().getHora() +
                    ":" + voo.getValue().getMinuto() + ":" + voo.getValue().getSegundo() + "\nMarca do Avião: " + voo.getValue().getMarcaAviao();
        }
        return resposta;
    }

    //usado no 8 e no 9
    public HashMap<Integer, Bilhete> lerBilhetePassageiroEfetivoOuSuplente(String NomeFich, String idPassageiroFiltro, int tipoBilheteFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete, cont=0;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");
            if ((idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) && (tipoBilheteFiltro == 0 || tipoBilheteFiltro == Integer.parseInt(campos[16]))) {
                idPassageiro = campos[0];
                idRota = Integer.parseInt(campos[1]);
                idVoo = Integer.parseInt(campos[2]);
                anoViagem = Integer.parseInt(campos[3]);
                mesViagem = Integer.parseInt(campos[4]);
                diaViagem = Integer.parseInt(campos[5]);
                horaViagem = Integer.parseInt(campos[6]);
                minViagem = Integer.parseInt(campos[7]);
                segViagem = Integer.parseInt(campos[8]);
                anoAquisicao = Integer.parseInt(campos[9]);
                mesAquisicao = Integer.parseInt(campos[10]);
                diaAquisicao = Integer.parseInt(campos[11]);
                horaAquisicao = Integer.parseInt(campos[12]);
                minAquisicao = Integer.parseInt(campos[13]);
                segAquisicao = Integer.parseInt(campos[14]);
                preco = Double.parseDouble(campos[15]);
                tipoBilhete = Integer.parseInt(campos[16]);
                data = LocalDateTime.of(anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem);
                if (data.isAfter(dataAtual)) {
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao,
                            horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    cont++;
                    dicBilhete.put(cont, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }


    private String menu() {
        String op;

        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU PASSAGEIRO------------------------#");
        System.out.println("|  (1) - Comprar um bilhete efetivo          |");
        System.out.println("|  (2) - Cancelar um bilhete efetivo         |");
        System.out.println("|  (3) - Cancelar um bilhete suplente        |");
        System.out.println("|  (4) - Listar rotas                        |");
        System.out.println("|  (5) - Listar os voos de uma rota;         |");
        System.out.println("|  (6) - Listar historial                    |");
        System.out.println("|  (7) - Listar bilhetes efetivos            |");
        System.out.println("|  (8) - Listar bilhetes suplentes           |");
        System.out.println("|  (0) - Sair                                |");
        System.out.println("#--------------------------------------------#");
        System.out.print("Escolha opção: ");
        op = sc.next();

        return op;
    }
}