import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class GestorAssistente {
    //1 - Listar Rotas
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


    //2 - Listar os voos de uma rota
    public void listarVoosPorRota(String NomeFich, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRota = 0, idVoo, hora, minuto, segundo, cont = 0;
        String diaSemana, marcaAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if (Integer.parseInt(campos[0]) == idRotas) { //ver se a rota do voo é a rota pedida (se for adiciona ao hashmap, senão lê a próxima linha)
                idRota = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcaAviao = campos[6];
                Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcaAviao);
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
        }
    }

    private String toStringV(Voo value) {
        return "Id Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() + "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }


    //3 - Listar todos os passageiros (o nome e o ID do passageiro)
    public void lerPassageiroNomeIdTxt(String NomeFich) throws IOException {
        HashMap<String, Passageiro> dicPassageiro = lerPassageiroTxt(NomeFich);
        if (dicPassageiro.isEmpty()) {
            System.out.println("Não existem passageiros.");
        } else {
            for (HashMap.Entry<String, Passageiro> passageiro : dicPassageiro.entrySet()) {
                System.out.println(toStringP(passageiro.getValue()));
            }
        }
    }


    //4 - Listar os passageiros de um voo (o nome e o ID do passageiro)
    public void lerPassageiroNomeIdTxtPorVoo(String NomeFich) throws IOException {
        Scanner sc = new Scanner(System.in);
        LocalDate VerificarData;
        Rota Rota = EscolherRota();
        Voo Voo = EscolherVoo(Rota.getIdRota());
        int ano, mes, dia = 0;
        boolean diaEncontrado = false;
        String diaSemana;
        System.out.println("\nAno da viagem: ");
        ano = sc.nextInt();
        System.out.println("Mês da viagem: ");
        mes = sc.nextInt();
        diaSemana = Voo.getDiaSemana().toUpperCase(Locale.ROOT);
        while (!diaEncontrado) {
            System.out.println("Dia da viagem: ");
            dia = sc.nextInt();
            VerificarData = LocalDate.of(ano, mes, dia);
            diaEncontrado = diaSemana.equals(VerificarData.getDayOfWeek().name());
            if (!diaEncontrado) {
                System.out.println("\nTem de selecionar um dia que corresponda a " + diaSemana);
            }
        }
        HashMap<String, Bilhete> dicBilhete = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 1, Rota.getIdRota(), ano, mes, dia); //lê os bilhetes com o filtro do id do voo
        HashMap<String, Passageiro> dicPassageiro = lerPassageiroTxt(NomeFich); //lê os passageiros todos
        if (dicBilhete.isEmpty()) { //se não existirem bilhetes diz que o voo não tem passageiros
            System.out.println("\nO voo " + Voo.getIdVoo() + " ainda não tem passageiros.");
        } else {
            for (HashMap.Entry<String, Bilhete> bilhete : dicBilhete.entrySet()) { //senão corre os bilhetes
                if (dicPassageiro.get(bilhete.getKey()) == null) { //vê se o passageiro existe no ficheiro dos passageiros
                    System.out.println("\nPassageiro " + bilhete.getKey() + " sem registo!"); //se não existe, diz que não tem registo
                } else {
                    System.out.println(toStringP(dicPassageiro.get(bilhete.getKey()))); //senão printamos o passageiro
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
        HashMap<Integer, Voo> dicVoo = lerVoosPorRota(idRota, "voos.txt");
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

    public void MostrarRota(HashMap<Integer, Rota> dicRota) throws IOException {
        for (HashMap.Entry<Integer, Rota> Rota : dicRota.entrySet()) {
            System.out.println(Rota.getKey() + ": Destino: " + Rota.getValue().getDestino() + " " + Rota.getValue().getDistanciaKm());
        }
    }

    public void MostrarVoo(HashMap<Integer, Voo> dicVoo) throws IOException {
        LocalTime tempo = null;
        for (HashMap.Entry<Integer, Voo> Voo : dicVoo.entrySet()) {
            tempo = LocalTime.of(Voo.getValue().getHora(), Voo.getValue().getMinuto(), Voo.getValue().getSegundo());
            System.out.println(Voo.getKey() + ": Dia da semana: " + Voo.getValue().getDiaSemana() + " " + tempo);
        }
    }

    private String toStringP(Passageiro value) {
        return "\nId: " + value.getIdPassageiro() + "\nNome: " + value.getNome() + "\n";
    }


    //5 - Listar os passageiros suplentes de um voo (o nome e o ID do passageiro)
    public void lerPassageiroSuplenteNomeIdTxtPorVoo(String NomeFich) throws IOException {
        Scanner sc = new Scanner(System.in);
        LocalDate VerificarData;
        Rota Rota = EscolherRota();
        Voo Voo = EscolherVoo(Rota.getIdRota());
        int ano, mes, dia = 0;
        boolean diaEncontrado = false;
        String diaSemana;
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
                System.out.println("\nTem de selecionar um dia que corresponda a " + diaSemana);
            }
        }
        HashMap<String, Bilhete> dicBilhete = lerBilheteTxt("bilhetes.txt", Voo.getIdVoo(), 2, Rota.getIdRota(), ano, mes, dia); //lê os bilhetes com o filtro do id do voo
        HashMap<String, Passageiro> dicPassageiro = lerPassageiroTxt(NomeFich); //lê os passageiros todos
        if (dicBilhete.isEmpty()) { //se não existirem bilhetes diz que o voo não tem passageiros
            System.out.println("\nO voo " + Voo.getIdVoo() + " ainda não tem passageiros.");
        } else {
            for (HashMap.Entry<String, Bilhete> bilhete : dicBilhete.entrySet()) { //senão corre os bilhetes
                if (dicPassageiro.get(bilhete.getKey()) == null) { //vê se o passageiro existe no ficheiro dos passageiros
                    System.out.println("\nPassageiro " + bilhete.getKey() + " sem registo!"); //se não existe, diz que não tem registo
                } else {
                    System.out.println(toStringP(dicPassageiro.get(bilhete.getKey()))); //senão printamos o passageiro
                }
            }
        }
    }

    //6 - Listar o historial de um passageiro (lista de viagens já realizadas)
    public void lerBilheteTxtPorPassageiro(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilheteDePassageiro(nomeFich, idPassageiro);
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " ainda não tem bilhetes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    private String toStringB(Bilhete value) {
        return "\nId: " + value.getIdPassageiro() + "\nId Rota: " + value.getIdRota() + "\nId Voo: " + value.getIdVoo();
    }


    //7 - Listar os bilhetes efetivos do passageiro (lista de viagens por realizar)
    public void listarBilheteEfetivos(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente(nomeFich, idPassageiro, 1);
        HashMap<Integer, Voo> dicVoo = lerVooTxt("voos.txt");
        if (dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " não tem bilhetes efetivos.");
        } else {
            for (Map.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                if (dicVoo.get(bilhete.getKey()) == null) {
                    System.out.println("\nPassageiro " + bilhete.getKey() + " sem registo!");
                } else {
                    System.out.println(toStringX(dicVoo.get(bilhete.getKey())));
                }
            }
            /*for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            } */
        }
    }

    private String toStringX(Voo value) {
        return "\nId Rota: " + value.getIdRota() + "\nId Voo: " + value.getIdVoo() + "\nDia da Semana: " + value.getDiaSemana() + "\nHora: " + value.getHora() +
                ":" + value.getMinuto() + ":" + value.getSegundo() + "\nMarca do Avião: " + value.getMarcaAviao();
    }
    //FIM DO 7


    //8 - Listar os bilhetes suplentes do passageiro (lista de voos em espera)
    public void listarBilheteSuplentes(String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = lerBilhetePassageiroEfetivoOuSuplente(nomeFich, idPassageiro, 2);
        HashMap<Integer, Voo> dicVoo = lerVooTxt("voos.txt");
        if (dicBilhete.isEmpty()) {
            System.out.println("\nO passageiro " + idPassageiro + " não tem bilhetes suplentes.");
        } else {
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringX(dicVoo.get(bilhete.getKey())));
            }
        }
    }
    //FIM DO 8




    //Auxiliares - leitura de ficheiros:

    //Usado no 2 no 4
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


    //Usado no 4
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

    //usado no 4
    public HashMap<Integer, Voo> lerVoosPorRota(int idRota, String NomeFich) throws IOException {
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

    //usado no 4
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

    //usado no 6
    public HashMap<Integer, Bilhete> lerBilheteDePassageiro(String NomeFich, String idPassageiroFiltro) throws IOException {
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
            if (idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])) { //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
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

    //usado no 7 e no 8
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

    //usado no 7 e no 8
    public HashMap<Integer, Bilhete> lerBilhetePassageiroEfetivoOuSuplente(String NomeFich, String idPassageiroFiltro, int tipoBilheteFiltro) throws IOException {
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
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco, tipoBilhete);
                    dicBilhete.put(idVoo, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }
}
