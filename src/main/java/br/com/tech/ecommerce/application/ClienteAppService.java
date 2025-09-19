package src.main.java.br.com.tech.ecommerce.application;

import src.main.java.br.com.tech.ecommerce.domain.factory.ClienteFactory;
import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;
import src.main.java.br.com.tech.ecommerce.domain.repository.ClienteRepositorio;

import java.util.List;

public class ClienteAppService {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteAppService(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public void cadastrar(String nome, String documento, TipoDocumento tipoDocumento, String email) {
        Cliente cliente = ClienteFactory.criar(nome, documento, tipoDocumento, email);
        clienteRepositorio.salvar(cliente);
    }

    public Cliente buscarPorId(String id) {
        return clienteRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    public List<Cliente> listarTodos() {
        return clienteRepositorio.listarTodos();
    }
}