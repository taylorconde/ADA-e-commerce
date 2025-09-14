package src.main.java.br.com.tech.ecommerce.application;

import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.domain.repository.ProdutoRepositorio;

import java.util.List;

public class ProdutoAppService {

    private final ProdutoRepositorio produtoRepositorio;

    public ProdutoAppService(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public void cadastrarProduto(Produto produto) {
        produtoRepositorio.salvar(produto);
    }

    public Produto buscarPorId(String id) {
        return produtoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public List<Produto> listarTodos() {
        return produtoRepositorio.listarTodos();
    }
}