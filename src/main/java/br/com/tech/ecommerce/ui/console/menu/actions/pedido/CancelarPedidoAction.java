package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.ui.console.VoltarMenuPrincipalException;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.Scanner;

public class CancelarPedidoAction implements MenuAction {

    private final String pedidoId;
    private final PedidoAppService pedidoAppService;

    public CancelarPedidoAction(String pedidoId, PedidoAppService pedidoAppService) {
        this.pedidoId = pedidoId;
        this.pedidoAppService = pedidoAppService;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Tem certeza que deseja cancelar o pedido? (S/N): ");
        String confirmacao = sc.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            pedidoAppService.cancelarPedido(pedidoId);
            System.out.println("✅ Pedido cancelado com sucesso!");
            throw new VoltarMenuPrincipalException(); // força retorno ao menu inicial
        } else {
            System.out.println("❌ Cancelamento abortado.");
        }
    }

    @Override
    public String getTitle() {
        return "Cancelar Pedido";
    }
}