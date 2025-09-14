package src.main.java.br.com.tech.ecommerce.ui.console;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;
import src.main.java.br.com.tech.ecommerce.domain.service.PedidoServico;
import src.main.java.br.com.tech.ecommerce.infra.repository.ClienteRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.repository.ProdutoRepositorioArquivo;
import src.main.java.br.com.tech.ecommerce.infra.service.NotificacaoServicoConsole;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class EcommerceConsoleApp {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final NumberFormat NF_CURRENCY = NumberFormat.getCurrencyInstance(LOCALE_BR);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static String formatMoney(BigDecimal v) {
        return NF_CURRENCY.format(v);
    }

    private static String formatDate(java.time.LocalDateTime dt) {
        return dt == null ? "-" : dt.format(DTF);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

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

        int opcao;
        do {
            System.out.println("\n===== MENU E-COMMERCE =====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Produto");
            System.out.println("3 - Criar Pedido");
            System.out.println("4 - Adicionar Item ao Pedido");
            System.out.println("5 - Finalizar Pedido");
            System.out.println("6 - Pagar Pedido");
            System.out.println("7 - Entregar Pedido");
            System.out.println("8 - Listar Clientes");
            System.out.println("9 - Listar Produtos");
            System.out.println("10 - Listar Pedidos");
            System.out.println("11 - Detalhes de um Pedido");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine());

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome do cliente: ");
                        String nome = sc.nextLine();
                        System.out.print("Documento (CPF/CNPJ): ");
                        String documentoId = sc.nextLine();
                        System.out.print("Tipo de documento (CPF/CNPJ): ");
                        TipoDocumento tipoDocumento = TipoDocumento.valueOf(sc.nextLine().toUpperCase());
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        Cliente cliente = new Cliente(
                                UUID.randomUUID().toString(),
                                nome,
                                documentoId,
                                tipoDocumento,
                                email
                        );
                        clienteAppService.cadastrarCliente(cliente);
                        System.out.println("✅ Cliente cadastrado com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("Nome do produto: ");
                        String nome = sc.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = sc.nextLine();
                        System.out.print("Preço base: ");
                        BigDecimal precoBase = new BigDecimal(sc.nextLine());
                        System.out.print("Ativo? (true/false): ");
                        boolean ativo = Boolean.parseBoolean(sc.nextLine());

                        Produto produto = new Produto(
                                UUID.randomUUID().toString(),
                                nome,
                                descricao,
                                precoBase,
                                ativo
                        );
                        produtoAppService.cadastrarProduto(produto);
                        System.out.println("✅ Produto cadastrado com sucesso!");
                    }
                    case 3 -> {
                        System.out.print("ID do cliente: ");
                        String clienteId = sc.nextLine();
                        pedidoAppService.criarPedido(clienteId);
                        System.out.println("✅ Pedido criado com sucesso!");
                    }
                    case 4 -> {
                        System.out.print("ID do pedido: ");
                        String pedidoId = sc.nextLine();
                        System.out.print("ID do produto: ");
                        String produtoId = sc.nextLine();
                        System.out.print("Quantidade: ");
                        int qtd = Integer.parseInt(sc.nextLine());
                        System.out.print("Preço unitário: ");
                        BigDecimal precoUnit = new BigDecimal(sc.nextLine());
                        pedidoAppService.adicionarItem(pedidoId, produtoId, qtd, precoUnit);
                        System.out.println("✅ Item adicionado com sucesso!");
                    }
                    case 5 -> {
                        System.out.print("ID do pedido: ");
                        String pedidoId = sc.nextLine();
                        pedidoAppService.finalizarPedido(pedidoId);
                        System.out.println("✅ Pedido finalizado!");
                    }
                    case 6 -> {
                        System.out.print("ID do pedido: ");
                        String pedidoId = sc.nextLine();
                        pedidoAppService.pagarPedido(pedidoId);
                        System.out.println("✅ Pedido pago!");
                    }
                    case 7 -> {
                        System.out.print("ID do pedido: ");
                        String pedidoId = sc.nextLine();
                        pedidoAppService.entregarPedido(pedidoId);
                        System.out.println("✅ Pedido entregue!");
                    }
                    case 8 -> {
                        System.out.println("\n--- Lista de Clientes ---");
                        for (Cliente c : clienteAppService.listarTodos()) {
                            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() +
                                    " | Documento: " + c.getDocumentoId() + " (" + c.getTipoDocumento() + ")" +
                                    " | Email: " + c.getEmail());
                        }
                    }
                    case 9 -> {
                        System.out.println("\n--- Lista de Produtos ---");
                        for (Produto p : produtoAppService.listarTodos()) {
                            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() +
                                    " | Descrição: " + p.getDescricao() +
                                    " | Preço: " + formatMoney(p.getPrecoBase()) +
                                    " | Ativo: " + p.isAtivo());
                        }
                    }
                    case 10 -> {
                        System.out.println("\n--- Lista de Pedidos ---");
                        for (Pedido p : pedidoRepo.listarTodos()) {
                            System.out.println("ID: " + p.getId() +
                                    " | Cliente: " + p.getCliente().getNome() +
                                    " | Data: " + formatDate(p.getDataCriacao()) +
                                    " | Status Pedido: " + p.getStatusPedido() +
                                    " | Status Pagamento: " + p.getStatusPagamento() +
                                    " | Itens: " + p.getItens().size());
                        }
                    }
                    case 11 -> {
                        System.out.print("ID do pedido: ");
                        String pedidoId = sc.nextLine();
                        Pedido pedido = pedidoRepo.buscarPorId(pedidoId)
                                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

                        System.out.println("\n--- Detalhes do Pedido ---");
                        System.out.println("ID: " + pedido.getId());
                        System.out.println("Cliente: " + pedido.getCliente().getNome());
                        System.out.println("Data de criação: " + formatDate(pedido.getDataCriacao()));
                        System.out.println("Status Pedido: " + pedido.getStatusPedido());
                        System.out.println("Status Pagamento: " + pedido.getStatusPagamento());
                        System.out.println("Itens:");

                        BigDecimal total = BigDecimal.ZERO;
                        for (ItemPedido item : pedido.getItens()) {
                            BigDecimal subtotal = item.getPrecoUnitario()
                                    .multiply(BigDecimal.valueOf(item.getQuantidade()));
                            total = total.add(subtotal);
                            System.out.println(" - Produto: " + item.getProduto().getNome()
                                    + " | Quantidade: " + item.getQuantidade()
                                    + " | Preço Unitário: " + formatMoney(item.getPrecoUnitario())
                                    + " | Subtotal: " + formatMoney(subtotal));
                        }
                        System.out.println("Valor Total: " + formatMoney(total));
                    }
                    case 0 -> System.out.println("👋 Saindo...");
                    default -> System.out.println("❌ Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("⚠ Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }
}