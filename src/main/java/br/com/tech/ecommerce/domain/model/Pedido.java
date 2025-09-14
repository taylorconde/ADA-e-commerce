package src.main.java.br.com.tech.ecommerce.domain.model;

import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPagamento;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.StatusPedido;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private Cliente cliente;
    private LocalDateTime dataCriacao;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private List<ItemPedido> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public Pedido(String id, Cliente cliente, LocalDateTime dataCriacao,
                  StatusPedido statusPedido, StatusPagamento statusPagamento,
                  List<ItemPedido> itens) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.statusPedido = statusPedido;
        this.statusPagamento = statusPagamento;
        this.itens = (itens != null) ? itens : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public StatusPedido getStatusPedido() { return statusPedido; }
    public void setStatusPedido(StatusPedido statusPedido) { this.statusPedido = statusPedido; }

    public StatusPagamento getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(StatusPagamento statusPagamento) { this.statusPagamento = statusPagamento; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}