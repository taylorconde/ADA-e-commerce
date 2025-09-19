package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.List;
import java.util.Scanner;

public class RemoverItemPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;
    private final ProdutoAppService produtoAppService;
    private final PedidoRepositorioArquivo pedidoRepo;

    public RemoverItemPedidoAction(String pedidoId,
                                   PedidoAppService pedidoAppService,
                                   ProdutoAppService produtoAppService,
                                   PedidoRepositorioArquivo pedidoRepo) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
        this.produtoAppService = produtoAppService;
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
            System.out.println((i + 1) + " - " + item.getProduto().getNome() + " | Qtd: " + item.getQuantidade());
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
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        String itemId = itens.get(escolha - 1).getId(); // ✅ ID do ItemPedido, não do Produto

        pedidoAppService.removerItem(pedidoId, itemId);
        System.out.println("🗑 Item removido com sucesso!");
    }

    @Override
    public String getTitle() {
        return "Remover Item";
    }
}