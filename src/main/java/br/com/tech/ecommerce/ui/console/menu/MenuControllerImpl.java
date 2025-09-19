package src.main.java.br.com.tech.ecommerce.ui.console.menu;

import java.util.Map;
import java.util.Scanner;

public class MenuControllerImpl implements MenuController {

    private final Map<Integer, MenuAction> options;

    public MenuControllerImpl(Map<Integer, MenuAction> options) {
        this.options = options;
    }

    @Override
    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        do {
            System.out.println("\nEscolha uma opção:");
            for (Map.Entry<Integer, MenuAction> option : options.entrySet()) {
                System.out.println(option.getKey() + " - " + option.getValue().getTitle());
            }
            System.out.println("0 - Voltar");

            // Loop para validar entrada
            while (true) {
                System.out.print("Opção: ");
                String entrada = sc.nextLine();

                try {
                    opcao = Integer.parseInt(entrada);
                    break; // entrada numérica válida
                } catch (NumberFormatException e) {
                    System.out.println("❌ Entrada inválida. Digite um número inteiro.");
                }
            }

            if (opcao == 0) {
                break; // sair do menu
            }

            MenuAction action = options.get(opcao);
            if (action != null) {
                try {
                    action.execute();
                } catch (IllegalStateException | IllegalArgumentException e) {
                    // Captura erros de regra de negócio e mostra mensagem amigável
                    System.out.println("❌ " + e.getMessage());
                }
            } else {
                System.out.println("❌ Opção inválida");
            }

        } while (opcao != 0);
    }
}