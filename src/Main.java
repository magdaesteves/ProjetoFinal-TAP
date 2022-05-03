import java.awt.dnd.DragGestureRecognizer;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GestorPassageiro g = new GestorPassageiro();
        Scanner sc = new Scanner(System.in);
        String op, nif, curso, ficheiro;
        int tipo;
        op = menu();
        while (op.equals("0") == false) {
            switch (op) {
                case "1":
                    System.out.println("1-Escolha o tipo de Pessoa 1-Passageiro 2-Assistente de Bordo");
                    tipo = sc.nextInt();
                    if (tipo == 1) {
                        g.addPassageiro(tipo);
                    } else {

                    }
                case "2":
                    g.lerPassageiroTxt("passageiros.txt");
            }
            break;
        }
    }
    private static String menu() {
        String op;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEscolha opção\n");
        System.out.println("1-Bemvindo ao Aeroporto mosquito deseja continuar? 1-Sim 0-Não\n");
        System.out.println("2-Ler ficheiro do passageiro\n");
        System.out.println("0-Sair");
        op=sc.next();
        return op;
    }
}