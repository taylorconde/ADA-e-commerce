package src.main.java.br.com.tech.ecommerce.domain.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositorio {
    void salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(String id);
    List<Cliente> listarTodos();
}