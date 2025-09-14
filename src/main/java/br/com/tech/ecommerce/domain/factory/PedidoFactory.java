package src.main.java.br.com.tech.ecommerce.domain.factory;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPagamento;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PedidoFactory {

    public static Pedido criar(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.ABERTO);
        pedido.setStatusPagamento(StatusPagamento.AGUARDANDO);
        pedido.setItens(new ArrayList<>());
        return pedido;
    }

    public static Pedido criarComItens(Cliente cliente, List<ItemPedido> itens) {
        Pedido pedido = criar(cliente);
        pedido.setItens(itens != null ? itens : new ArrayList<>());
        return pedido;
    }
}
