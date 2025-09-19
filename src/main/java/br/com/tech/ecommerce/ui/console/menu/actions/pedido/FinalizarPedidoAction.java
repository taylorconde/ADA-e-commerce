package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class FinalizarPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;

    public FinalizarPedidoAction(String pedidoId, PedidoAppService pedidoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
    }

    @Override
    public void execute() {
        try {
            pedidoAppService.finalizarPedido(pedidoId);
            System.out.println("✅ Pedido finalizado com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println("❌ Não foi possível finalizar o pedido: " + e.getMessage());
        }
    }

    @Override
    public String getTitle() {
        return "Finalizar Pedido";
    }
}