package src.main.java.br.com.tech.ecommerce.domain.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido() {
    }

    public ItemPedido(String id, Produto produto, int quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}