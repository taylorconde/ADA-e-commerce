package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.math.BigDecimal;

public class VerDetalhesPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoRepositorioArquivo pedidoRepo;
    private final PedidoAppService pedidoAppService;

    public VerDetalhesPedidoAction(String pedidoId,
                                   PedidoRepositorioArquivo pedidoRepo,
                                   PedidoAppService pedidoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoRepo = pedidoRepo;
        this.pedidoAppService = pedidoAppService;
    }

    @Override
    public void execute() {
        Pedido pedido = pedidoRepo.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        System.out.println("\n--- Detalhes do Pedido ---");
        System.out.println("ID: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Data de criação: " + EcommerceConsoleApp.formatDate(pedido.getDataCriacao()));
        System.out.println("Status Pedido: " + pedido.getStatusPedido());
        System.out.println("Status Pagamento: " + pedido.getStatusPagamento());

        System.out.println("Itens:");
        for (ItemPedido item : pedido.getItens()) {
            BigDecimal subtotal = item.getPrecoUnitario()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));
            System.out.println(" - Produto: " + item.getProduto().getNome()
                    + " | Quantidade: " + item.getQuantidade()
                    + " | Preço Unitário: " + EcommerceConsoleApp.formatMoney(item.getPrecoUnitario())
                    + " | Subtotal: " + EcommerceConsoleApp.formatMoney(subtotal));
        }

        BigDecimal total = pedidoAppService.calcularValorTotal(pedidoId);
        System.out.println("Valor Total: " + EcommerceConsoleApp.formatMoney(total));
    }

    @Override
    public String getTitle() {
        return "Ver Detalhes";
    }
}