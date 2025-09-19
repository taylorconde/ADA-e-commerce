package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class ListarClientesAction implements MenuAction {

    private final ClienteAppService clienteAppService;

    public ListarClientesAction(ClienteAppService clienteAppService) {
        this.clienteAppService = clienteAppService;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente c : clienteAppService.listarTodos()) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
        }
    }

    @Override
    public String getTitle() {
        return "Listar Clientes";
    }
}