import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorPassageiro {
    private Map<String, Passageiro> dicFinal;

    public GestorPassageiro() {
        dicFinal= new HashMap<>();
    }

    public void lerVooTxt(String nf) throws IOException {
        Map<Integer, Voo> dicVoo = new HashMap<>();
        int idRota, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
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

    public void lerPassageiroTxt(String nf) throws IOException {
        Map<String, Passageiro> dicPassageiro = new HashMap<>();
        int anoNascimento, mesNascimento, diaNascimento;
        String idPassageiro, nome, profissao, morada;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
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
        for(Map.Entry<String, Passageiro> passageiro : dicPassageiro.entrySet()){
            System.out.println(passageiro.getKey() + ": " + toString(passageiro.getValue()));
        }
    }

    private String toString(Passageiro value) {
        return "\nId: " + value.getIdPassageiro() + "\nNome: " + value.getNome() + "\nProfissao: " + value.getProfissao() + "\nMorada: " + value.getMorada() + "\nAno Nascimento: " + value.getAno() + "\nMes Nascimento: " + value.getMes() + "\nDia Nascimento: " + value.getDia() +"\n";
    }

    public void lerRotaTxt(String nf) throws IOException {
        Map<Integer, Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
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
    public void lerBilheteTxt(String nf) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
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

    public void selecionarVoosIdPorRotas(String nf, int idRotas) throws IOException {
        Map<Integer, Voo> dicVoo = new HashMap<>();
        Map<Integer, Voo> dicVoo2 = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int idRota = 0, idVoo = 0, hora, minuto, segundo, cont = 0;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
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
            for (Map.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
                System.out.println(toStringV(voo.getValue()));
            }
            System.out.println("Existem " + cont + " voos com a rota " + idRota + ".");
            System.out.println("Selecione um voo:");
            idVoos= sc.nextInt();
            System.out.println(toString(dicVoo.get(idVoos)));
        }
    }


    private String toString(Voo value) {
        return "\nId Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() +  "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }


    private String toStringV(Voo value) {
        return "\nId Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() +  "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }

    public void addPassageiro(int tipo) throws IOException {
        String idPassageiro, nome, profissao, morada, op;
        int anoNascimento, mesNascimento, diaNascimento, quanVoo, destVoo;
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
            for(Map.Entry<String, Passageiro> passageiro : dicFinal.entrySet()){
                System.out.println(passageiro.getKey() + ": " + toString(passageiro.getValue()));
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
            System.out.println("Insira um id");
            idPassageiro = sc.next();
            op = menuExistente();
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        System.out.println("\nQuantidade de Voos");
                        quanVoo = sc.nextInt();
                        for (int i = 0; i < quanVoo; i++) {
                            System.out.println("Destino a Selecionar");
                            destVoo = sc.nextInt();
                        }
                        System.out.println("Informação do voo");
                        System.out.println("Informação do bilhete");
                        break;
                    case"2":
                        System.out.println("blabla");
                        break;
                    case "3":
                        System.out.println("blablabla1231231231231231");
                        break;
                }
                break;

            }
        }menuExistente();
    }


    private String menuExistente() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU PASSAGEIRO--------------------#");
        System.out.println("|  (1) - Selecionar rotas              |");
        System.out.println("|  (2) - Ver rotas                     |");
        System.out.println("|  (3) - Ver Voos                      |");
        System.out.println("|  (4) - Selecionar Bilhetes           |");
        System.out.println("|  (0) - Sair                          |");
        System.out.println("#--------------------------------------#");
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