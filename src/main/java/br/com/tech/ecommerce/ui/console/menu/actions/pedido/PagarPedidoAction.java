package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class PagarPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;

    public PagarPedidoAction(String pedidoId, PedidoAppService pedidoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
    }

    @Override
    public void execute() {
        pedidoAppService.pagarPedido(pedidoId);
        System.out.println("✅ Pedido pago!");
    }

    @Override
    public String getTitle() {
        return "Pagar Pedido";
    }
}