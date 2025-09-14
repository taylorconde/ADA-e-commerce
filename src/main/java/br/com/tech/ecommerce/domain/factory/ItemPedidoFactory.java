package src.main.java.br.com.tech.ecommerce.domain.factory;

import src.main.java.br.com.tech.ecommerce.domain.model.ItemPedido;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemPedidoFactory {

    public static ItemPedido criar(Produto produto, int quantidade, BigDecimal precoUnitario) {
        ItemPedido item = new ItemPedido();
        item.setId(UUID.randomUUID().toString());
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(precoUnitario);
        return item;
    }
}