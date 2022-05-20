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
    private HashMap<String, Passageiro> dicFinal;

    public GestorPassageiro() {
        dicFinal = new HashMap<>();
    }

    //1 - Registar-se como passageiro
    public void addPassageiro(int tipo) throws IOException {
        String idPassageiro = "", nome, profissao, morada, op, op2 = null;
        int anoNascimento, mesNascimento, diaNascimento, quanVoo, destVoo, idRota = 0;
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("\n1-Criar novo  2-Existe");
        System.out.print("Escolha uma opção: ");
        tipo = sc.nextInt();
        if (tipo == 1) {
            System.out.println("\nQual é idPassageiro?");
            idPassageiro = sc.next();
            //if(idPassageiro.equals(l)
            System.out.print("Qual o nome?");
            nome = sc.next();
            System.out.print("Qual é a profissão?");
            profissao = sc.next();
            System.out.print("Qual é a morada?");
            morada = sc.next();
            System.out.print("Qual é o ano de nascimento?");
            anoNascimento = sc1.nextInt();
            System.out.print("Qual é a mês de nascimento?");
            mesNascimento = sc1.nextInt();
            System.out.print("Qual é a dia de nascimento?");
            diaNascimento = sc1.nextInt();

            Passageiro A = new Passageiro(idPassageiro, nome, profissao, morada, anoNascimento, mesNascimento, diaNascimento);
            dicFinal.put(idPassageiro, A);
            CriarPassageiro(A);
            op2 = menu1();


            while (op2.equals("0") == false) {

                switch (op2) {
                    case "1":
                        ComprarBilheteEfetivo(idPassageiro);
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        System.out.println("Celso");
                        break;
                    case "5":
                        break;
                    case "6":
                        break;
                    case "7":
                        System.out.println("Listar bilhetes efetivos :");
                        listarBilheteEfetivos("bilhetes.txt", idPassageiro);
                        break;
                    case "8":
                        listarBilheteSuplentes("bilhetes.txt", idPassageiro);
                        break;


                }


                op2 = menu1();
            }


        } else {
            boolean passageiroExiste = false;
            HashMap<String, Passageiro> dicPassageiros = lerPassageiroTxt("passageiros.txt");
            while (!passageiroExiste) {
                System.out.print("\nInsira um id: ");
                idPassageiro = sc.next();
                passageiroExiste = dicPassageiros.containsKey(idPassageiro);
            }

            op = menu();
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        ComprarBilheteEfetivo(idPassageiro);

                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":
                        System.out.println("\nRotas: ");
                        lerRotaTxt("rotas.txt");
                        break;
                    case "5":
                        System.out.print("\nQual o id da rota que pretende listar os voos: ");
                        idRota = sc.nextInt();
                        listarVoosPorRota("voos.txt", idRota);
                        break;
                    case "6":
                        System.out.println("\nHistorial: ");
                        lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
                        break;
                    case "7":
                        break;
                    case "8":
                        listarBilheteSuplentes("bilhetes.txt", idPassageiro);
                        break;

                }
                menu();
                break;

            }

        }

    }

    //1 - Registar-se como passageiro
    public void CriarPassageiro(Passageiro Passageiro) throws IOException { //esta função vai escrever no txt do passageiro
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("passageiro.txt", true));
        String linha = "\n" + Passageiro.getIdPassageiro() + "," + Passageiro.getNome() + "," + Passageiro.getProfissao() + "," + Passageiro.getMorada() + "," +
                Passageiro.getAno() + "," + Passageiro.getMes() + "," + Passageiro.getDia();
        buffWrite.append(linha);
        buffWrite.close();
    }

    //2 - Comprar um bilhete efetivo - não havendo vaga será um bilhete suplente (só há 4 bilhetes suplentes em cada voo);
    public void ComprarBilheteEfetivo(String idPassageiro) throws IOException {
        Scanner sc = new Scanner(System.in);
        LocalDate VerificarData;
        Bilhete bilhete = null;
        Rota Rota = EscolherRota();                 //vai à função para escolher uma rota
        Voo Voo = EscolherVoo(Rota.getIdRota());    //dependendo da rota vai dizer quais os voos que existem relacionados com a mesma
        int tipoBilhete = 0;    //vai ver a função onde vai indicar se é suplente, efetivo ou se não existe (switch abaixo)
        double preco = 0;
        int ano, mes, dia = 0;
        boolean diaEncontrado = false;
        String diaSemana;
        preco = calculoPrecoBilhete(Rota, 1, 300, 1000, 25, 50);
        //A data do voo é pedida ao utilizador, onde verificamos posteriormente se o mesmo pertence ao dia de semana do voo
        System.out.print("\nAno da viagem: ");
        ano = sc.nextInt();
        System.out.print("Mês da viagem: ");
        mes = sc.nextInt();
        diaSemana = Voo.getDiaSemana().toUpperCase(Locale.ROOT);
        while (!diaEncontrado) {
            System.out.print("Dia da viagem: ");
            dia = sc.nextInt();
            VerificarData = LocalDate.of(ano, mes, dia);
            diaEncontrado = diaSemana.equals(VerificarData.getDayOfWeek().name());
            if (!diaEncontrado) {
                System.out.println("Tem de selecionar um dia que corresponda a " + diaSemana);
            } else {
                tipoBilhete = tipoBilhete(Voo, Rota, ano, mes, dia);
                if (tipoBilhete == 0) {
                    System.out.println("\nNão existem bilhetes disponíveis para o dia pretendido!");
                    diaEncontrado = false;
                } else {
                    LocalDateTime data = LocalDateTime.now();
                    bilhete = new Bilhete(idPassageiro, Rota.getIdRota(), Voo.getIdVoo(), ano, mes, dia, Voo.getHora(), Voo.getMinuto(), Voo.getSegundo(), data.getYear(), data.getMonth().getValue(), data.getDayOfMonth(), data.getHour(), data.getMinute(), data.getSecond(), preco, tipoBilhete);
                    CriarBilhete(bilhete);
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

    public Rota EscolherRota() throws IOException { //mostra todas as rotas para escolher uma que esteja válida, ex. Lisboa, Leiria, Porto, etc
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Rota> dicRotas = lerRotasTxt("rotas.txt");
        boolean encontrado = false;
        int rota = 0;
        while (!encontrado) {
            System.out.println("\nEscolha uma das seguintes rotas:");
            MostrarRota(dicRotas);
            System.out.print("\nEscolha uma opção: ");
            rota = sc.nextInt();
            encontrado = dicRotas.containsKey(rota); //verifica se a rota escolhida existe
        }
        return dicRotas.get(rota);
    }

    public Voo EscolherVoo(int idRota) throws IOException { //após escolher a rota, aparecem os voos programados para essa rota, e tem de se escolher uma
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Voo> dicVoo = getVoosPorRota(idRota, "voos.txt");
        boolean encontrado = false;
        int voo = 0;
        while (!encontrado) {
            System.out.println("\nEscolha um dos seguintes voos:");
            MostrarVoo(dicVoo);
            System.out.print("\nEscolha uma opção: ");
            voo = sc.nextInt();
            encontrado = dicVoo.containsKey(voo);
        }
        return dicVoo.get(voo);
    }

    public int tipoBilhete(Voo Voo, Rota Rota, int ano, int mes, int dia) throws IOException { //verifica se o bilhete é ou não efetivo
        int maxBilhetes = getMaxBilhetes(Voo.getMarcaAviao());
        HashMap<String, Bilhete> dicBilhetesEfetivos = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 1, Rota.getIdRota(), ano, mes, dia);
        int bilhetesEfetivos = dicBilhetesEfetivos.size();
        HashMap<String, Bilhete> dicBilhetesSuplentes = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 2, Rota.getIdRota(), ano, mes, dia);
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

    public int getMaxBilhetes(String aviao) throws IOException { //diz qual o máximo de bilhetes efetivos por avião
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

    public double calculoPrecoBilhete(Rota Rota, double precoKM, double primeiroPrecoDesconto, double segundoPrecoDesconto, double primeiroDescontoPercentagem, double segundoDescontoPercentagem) throws IOException { //verifica os Km
        double Preco = Rota.getDistanciaKm() * precoKM;
        if (Preco > segundoPrecoDesconto) //se o preço for maior que o segundo desconto então aplica os 50% de desconto
            return Preco * (segundoDescontoPercentagem / 100);
        if (Preco > primeiroPrecoDesconto) //se o preço for maior que o primeiro desconto então aplica os 25% de desconto
            return Preco * (primeiroDescontoPercentagem / 100);
        return Preco; // senão ele vai dar o preço que é 1€ (neste caso em concreto, pois pode ser alterado o preço por km) por km
    }

    public void CriarBilhete(Bilhete Bilhete) throws IOException { //esta função vai escrever no txt do bilhete, efetivo (1) ou suplente (2)
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("bilhetes.txt", true));
        String linha = "\n" + Bilhete.getIdPassageiro() + "," + Bilhete.getIdRota() + "," + Bilhete.getIdVoo() + "," + Bilhete.getAnoViagem() + "," +
                Bilhete.getMesViagem() + "," + Bilhete.getDiaViagem() + "," + Bilhete.getHoraViagem() + "," + Bilhete.getMinutoViagem() + "," +
                Bilhete.getSegundoViagem() + "," + Bilhete.getAnoAquisicao() + "," + Bilhete.getMesAquisicao() + "," + Bilhete.getDiaAquisicao() + "," +
                Bilhete.getHoraAquisicao() + "," + Bilhete.getMinutoAquisicao() + "," + Bilhete.getSegundoAquisicao() + "," + Bilhete.getPreco() + "," +
                Bilhete.getTipoBilhete();
        buffWrite.append(linha);
        buffWrite.close();
    }
    //Fim 2


    //5 - Listar as rotas
    public void lerRotaTxt(String NomeFich) throws IOException {
        HashMap<Integer, Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
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

    private String toStringR(Rota value) {
        return "Id: " + value.getIdRota() + "\nQuantidade de Voos: " + value.getQuantidadeHorarios() + "\nDestino: " + value.getDestino() + "\nDistancia (km): " + value.getDistanciaKm() + "\n";
    }
    //FIM DO 5

    //6 - Listar os voos de uma rota;
    public void listarVoosPorRota(String NomeFich, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = getVoosPorRota(idRotas, NomeFich);
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

    public HashMap<Integer, Voo> getVoosPorRota(int idRota, String NomeFich) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRotas, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
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
    //FIM DO 6


    //7- Listar o historial do passageiro (lista de viagens já realizadas)
    public void lerBilheteTxtPorPassageiro(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroHistorial(nomeFich, idPassageiro);
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " ainda não tem bilhetes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    public HashMap<Integer, Bilhete> lerBilhetePassageiroHistorial(String NomeFich, String idPassageiroFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if (idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) {
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
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    dicBilhete.put(idVoo, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }
    //FIM DO 7


    //8 - Listar os bilhetes efetivos do passageiro (lista de viagens por realizar)
    public void listarBilheteEfetivos(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroEfetivos(nomeFich, idPassageiro);
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " ainda não tem bilhetes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    private String toStringB(Bilhete value) {
        return "Id: " + value.getIdPassageiro() + "\nId Rota: " + value.getIdRota() + "\nId Voo: " + value.getIdVoo() + "\n";
    }

    public HashMap<Integer, Bilhete> lerBilhetePassageiroEfetivos(String NomeFich, String idPassageiroFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if (idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) {
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
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    dicBilhete.put(idVoo, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }
    //FIM DO 8

    //9 - Listar os bilhetes suplentes do passageiro (lista de voos em espera)
    public void listarBilheteSuplentes(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroSuplentes(nomeFich, idPassageiro, 2);
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " não tem bilhetes suplentes por realizar.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringS(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    private String toStringS(Bilhete value) {
        return "\n " + value.getIdPassageiro() + "," + value.getIdRota() + "," + value.getIdVoo() + "," + value.getAnoViagem() + "/" + value.getMesViagem() +
                "/" + value.getDiaViagem() + "," + value.getHoraViagem() + ":" + value.getMinutoViagem() + ":" + value.getSegundoViagem() + value.getAnoAquisicao() +
                "/" + value.getMesAquisicao() + "/" + value.getDiaAquisicao() + "," + value.getHoraAquisicao() + ":" + value.getMinutoAquisicao() + ":" +
                value.getSegundoAquisicao() + "," + value.getPreco();
    }

    public HashMap<Integer, Bilhete> lerBilhetePassageiroSuplentes(String NomeFich, String idPassageiroFiltro, int tipoBilheteFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if ((idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) && (tipoBilheteFiltro == 2 || tipoBilheteFiltro == Integer.parseInt(campos[16]))) {
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
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    dicBilhete.put(idVoo, b);
                }

            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }
    //FIM DO 9


    //Auxiliares:

    //Usado no 1
    public void selecionarVoosIdPorRotas(String NomeFich, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int idRota = 0, idVoo = 0, hora, minuto, segundo, cont = 0;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        int idVoos = 0;
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if (Integer.parseInt(campos[0]) == idRotas) { //ver se a rota do voo é a rota pedida (se for adiciona ao hashmap, senão lê a próxima linha)
                idRota = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcadoAviao = campos[6];
                Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
                dicVoo.put(idVoo, v);
                cont++;
            }
            linha = f.readLine();
        }

        f.close();

        if (dicVoo.isEmpty()) {
            System.out.println("Erro! Não existem voos nesta rota.");
        } else {
            for (HashMap.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
                System.out.println(toStringV(voo.getValue()));
            }
            System.out.println("Existem " + cont + " voos com a rota " + idRota + ".");
            System.out.println("Selecione um voo: ");
            idVoos = sc.nextInt();
            System.out.println(toStringV(dicVoo.get(idVoos)));
        }
    }

    private String toStringV(Voo value) {
        return "Id Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() + "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }

    //usado na main para ver se existe o id do passageiro
    public HashMap<String, Passageiro> lerPassageiroTxt(String NomeFich) throws IOException {
        HashMap<String, Passageiro> dicPassageiro = new HashMap<>();
        int anoNascimento, mesNascimento, diaNascimento;
        String idPassageiro, nome, profissao, morada;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
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

    private String toStringP(Passageiro value) {
        return "Id: " + value.getIdPassageiro() + "\nNome: " + value.getNome() + "\nProfissao: " + value.getProfissao() + "\nMorada: " + value.getMorada() + "\nAno Nascimento: " + value.getAno() + "\nMes Nascimento: " + value.getMes() + "\nDia Nascimento: " + value.getDia() + "\n";
    }

    //usado no 2
    public HashMap<String, Bilhete> lerBilheteTxt(String NomeFich, int idVooFiltro, int tipoBilheteFiltro, int idRotaFiltro, int ano, int mes, int dia) throws IOException {
        HashMap<String, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, tipoBilhete;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if ((idVooFiltro == 0 || idVooFiltro == Integer.parseInt(campos[2]) && (tipoBilheteFiltro == 0 || tipoBilheteFiltro == Integer.parseInt(campos[16])) &&
                    (idRotaFiltro == 0 || idRotaFiltro == Integer.parseInt(campos[1])) && (ano == 0 || ano == Integer.parseInt(campos[3])) && (mes == 0 || mes == Integer.parseInt(campos[4])) &&
                    (dia == 0 || dia == Integer.parseInt(campos[5])))) { //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
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
                Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                dicBilhete.put(idPassageiro, b);
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
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
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
    public void MostrarRota(HashMap<Integer, Rota> dicRota) throws IOException {
        for (HashMap.Entry<Integer, Rota> Rota : dicRota.entrySet()) {
            System.out.println(Rota.getKey() + ": Destino: " + Rota.getValue().getDestino() + " " + Rota.getValue().getDistanciaKm());
        }
    }

    //usado no 2
    public void MostrarVoo(HashMap<Integer, Voo> dicVoo) throws IOException {
        LocalTime tempo = null;
        for (HashMap.Entry<Integer, Voo> Voo : dicVoo.entrySet()) {
            tempo = LocalTime.of(Voo.getValue().getHora(), Voo.getValue().getMinuto(), Voo.getValue().getSegundo());
            System.out.println(Voo.getKey() + ": Dia da semana: " + Voo.getValue().getDiaSemana() + " " + tempo);
        }
    }

    /*
    public HashMap<Integer,Voo> lerVooTxt (String NomeFich) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRota, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idRota = Integer.parseInt(campos[0]);
            idVoo = Integer.parseInt(campos[1]);
            diaSemana = campos[2];
            hora = Integer.parseInt(campos[3]);
            minuto = Integer.parseInt(campos[4]);
            segundo = Integer.parseInt(campos[5]);
            marcadoAviao = campos[6];
            Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
            dicVoo.put(idVoo, v);
            linha = f.readLine();
        }
        f.close();
        return dicVoo;
    }
     */


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

    private String menu1() {
        String op2;

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
        op2 = sc.next();

        return op2;
    }
}