package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuController;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuControllerImpl;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.pedido.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CriarPedidoAction implements MenuAction {

    private final ClienteAppService clienteAppService;
    private final PedidoAppService pedidoAppService;
    private final ProdutoAppService produtoAppService;
    private final PedidoRepositorioArquivo pedidoRepo;

    public CriarPedidoAction(ClienteAppService clienteAppService,
                             PedidoAppService pedidoAppService,
                             ProdutoAppService produtoAppService,
                             PedidoRepositorioArquivo pedidoRepo) {
        this.clienteAppService = clienteAppService;
        this.pedidoAppService = pedidoAppService;
        this.produtoAppService = produtoAppService;
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);

        // Lista clientes com índice
        List<Cliente> clientes = clienteAppService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("⚠ Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n--- Clientes ---");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNome());
        }
        System.out.print("Escolha o cliente pelo número: ");
        int escolha = Integer.parseInt(sc.nextLine());
        String clienteId = clientes.get(escolha - 1).getId();

        // Cria pedido
        String pedidoId = pedidoAppService.criarPedido(clienteId);
        System.out.println("✅ Pedido criado com sucesso! ID: " + pedidoId);

        // Monta menu do pedido
        Map<Integer, MenuAction> pedidoMenuOptions = new LinkedHashMap<>();
        pedidoMenuOptions.put(1, new AdicionarItemPedidoAction(pedidoId, pedidoAppService, produtoAppService));
        pedidoMenuOptions.put(2, new AlterarQuantidadeItemPedidoAction(pedidoId, pedidoAppService, pedidoRepo));
        pedidoMenuOptions.put(3, new RemoverItemPedidoAction(pedidoId, pedidoAppService, produtoAppService, pedidoRepo));
        pedidoMenuOptions.put(4, new FinalizarPedidoAction(pedidoId, pedidoAppService));
        pedidoMenuOptions.put(5, new PagarPedidoAction(pedidoId, pedidoAppService));
        pedidoMenuOptions.put(6, new EntregarPedidoAction(pedidoId, pedidoAppService));
        pedidoMenuOptions.put(7, new CancelarPedidoAction(pedidoId, pedidoAppService));
        pedidoMenuOptions.put(8, new VerDetalhesPedidoAction(pedidoId, pedidoRepo, pedidoAppService));
        pedidoMenuOptions.put(9, new ListarProdutosAction(produtoAppService));

        MenuController pedidoMenu = new MenuControllerImpl(pedidoMenuOptions);
        pedidoMenu.exibir();
    }

    @Override
    public String getTitle() {
        return "Criar Pedido";
    }
}