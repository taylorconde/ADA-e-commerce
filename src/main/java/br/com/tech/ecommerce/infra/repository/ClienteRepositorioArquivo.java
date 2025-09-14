package src.main.java.br.com.tech.ecommerce.infra.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.repository.ClienteRepositorio;
import src.main.java.br.com.tech.ecommerce.infra.util.ArquivoUtil;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class ClienteRepositorioArquivo implements ClienteRepositorio {

    private final File arquivo;

    public ClienteRepositorioArquivo(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
    }

    @Override
    public void salvar(Cliente cliente) {
        List<Cliente> clientes = ArquivoUtil.lerListaDeArquivo(arquivo);
        clientes.removeIf(c -> c.getId().equals(cliente.getId()));
        clientes.add(cliente);
        ArquivoUtil.gravarListaEmArquivo(clientes, arquivo);
    }

    @Override
    public Optional<Cliente> buscarPorId(String id) {
        return ArquivoUtil.<Cliente>lerListaDeArquivo(arquivo).stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Cliente> listarTodos() {
        return ArquivoUtil.lerListaDeArquivo(arquivo);
    }
}
