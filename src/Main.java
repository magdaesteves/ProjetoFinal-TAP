import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorPassageiro gP = new GestorPassageiro();
        GestorAssistente gA = new GestorAssistente();
        Scanner sc = new Scanner(System.in);
        String operacao, menu, idPassageiro = "";
        int tipo, idRota;
        menu = menuInicio();
        if(menu.equals("1")) {
            /* ESCOLHER SE É PASSAGEITO OU ASSISTENTE DE BORDO */
            do {
                System.out.println("\nQue tipo de pessoa é? 1-Passageiro; 2-Assistente de Bordo");
                System.out.print("Escolha uma opção: ");
                tipo = sc.nextInt();
            } while (tipo != 1 && tipo != 2);

            /* MENU DO PASSAGEIRO */
            if (tipo == 1) {
                System.out.println("\n1-Criar novo  2-Existe");
                System.out.print("Escolha uma opção: ");
                tipo = sc.nextInt();
                if (tipo == 1) {
                    try {
                        idPassageiro = gP.adicionarPassageiro();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    boolean passageiroExiste = false;
                    HashMap<String, Passageiro> dicPassageiros = null;
                    try {
                        dicPassageiros = gP.lerPassageiroTxt("passageiro.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (!passageiroExiste) {
                        System.out.print("\nInsira um id: ");
                        idPassageiro = sc.next();
                        passageiroExiste = dicPassageiros.containsKey(idPassageiro);
                    }
                }
                do {
                    operacao = menuPassageiro();
                    switch (operacao) {
                        case "1":
                            try {
                                gP.comprarBilheteEfetivo(idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "2":
                            try {
                                gP.cancelarBilheteEfetivo(idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "3":
                            try {
                                gP.cancelarBilheteSuplente(idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "4":
                            try {
                                System.out.println("\nRotas: ");
                                gP.lerRotaTxt("rotas.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "5":
                            try {
                                System.out.print("\nQual o id da rota que pretende listar os voos: ");
                                idRota = sc.nextInt();
                                gP.listarVoosPorRota("voos.txt", idRota);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "6":
                            try {
                                System.out.println("\nHistorial: ");
                                gP.lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "7":
                            try {
                                System.out.println("\nBilhetes efetivos: ");
                                gP.listarBilheteEfetivos("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "8":
                            try {
                                System.out.println("\nBilhetes suplentes: ");
                                gP.listarBilheteSuplentes("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } while (!operacao.equals("0"));
                /* MENU DO ASSISTENTE DE BORDO */
            } else {
                do {
                    operacao = menuAssistente();
                    switch (operacao) {
                        case "1":
                            try {
                                gA.lerRotaTxt("rotas.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "2":
                            try {
                                System.out.print("\nQual o id da rota que pretende listar os voos: ");
                                idRota = sc.nextInt();
                                gA.listarVoosPorRota("voos.txt", idRota);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "3":
                            try {
                                gA.lerPassageiroNomeIdTxt("passageiro.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "4":
                            try {
                                gA.lerPassageiroNomeIdTxtPorVoo("passageiro.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "5":
                            try {
                                gA.lerPassageiroSuplenteNomeIdTxtPorVoo("passageiro.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "6":
                            try {
                                System.out.print("\nQual o id do passageiro que pretende listar o historial de viagens: ");
                                idPassageiro = sc.next();
                                gA.lerBilheteTxtPorPassageiro("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "7":
                            try {
                                System.out.print("\nQual o id do passageiro que pretende listar os bilhetes efetivos de viagens por realizar: ");
                                idPassageiro = sc.next();
                                gA.listarBilheteEfetivos("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "8": ;
                            try {
                                System.out.print("\nQual o id do passageiro que pretende listar os bilhetes suplentes de viagens por realizar: ");
                                idPassageiro = sc.next();
                                gA.listarBilheteSuplentes("bilhetes.txt", idPassageiro);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } while (!operacao.equals("0"));
            }//fim do else
        }//fim do if do meni inicial
    }//fim do psvm


    /*
    do { //eu quero que ele peça o NIF e este tem obrigatoriamente de ter 9 números
         System.out.print("Insira o NIF: ");
         NIF = sc.nextLine();
     } while (NIF.length() != 9);
     break;
     */
    private static String menuInicio() {
        String operacao;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n#---------------------------------------------------------------------#");
        System.out.println("|  Bem vindo à companhia aérea DWDM AirViseu deseja continuar?        |");
        System.out.println("|  (1) - Sim                                                          |");
        System.out.println("|  (0) - Não                                                          |");
        System.out.println("#---------------------------------------------------------------------#");
        System.out.print("Escolha uma opção: ");

        operacao = sc.next();
        return operacao;
    }

    public static String menuPassageiro() {
        Scanner sc = new Scanner(System.in);
        String operacao;
        System.out.println("\n#---MENU PASSAGEIRO------------------------#");
        System.out.println("|  (1) - Comprar um bilhete efetivo          |");
        System.out.println("|  (2) - Cancelar um bilhete efetivo         |");
        System.out.println("|  (3) - Cancelar um bilhete suplente        |");
        System.out.println("|  (4) - Listar rotas                        |");
        System.out.println("|  (5) - Listar os voos de uma rota          |");
        System.out.println("|  (6) - Listar historial                    |");
        System.out.println("|  (7) - Listar bilhetes efetivos            |");
        System.out.println("|  (8) - Listar bilhetes suplentes           |");
        System.out.println("|  (0) - Sair                                |");
        System.out.println("#--------------------------------------------#");
        System.out.print("Escolha opção: ");
        operacao = sc.next();
        return operacao;
    }

    private static String menuAssistente() {
        String operacao;
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
        System.out.println("|  (0) - Sair                                                                      |");
        System.out.println("#----------------------------------------------------------------------------------#");
        System.out.print("Escolha uma opção: ");
        operacao = sc.next();
        return operacao;
    }
}
