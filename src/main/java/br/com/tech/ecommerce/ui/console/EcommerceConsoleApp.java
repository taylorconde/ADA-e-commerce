package src.main.java.br.com.tech.ecommerce.ui.console;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.service.PedidoServico;
import src.main.java.br.com.tech.ecommerce.infra.repository.ClienteRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.repository.ProdutoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.service.NotificacaoServicoConsole;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuController;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuControllerImpl;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.actions.*;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class EcommerceConsoleApp {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final NumberFormat NF_CURRENCY = NumberFormat.getCurrencyInstance(LOCALE_BR);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String formatMoney(BigDecimal v) {
        return NF_CURRENCY.format(v);
    }

    public static String formatDate(java.time.LocalDateTime dt) {
        return dt == null ? "-" : dt.format(DTF);
    }

    public static void main(String[] args) {

        // Infra - cria pasta data no mesmo nível de src
        String dataDir = System.getProperty("user.dir") + File.separator + "data";
        new File(dataDir).mkdirs();

        var clienteRepo = new ClienteRepositorioArquivo(dataDir + File.separator + "clientes.dat");
        var produtoRepo = new ProdutoRepositorioArquivo(dataDir + File.separator + "produtos.dat");
        var pedidoRepo  = new PedidoRepositorioArquivo(dataDir + File.separator + "pedidos.dat");

        var pedidoServico = new PedidoServico();
        var notificacaoServico = new NotificacaoServicoConsole();

        // AppServices
        var clienteAppService = new ClienteAppService(clienteRepo);
        var produtoAppService = new ProdutoAppService(produtoRepo);
        var pedidoAppService = new PedidoAppService(
                pedidoRepo,
                clienteRepo,
                produtoRepo,
                pedidoServico,
                notificacaoServico
        );

        // Menu principal
        Map<Integer, MenuAction> mainMenuOptions = new LinkedHashMap<>();
        mainMenuOptions.put(1, new CadastrarClienteAction(clienteAppService));
        mainMenuOptions.put(2, new CadastrarProdutoAction(produtoAppService));
        mainMenuOptions.put(3, new CriarPedidoAction(clienteAppService, pedidoAppService, produtoAppService, pedidoRepo));
        mainMenuOptions.put(4, new ListarClientesAction(clienteAppService));
        mainMenuOptions.put(5, new ListarProdutosAction(produtoAppService));
        mainMenuOptions.put(6, new ListarPedidosAction(pedidoRepo));

        // Cria e exibe o menu principal
        MenuController mainMenu = new MenuControllerImpl(mainMenuOptions);

        while (true) {
            try {
                mainMenu.exibir();
                break;
            } catch (VoltarMenuPrincipalException e) {}
        }
    }
}