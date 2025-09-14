package src.main.java.br.com.tech.ecommerce.domain.service;

import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPagamento;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Objects;

public class PedidoServico {

    public void adicionarItem(Pedido pedido, ItemPedido item) {
        validarPedidoAberto(pedido);
        Objects.requireNonNull(item, "Item não pode ser nulo");
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (item.getPrecoUnitario() == null || item.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior que zero");
        }
        pedido.getItens().add(item);
    }

    public void alterarQuantidade(Pedido pedido, String itemId, int novaQuantidade) {
        validarPedidoAberto(pedido);
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        ItemPedido item = localizarItemObrigatorio(pedido, itemId);
        item.setQuantidade(novaQuantidade);
    }

    public void removerItem(Pedido pedido, String itemId) {
        validarPedidoAberto(pedido);
        boolean removido = false;
        Iterator<ItemPedido> it = pedido.getItens().iterator();
        while (it.hasNext()) {
            if (Objects.equals(it.next().getId(), itemId)) {
                it.remove();
                removido = true;
                break;
            }
        }
        if (!removido) {
            throw new IllegalArgumentException("Item não encontrado no pedido");
        }
    }

    public BigDecimal calcularValorTotal(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        return pedido.getItens().stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void finalizarPedido(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        if (pedido.getStatusPedido() != StatusPedido.ABERTO) {
            throw new IllegalStateException("Apenas pedidos ABERTOS podem ser finalizados");
        }
        BigDecimal total = calcularValorTotal(pedido);
        if (pedido.getItens().isEmpty() || total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Pedido deve ter ao menos um item e total maior que zero");
        }
        pedido.setStatusPagamento(StatusPagamento.AGUARDANDO);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
    }

    public void pagar(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        if (pedido.getStatusPagamento() != StatusPagamento.AGUARDANDO
                || pedido.getStatusPedido() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Pagamento só pode ocorrer quando status é AGUARDANDO PAGAMENTO");
        }
        pedido.setStatusPagamento(StatusPagamento.PAGO);
        pedido.setStatusPedido(StatusPedido.PAGO);
    }

    public void entregar(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        if (pedido.getStatusPagamento() != StatusPagamento.PAGO) {
            throw new IllegalStateException("Entrega só pode ocorrer após o pagamento");
        }
        pedido.setStatusPedido(StatusPedido.FINALIZADO);
    }

    private void validarPedidoAberto(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        if (pedido.getStatusPedido() != StatusPedido.ABERTO) {
            throw new IllegalStateException("Operação permitida apenas para pedidos ABERTOS");
        }
    }

    private ItemPedido localizarItemObrigatorio(Pedido pedido, String itemId) {
        return pedido.getItens().stream()
                .filter(i -> Objects.equals(i.getId(), itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado no pedido"));
    }
}