package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class ListarClientesAction implements MenuAction {

    private final ClienteAppService clienteAppService;

    public ListarClientesAction(ClienteAppService clienteAppService) {
        this.clienteAppService = clienteAppService;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Clientes ---");

        var clientes = clienteAppService.listarTodos();

        for (int i = 0; i < clientes.size(); i++) {
            var c = clientes.get(i);
            String uuidAbreviado = abreviarUUID(c.getId());

            System.out.println(
                    (i + 1) + " (" + uuidAbreviado + ")" +
                            " | Nome: " + c.getNome() +
                            " | Documento: " + c.getDocumentoId() +
                            " | Tipo: " + c.getTipoDocumento() +
                            " | Email: " + c.getEmail()
            );
        }
    }

    private String abreviarUUID(String uuid) {
        if (uuid == null || uuid.length() < 8) return uuid;
        return uuid.substring(0, 8) + "...";
    }

    @Override
    public String getTitle() {
        return "Listar Clientes";
    }
}