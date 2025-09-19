package src.main.java.br.com.tech.ecommerce.application;

import src.main.java.br.com.tech.ecommerce.domain.factory.ItemPedidoFactory;
import src.main.java.br.com.tech.ecommerce.domain.factory.PedidoFactory;
import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPedido;
import src.main.java.br.com.tech.ecommerce.domain.repository.ClienteRepositorio;
import src.main.java.br.com.tech.ecommerce.domain.repository.PedidoRepositorio;
import src.main.java.br.com.tech.ecommerce.domain.repository.ProdutoRepositorio;
import src.main.java.br.com.tech.ecommerce.domain.service.NotificacaoServico;
import src.main.java.br.com.tech.ecommerce.domain.service.PedidoServico;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class PedidoAppService {

    private final PedidoRepositorio pedidoRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final ProdutoRepositorio produtoRepositorio;
    private final PedidoServico pedidoServico;
    private final NotificacaoServico notificacaoServico;

    public PedidoAppService(PedidoRepositorio pedidoRepositorio,
                            ClienteRepositorio clienteRepositorio,
                            ProdutoRepositorio produtoRepositorio,
                            PedidoServico pedidoServico,
                            NotificacaoServico notificacaoServico) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.produtoRepositorio = produtoRepositorio;
        this.pedidoServico = pedidoServico;
        this.notificacaoServico = notificacaoServico;
    }

    public String criarPedido(String clienteId) {
        Cliente cliente = clienteRepositorio.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Pedido pedido = PedidoFactory.criar(cliente);
        pedidoRepositorio.salvar(pedido);

        return pedido.getId();
    }

    public void adicionarItem(String pedidoId, String produtoId, int quantidade, BigDecimal precoUnitario) {
        Pedido pedido = buscarPedidoOuErro(pedidoId);

        Produto produto = produtoRepositorio.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (!produto.isAtivo()) {
            throw new IllegalArgumentException("Produto inativo não pode ser adicionado ao pedido");
        }

        ItemPedido item = ItemPedidoFactory.criar(produto, quantidade, precoUnitario);
        pedidoServico.adicionarItem(pedido, item);

        pedidoRepositorio.salvar(pedido);
    }

    public void alterarQuantidadeItem(String pedidoId, String itemId, int novaQuantidade) {
        Pedido pedido = buscarPedidoOuErro(pedidoId);
        pedidoServico.alterarQuantidade(pedido, itemId, novaQuantidade);
        pedidoRepositorio.salvar(pedido);
    }

    public void removerItem(String pedidoId, String itemId) {
        Pedido pedido = buscarPedidoOuErro(pedidoId);
        pedidoServico.removerItem(pedido, itemId);
        pedidoRepositorio.salvar(pedido);
    }

    public BigDecimal calcularValorTotal(String pedidoId) {
        Pedido pedido = buscarPedidoOuErro(pedidoId);
        return pedidoServico.calcularValorTotal(pedido);
    }

    public void cancelarPedido(String pedidoId) {
        Pedido pedido = pedidoRepositorio.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.setStatusPedido(StatusPedido.CANCELADO);
        pedidoRepositorio.salvar(pedido);
    }

    public void finalizarPedido(String pedidoId) {
        atualizarPedidoEEnviarNotificacao(
                pedidoId,
                pedidoServico::finalizarPedido,
                "Seu pedido foi finalizado e está aguardando pagamento."
        );
    }

    public void pagarPedido(String pedidoId) {
        atualizarPedidoEEnviarNotificacao(
                pedidoId,
                pedidoServico::pagar,
                "Pagamento do pedido confirmado!"
        );
    }

    public void entregarPedido(String pedidoId) {
        atualizarPedidoEEnviarNotificacao(
                pedidoId,
                pedidoServico::entregar,
                "Seu pedido foi entregue. Status: Finalizado."
        );
    }

    private Pedido buscarPedidoOuErro(String pedidoId) {
        return pedidoRepositorio.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    private void atualizarPedidoEEnviarNotificacao(String pedidoId,
                                                   Consumer<Pedido> acaoDominio,
                                                   String mensagemNotificacao) {
        Pedido pedido = buscarPedidoOuErro(pedidoId);
        acaoDominio.accept(pedido);
        pedidoRepositorio.salvar(pedido);
        notificacaoServico.enviarNotificacao(pedido.getCliente(), mensagemNotificacao);
    }
}