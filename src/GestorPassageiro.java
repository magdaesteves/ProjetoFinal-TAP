import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorPassageiro {
    private Map<String,String> Dicifinal;
    public GestorPassageiro() {

        Dicifinal= new HashMap<>();
    }

    public void lerVooTxt(String nf) throws IOException {
        Map<Integer, Voo> dicVoo = new HashMap<>();
        int idRota, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
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
        BufferedReader f = new BufferedReader(new FileReader(new File("passageiros.txt")));
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
    }
    public void lerRotaTxt(String nf) throws IOException {
        Map<Integer, Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File("rotas.txt")));
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
        Map<Integer, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File("bilhetes.txt")));
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
    public void addPassageiro(int tipo) {
        String idPassageiro, nome, profissao, morada, op;
        int anoNascimento = 0, mesNascimento, diaNascimento, quanVoo, destVoo;
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("1-Criar Novo  2-Existe");
        tipo = sc.nextInt();
        if (tipo == 1) {

            System.out.println("Qual é idPassageiro?");
            idPassageiro = sc.next();
            System.out.println("Qual o nome?");
            nome = sc.next();
            System.out.println("Qual é a profissao?");
            profissao = sc.next();
            System.out.println("Qual é a morada?");
            morada = sc.next();
            System.out.println("Qual é o anoNascimento?");
            anoNascimento = sc1.nextInt();
            System.out.println("Qual é a mesNascimento?");
            mesNascimento = sc1.nextInt();
            System.out.println("Qual é a diaNascimento?");
            diaNascimento = sc1.nextInt();

            Passageiro A=new Passageiro(idPassageiro,nome,profissao,morada,anoNascimento,mesNascimento,diaNascimento);
            Dicifinal.put(idPassageiro,String.valueOf(A));


            op = menu();
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        System.out.println("Quantidade de Voos");
                        quanVoo = sc.nextInt();
                        for (int i = 0; i < quanVoo; i++) {
                            System.out.println("Destino a Selecionar");
                            destVoo = sc.nextInt();
                        }
                        System.out.println("Informação do voo");
                        System.out.println("Informação do bilhete");

                }
                break;
            }
        } else {
            System.out.println("Insira um id");
            idPassageiro = sc.next();
            op = menuexistente();
            while (op.equals("0") == false) {
                switch (op) {
                    case "1":
                        System.out.println("Quantidade de Voos");
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
        } menuexistente();
    }
    private String menuexistente() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEscolha opção\n");
        System.out.println("1-Selecionar rotas ? \n");
        System.out.println("2-Ver rotas ? \n");
        System.out.println("3-Ver Voos ? \n");
        System.out.println("4-Selecionar Bilhetes ? \n");
        System.out.println("0-Sair");
        op=sc.next();
        return op;
    }
    private String menu() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEscolha opção\n");
        System.out.println("1-Selecionar rotas ? 1-Sim 2-Não\n");
        System.out.println("0-Sair");
        op=sc.next();
        return op;
    }
}