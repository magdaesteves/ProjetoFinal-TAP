import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GestorPassageiro g = new GestorPassageiro();
        GestorAssistente gA = new GestorAssistente();
        Scanner sc = new Scanner(System.in);
        String op, nif, curso, ficheiro, idPassageiro;
        int tipo, idRota, idVoo;
        op = menu();
        while (op.equals("0") == false) {
            switch (op) {
                case "1":
                    System.out.println("\nEscolha o tipo de pessoa: 1-Passageiro 2-Assistente de Bordo");
                    System.out.print("Escolha uma opção: ");
                    tipo = sc.nextInt();
                    if (tipo == 1) {
                        g.addPassageiro(tipo);
                    } else {
                        op = menuAssistente();
                        while (op.equals("0") == false) {
                            switch (op) {
                                case "1":
                                    gA.lerRotaTxt("rotas.txt");
                                    break;
                                case "2":
                                    System.out.print("\nQual o id da rota que pretende listar os voos: ");
                                    idRota = sc.nextInt();
                                    gA.listarVoosPorRota("voos.txt", idRota);
                                    break;
                                case "3":
                                    gA.lerPassageiroNomeIdTxt("passageiros.txt");
                                    break;
                                case "4":
                                    gA.lerPassageiroNomeIdTxtPorVoo("passageiros.txt");
                                    break;
                                case "5":
                                    gA.lerPassageiroSuplenteNomeIdTxtPorVoo("passageiros.txt");
                                    break;
                                case "6":
                                    System.out.print("\nQual o id do passageiro que pretende listar o historial de viagens: ");
                                    idPassageiro = sc.next();
                                    gA.lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
                                    break;
                                case "7":
                                    System.out.print("\nQual o id do passageiro que pretende listar os bilhetes efetivos de viagens por realizar: ");
                                    idPassageiro = sc.next();
                                    gA.listarBilheteEfetivos("bilhetes.txt", idPassageiro);
                                    break;
                                case "8":
                                    System.out.print("\nQual o id do passageiro que pretende listar os bilhetes suplentes de viagens por realizar: ");
                                    idPassageiro = sc.next();
                                    gA.listarBilheteSuplentes("bilhetes.txt", idPassageiro);
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case "2":
                    g.lerPassageiroTxt("passageiros.txt");
            }
            break;
        }
    }

    private static String menu() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---------------------------------------------------------------------#");
        System.out.println("|  Bem vindo à companhia aérea DWDM AirViseu deseja continuar?        |");
        System.out.println("|  (1) - Sim                                                          |");
        System.out.println("|  (0) - Não                                                          |");
        System.out.println("#---------------------------------------------------------------------#");
        System.out.print("Escolha uma opção: ");

        op = sc.next();
        return op;
    }

    private static String menuAssistente() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU ASSISTENTE DE BORDO-------------------------------------------------------#");
        System.out.println("|  (1) - Listar Rotas                                                              |");
        System.out.println("|  (2) - Listar os voos de uma rota                                                |");
        System.out.println("|  (3) - Listar todos os passageiros (o nome e o ID do passageiro)                 |");
        System.out.println("|  (4) - Listar os passageiros de um voo (o nome e o ID do passageiro)             |");
        System.out.println("|  (5) - Listar os passageiros suplentes de um voo (o nome e o ID do passageiro)   |");
        System.out.println("|  (6) - Listar o historial de um passageiro (lista de viagens já realizadas)      |");
        System.out.println("|  (7) - Listar os bilhetes efetivos de um passageiro (lista de voos a realizar)   |");
        System.out.println("|  (8) - Listar os bilhetes suplentes de um passageiro (lista de voos em espera)   |");
        System.out.println("#----------------------------------------------------------------------------------#");
        System.out.print("Escolha uma opção: ");

        op = sc.next();
        return op;
    }
}