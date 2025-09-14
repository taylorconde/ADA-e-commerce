package src.main.java.br.com.tech.ecommerce.ui.console;

import src.main.java.br.com.tech.ecommerce.application.PedidoAppService;
import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.infra.repository.PedidoRepositorioArquivo;

import java.math.BigDecimal;
import java.util.Scanner;

import static src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp.formatMoney;
import static src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp.formatDate;

public class MenuPedido {

    public static void exibir(Scanner sc, String pedidoId,
                              PedidoAppService pedidoAppService,
                              PedidoRepositorioArquivo pedidoRepo,
                              ProdutoAppService produtoAppService) {

        int opcaoPedido;
        do {
            System.out.println("\n--- Menu do Pedido ---");
            System.out.println("1 - Adicionar Item");
            System.out.println("2 - Remover Item");
            System.out.println("3 - Finalizar Pedido");
            System.out.println("4 - Pagar Pedido");
            System.out.println("5 - Entregar Pedido");
            System.out.println("6 - Cancelar Pedido");
            System.out.println("7 - Ver Detalhes");
            System.out.println("8 - Listar Produtos Cadastrados"); // <-- nova função
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opcaoPedido = Integer.parseInt(sc.nextLine());

            try {
                switch (opcaoPedido) {
                    case 1 -> {
                        System.out.print("ID do produto: ");
                        String produtoId = sc.nextLine();
                        System.out.print("Quantidade: ");
                        int qtd = Integer.parseInt(sc.nextLine());
                        System.out.print("Preço unitário: ");
                        BigDecimal precoUnit = new BigDecimal(sc.nextLine());
                        pedidoAppService.adicionarItem(pedidoId, produtoId, qtd, precoUnit);
                        System.out.println("✅ Item adicionado com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("ID do produto a remover: ");
                        String produtoId = sc.nextLine();
                        pedidoAppService.removerItem(pedidoId, produtoId);
                        System.out.println("🗑 Item removido com sucesso!");
                    }
                    case 3 -> {
                        pedidoAppService.finalizarPedido(pedidoId);
                        System.out.println("✅ Pedido finalizado!");
                    }
                    case 4 -> {
                        pedidoAppService.pagarPedido(pedidoId);
                        System.out.println("✅ Pedido pago!");
                    }
                    case 5 -> {
                        pedidoAppService.entregarPedido(pedidoId);
                        System.out.println("✅ Pedido entregue!");
                    }
                    case 6 -> {
                        pedidoAppService.cancelarPedido(pedidoId);
                        System.out.println("🚫 Pedido cancelado!");
                        opcaoPedido = 0;
                    }
                    case 7 -> {
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
                    case 8 -> { // <-- Listar produtos cadastrados
                        System.out.println("\n--- Produtos Disponíveis ---");
                        for (Produto p : produtoAppService.listarTodos()) {
                            System.out.println("ID: " + p.getId()
                                    + " | Nome: " + p.getNome()
                                    + " | Preço: " + formatMoney(p.getPrecoBase())
                                    + " | Ativo: " + p.isAtivo());
                        }
                    }
                    case 0 -> System.out.println("↩ Voltando ao menu principal...");
                    default -> System.out.println("❌ Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("⚠ Erro: " + e.getMessage());
            }

        } while (opcaoPedido != 0);
    }
}