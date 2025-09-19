package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.List;

public class ListarPedidosAction implements MenuAction {

    private final PedidoRepositorioArquivo pedidoRepo;

    public ListarPedidosAction(PedidoRepositorioArquivo pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Pedidos ---");

        List<Pedido> pedidos = pedidoRepo.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("⚠ Nenhum pedido encontrado.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.println("ID: " + p.getId() +
                    " | Cliente: " + p.getCliente().getNome() +
                    " | Data: " + EcommerceConsoleApp.formatDate(p.getDataCriacao()) +
                    " | Status: " + p.getStatusPedido());

            // Lista produtos do pedido
            List<ItemPedido> itens = p.getItens();
            if (itens.isEmpty()) {
                System.out.println("   (Sem produtos)");
            } else {
                for (int i = 0; i < itens.size(); i++) {
                    Produto prod = itens.get(i).getProduto();
                    System.out.println("   " + (i + 1) + " - " +
                            prod.getNome() + " | " +
                            EcommerceConsoleApp.formatMoney(prod.getPrecoBase()) + " | " +
                            prod.isAtivo());
                }
            }
            System.out.println(); // linha em branco entre pedidos
        }
    }

    @Override
    public String getTitle() {
        return "Listar Pedidos";
    }
}