package src.main.java.br.com.tech.ecommerce.infra.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.domain.repository.ProdutoRepositorio;
import src.main.java.br.com.tech.ecommerce.infra.util.ArquivoUtil;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class ProdutoRepositorioArquivo implements ProdutoRepositorio {

    private final File arquivo;

    public ProdutoRepositorioArquivo(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
    }

    @Override
    public void salvar(Produto produto) {
        List<Produto> produtos = listarTodos();
        produtos.removeIf(p -> p.getId().equals(produto.getId()));
        produtos.add(produto);
        ArquivoUtil.gravarListaEmArquivo(produtos, arquivo);
    }
    @Override
    public Optional<Produto> buscarPorId(String id) {
        return ArquivoUtil.<Produto>lerListaDeArquivo(arquivo).stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Produto> listarTodos() {
        return ArquivoUtil.lerListaDeArquivo(arquivo);
    }
}
