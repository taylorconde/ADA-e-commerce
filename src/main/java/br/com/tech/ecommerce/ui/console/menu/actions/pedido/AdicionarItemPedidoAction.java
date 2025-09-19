package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class AdicionarItemPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;
    private final ProdutoAppService produtoAppService;

    public AdicionarItemPedidoAction(String pedidoId,
                                     PedidoAppService pedidoAppService,
                                     ProdutoAppService produtoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
        this.produtoAppService = produtoAppService;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);

        List<Produto> produtos = produtoAppService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("⚠ Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n--- Produtos Disponíveis ---");
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            System.out.println((i + 1) + " - " + p.getNome() + " | " + EcommerceConsoleApp.formatMoney(p.getPrecoBase()));
        }

        int escolha = -1;
        while (true) {
            System.out.print("Escolha o produto pelo número: ");
            String entrada = sc.nextLine();

            try {
                escolha = Integer.parseInt(entrada);
                if (escolha >= 1 && escolha <= produtos.size()) {
                    break; // entrada válida
                } else {
                    System.out.println("❌ Número fora do intervalo. Digite entre 1 e " + produtos.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número.");
            }
        }

        String produtoId = produtos.get(escolha - 1).getId();

        int qtd = -1;
        while (true) {
            System.out.print("Quantidade: ");
            String entrada = sc.nextLine();

            try {
                qtd = Integer.parseInt(entrada);
                if (qtd > 0) {
                    break; // valor válido
                } else {
                    System.out.println("❌ A quantidade deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        BigDecimal precoUnit = null;
        while (true) {
            System.out.print("Preço unitário: ");
            String entrada = sc.nextLine();

            try {
                precoUnit = new BigDecimal(entrada);
                if (precoUnit.compareTo(BigDecimal.ZERO) > 0) {
                    break; // valor válido
                } else {
                    System.out.println("❌ O preço deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número decimal, ex: 10.50");
            }
        }

        try {
            pedidoAppService.adicionarItem(pedidoId, produtoId, qtd, precoUnit);
            System.out.println("✅ Item adicionado com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println("❌ Não foi possível adicionar o item: " + e.getMessage());
        }
    }

    @Override
    public String getTitle() {
        return "Adicionar Item";
    }
}