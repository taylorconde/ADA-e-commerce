package src.main.java.br.com.tech.ecommerce.domain.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepositorio {
    void salvar(Produto produto);
    Optional<Produto> buscarPorId(String id);
    List<Produto> listarTodos();
}