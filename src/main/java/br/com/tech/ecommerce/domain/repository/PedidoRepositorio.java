package src.main.java.br.com.tech.ecommerce.domain.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositorio {
    void salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
    List<Pedido> listarTodos();
}