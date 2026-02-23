package techsupport.util;

import java.util.Scanner;

public class ConsoleUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pausar() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public static int lerInt(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, digite um número inteiro.");
            scanner.next();
            System.out.print(mensagem);
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        return valor;
    }

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}
