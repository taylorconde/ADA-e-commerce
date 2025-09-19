package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.List;
import java.util.Scanner;

public class AlterarQuantidadeItemPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;
    private final PedidoRepositorioArquivo pedidoRepo;

    public AlterarQuantidadeItemPedidoAction(String pedidoId,
                                             PedidoAppService pedidoAppService,
                                             PedidoRepositorioArquivo pedidoRepo) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);

        Pedido pedido = pedidoRepo.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        List<ItemPedido> itens = pedido.getItens();
        if (itens.isEmpty()) {
            System.out.println("⚠ Nenhum item no pedido.");
            return;
        }

        System.out.println("\n--- Itens do Pedido ---");
        for (int i = 0; i < itens.size(); i++) {
            ItemPedido item = itens.get(i);
            System.out.println((i + 1) + " - " + item.getProduto().getNome()
                    + " | Quantidade atual: " + item.getQuantidade());
        }

        int escolha = -1;
        while (true) {
            System.out.print("Escolha o item pelo número: ");
            String entrada = sc.nextLine();

            try {
                escolha = Integer.parseInt(entrada);
                if (escolha >= 1 && escolha <= itens.size()) {
                    break; // entrada válida
                } else {
                    System.out.println("❌ Número fora do intervalo. Digite entre 1 e " + itens.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número.");
            }
        }

        String itemId = itens.get(escolha - 1).getId();

        int novaQuantidade = -1;
        while (true) {
            System.out.print("Nova quantidade: ");
            String entrada = sc.nextLine();

            try {
                novaQuantidade = Integer.parseInt(entrada);
                if (novaQuantidade > 0) {
                    break;
                } else {
                    System.out.println("❌ A quantidade deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        pedidoAppService.alterarQuantidadeItem(pedidoId, itemId, novaQuantidade);
        System.out.println("✏ Quantidade alterada com sucesso!");
    }

    @Override
    public String getTitle() {
        return "Alterar Quantidade de Item";
    }
}