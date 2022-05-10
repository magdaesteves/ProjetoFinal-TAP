import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GestorPassageiro g = new GestorPassageiro();
        GestorAssistente gA = new GestorAssistente();
        Scanner sc = new Scanner(System.in);
        String op, nif, curso, ficheiro, idPassageiro;
        int tipo, idRota,idVoo;
        op = menu();
        while (op.equals("0") == false) {
            switch (op) {
                case "1":
                    System.out.println("1-Escolha o tipo de Pessoa 1-Passageiro 2-Assistente de Bordo");
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
                                    System.out.println("Qual o id da Rota que pretende listar os voos:");
                                    idRota = sc.nextInt();
                                    gA.listarVoosPorRota("voos.txt", idRota);
                                    break;
                                case "3":
                                    gA.lerPassageiroNomeIdTxt("passageiros.txt");
                                    break;
                                case "4":
                                    System.out.println("Qual o id do Voo que pretende listar todos os passageiros:");
                                    idVoo = sc.nextInt();
                                    gA.lerPassageiroNomeIdTxtPorVoo("passageiros.txt",idVoo);
                                    break;
                                case "5":
                                    break;
                                case "6":
                                    System.out.println("Qual o id do Passageiro que pretende listar os bilhetes:");
                                    idPassageiro = sc.next();
                                    gA.lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
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
        System.out.println("\n#---MENU PRINCIPAL---------------------------------------------------------------#");
        System.out.println("|  (1) - Bem vindo à companhia aérea DWDM AirViseu deseja continuar? 1-Sim 0-Não |");
        System.out.println("|  (2) - Ler ficheiro do passageiro                                              |");
        System.out.println("|  (0) - Sair                                                                    |");
        System.out.println("#--------------------------------------------------------------------------------#");
        System.out.print("\nEscolha uma opção: ");

        op=sc.next();
        return op;
    }

    private static String menuAssistente() {
         String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---MENU ASSISTENTE----------------------------------------------------------------#");
        System.out.println("|  (1) - Listar Rotas                                                              |");
        System.out.println("|  (2) - Listar os voos de uma rota                                                |");
        System.out.println("|  (3) - Listar todos os passageiros (o nome e o ID do passageiro)                 |");
        System.out.println("|  (4) - Listar os passageiros de um voo (o nome e o ID do passageiro)             |");
        System.out.println("|  (5) - Listar os passageiros suplentes de um voo (o nome e o ID do passageiro)   |");
        System.out.println("|  (6) - Listar o historial de um passageiro (lista de viagens já realizadas)      |");
        System.out.println("|  (7) - Listar os bilhetes efetivos de um passageiro (lista de voos a realizar)   |");
        System.out.println("|  (8) - Listar os bilhetes suplentes de um passageiro (lista de voos em espera)   |");
        System.out.println("#----------------------------------------------------------------------------------#");
        System.out.print("\nEscolha uma opção: ");

        op=sc.next();
        return op;
    }
}