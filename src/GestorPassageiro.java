import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

public class GestorPassageiro {
    private HashMap<String, Passageiro> dicFinal;

    public GestorPassageiro() {
        dicFinal= new HashMap<>();
    }

    //1 - Registar-se como passageiro
    public void addPassageiro(int tipo) throws IOException {
        String idPassageiro, nome, profissao, morada, op;
        int anoNascimento, mesNascimento, diaNascimento, quanVoo, destVoo, idRota;
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("\n1-Criar novo  2-Existe");
        System.out.print("Escolha uma opção: ");
        tipo = sc.nextInt();
        if (tipo == 1) {
            System.out.println("\nQual é idPassageiro?");
            idPassageiro = sc.next();
            System.out.println("Qual o nome?");
            nome = sc.next();
            System.out.println("Qual é a profissão?");
            profissao = sc.next();
            System.out.println("Qual é a morada?");
            morada = sc.next();
            System.out.println("Qual é o ano de nascimento?");
            anoNascimento = sc1.nextInt();
            System.out.println("Qual é a mês de nscimento?");
            mesNascimento = sc1.nextInt();
            System.out.println("Qual é a dia de nascimento?");
            diaNascimento = sc1.nextInt();

            Passageiro A=new Passageiro(idPassageiro,nome,profissao,morada,anoNascimento,mesNascimento,diaNascimento);
            dicFinal.put(idPassageiro, A);
            for(HashMap.Entry<String, Passageiro> passageiro : dicFinal.entrySet()){
                System.out.println(passageiro.getKey() + ": " + toStringP(passageiro.getValue()));
            }
            op = menu();

            Integer idvoo = 0;
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        System.out.println("\nQuantidade de voos");
                        quanVoo = sc.nextInt();
                        int idRotas = 0;
                        for (int i = 0; i < quanVoo; i++) {
                            System.out.println("Destino a selecionar 1-Viseu 2-Porto");
                            idRotas = sc.nextInt();
                            System.out.println("Informação do voo");
                            selecionarVoosIdPorRotas("voos.txt", idRotas);
                        }
                        System.out.println("Informação do bilhete");

                }
                break;
            }
        } else {
            System.out.print("\nInsira um id: ");
            idPassageiro = sc.next();
            op = menuExistente();
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        System.out.println("\nQuantidade de Voos: ");
                        quanVoo = sc.nextInt();
                        for (int i = 0; i < quanVoo; i++) {
                            System.out.print("Destino a Selecionar: ");
                            destVoo = sc.nextInt();
                        }
                        System.out.println("Informação do voo: ");
                        System.out.println("Informação do bilhete: ");
                        break;
                    case"2":
                        System.out.println("blabla");
                        break;
                    case "3":
                        System.out.println("blablabla1231231231231231");
                        break;
                    case"4":
                        break;
                    case"5":
                        System.out.println("\nRotas: ");
                        lerRotaTxt("rotas.txt");
                        break;
                    case "6":
                        System.out.print("\nQual o id da rota que pretende listar os voos: ");
                        idRota = sc.nextInt();
                        listarVoosPorRota("voos.txt", idRota);
                        break;
                    case"7":
                        System.out.println("\nHistorial: ");
                        lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
                        break;
                    case"8":
                        break;
                    case"9":
                        break;
                }
                break;

            }
        }menuExistente();
    }


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
            idVoos= sc.nextInt();
            System.out.println(toStringV(dicVoo.get(idVoos)));
        }
    }
    private String toStringV(Voo value) {
        return "Id Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() +  "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }

    //5 - Listar as rotas
    public void lerRotaTxt(String NomeFich) throws IOException {
        HashMap<Integer,Rota> dicRota = new HashMap<>();
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

        for(HashMap.Entry<Integer, Rota> rotas : dicRota.entrySet()){
            System.out.println(toStringR(rotas.getValue()));
        }
    }

    private String toStringR(Rota value) {
        return "Id: " + value.getIdRota() + "\nQuantidade de Voos: " + value.getQuantidadeHorarios() + "\nDestino: " + value.getDestino() + "\nDistancia (km): " + value.getDistanciaKm() + "\n";
    }
    //FIM DO 5

    //6 - Listar os voos de uma rota;
    public void listarVoosPorRota (String NomeFich, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRota = 0, idVoo, hora, minuto, segundo,cont=0;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if(Integer.parseInt(campos[0]) == idRotas) { //ver se a rota do voo é a rota pedida (se for adiciona ao hashmap, senão lê a próxima linha)
                idRota = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcadoAviao = campos[6];
                Voo v = new Voo(idRota,idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
                dicVoo.put(idVoo, v);
                cont++;
            }
            linha = f.readLine();
        }
        f.close();

        if(dicVoo.isEmpty()){
            System.out.println("Erro! Não existem voos nesta rota.");
        }else {
            for (HashMap.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
                System.out.println(toStringV(voo.getValue()));
            }
            System.out.println("Existem " + cont + " voos com a rota " + idRota + ".");
        }
    }
    //FIM DO 6


    //7 - Listar o historial do passageiro (lista de viagens já realizadas)
    public void lerBilheteTxtPorPassageiro (String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer,Bilhete> dicBilhete = lerBilheteDePassageiro (nomeFich, idPassageiro);
        if(dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " ainda não tem bilhetes.");
        }else{
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    private String toStringB(Bilhete value) {
        return "Id: " + value.getIdPassageiro() + "\nId Rota: " + value.getIdRota() + "\nId Voo: " + value.getIdVoo() +"\n";
    }

    //Usado no 7
    public HashMap<Integer,Bilhete> lerBilheteDePassageiro(String NomeFich,String idPassageiroFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if(idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])){
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
                data = LocalDateTime.of(anoViagem,mesViagem,diaViagem,horaViagem,minViagem,segViagem);
                if(data.isBefore(dataAtual)) {
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco);
                    dicBilhete.put(idVoo, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }
    //FIM DO 7
/*
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
    }
*/
    public void lerVooTxt(String NomeFich) throws IOException {
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
    }

    public void lerPassageiroTxt(String NomeFich) throws IOException {
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
        for(HashMap.Entry<String, Passageiro> passageiro : dicPassageiro.entrySet()){
            System.out.println(passageiro.getKey() + ": " + toStringP(passageiro.getValue()));
        }
    }

    private String toStringP(Passageiro value) {
        return "Id: " + value.getIdPassageiro() + "\nNome: " + value.getNome() + "\nProfissao: " + value.getProfissao() + "\nMorada: " + value.getMorada() + "\nAno Nascimento: " + value.getAno() + "\nMes Nascimento: " + value.getMes() + "\nDia Nascimento: " + value.getDia() +"\n";
    }



    public void lerBilheteTxt(String NomeFich) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
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
            Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco);
            dicBilhete.put(idRota, b);
            linha = f.readLine();
        }
        f.close();
    }





    private String menuExistente() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU PASSAGEIRO------------------------#");
        System.out.println("|  (1) - Selecionar rotas                    |");
        System.out.println("|  (2) -                                     |");
        System.out.println("|  (3) -                                     |");
        System.out.println("|  (4) -                                     |");
        System.out.println("|  (5) - Listar rotas                        |");
        System.out.println("|  (6) - Listar os voos de uma rota;         |");
        System.out.println("|  (7) - Listar historial                    |");
        System.out.println("|  (8) - Listar bilhetes efetivos            |");
        System.out.println("|  (9) - Listar bilhetes suplentes           |");
        System.out.println("|  (0) - Sair                                |");
        System.out.println("#--------------------------------------------#");
        System.out.print("Escolha opção:");
        op=sc.next();
        return op;
    }
    private String menu() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU PASSAGEIRO--------------------------#");
        System.out.println("|  (1) - Selecionar rotas ? 1-Sim 2-Não      |");
        System.out.println("|  (0) - Sair                                |");
        System.out.println("#--------------------------------------------#");
        System.out.print("Escolha opção:");
        op=sc.next();
        return op;
    }
}