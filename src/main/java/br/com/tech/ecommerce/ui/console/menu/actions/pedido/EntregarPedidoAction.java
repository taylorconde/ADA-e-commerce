package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class EntregarPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;

    public EntregarPedidoAction(String pedidoId, PedidoAppService pedidoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
    }

    @Override
    public void execute() {
        try {
            pedidoAppService.entregarPedido(pedidoId);
            System.out.println("✅ Pedido entregue!");
        } catch (IllegalStateException e) {
            System.out.println("Entrega só pode ocorrer após o pagamento");
        }
    }

    @Override
    public String getTitle() {
        return "Entregar Pedido";
    }
}