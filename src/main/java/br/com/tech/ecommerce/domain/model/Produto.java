package src.main.java.br.com.tech.ecommerce.domain.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private String nome;
    private String descricao;
    private BigDecimal precoBase;
    private boolean ativo;

    public Produto(String id, String nome, String descricao, BigDecimal precoBase, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.ativo = ativo;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPrecoBase() { return precoBase; }
    public void setPrecoBase(BigDecimal precoBase) { this.precoBase = precoBase; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}