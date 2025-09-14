package src.main.java.br.com.tech.ecommerce.domain.factory;

import src.main.java.br.com.tech.ecommerce.domain.model.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProdutoFactory {

    public static Produto criarProduto(String nome, String descricao, BigDecimal precoBase, boolean ativo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        String id = UUID.randomUUID().toString();
        return new Produto(id, nome, descricao, precoBase, ativo);
    }
}