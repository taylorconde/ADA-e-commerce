package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class ListarPedidosAction implements MenuAction {

    private final PedidoRepositorioArquivo pedidoRepo;

    public ListarPedidosAction(PedidoRepositorioArquivo pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Pedidos ---");
        for (Pedido p : pedidoRepo.listarTodos()) {
            System.out.println("ID: " + p.getId() +
                    " | Cliente: " + p.getCliente().getNome() +
                    " | Data: " + EcommerceConsoleApp.formatDate(p.getDataCriacao()));
        }
    }

    @Override
    public String getTitle() {
        return "Listar Pedidos";
    }
}