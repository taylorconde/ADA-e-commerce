package src.main.java.br.com.tech.ecommerce.infra.repository;

import src.main.java.br.com.tech.ecommerce.domain.model.Pedido;
import src.main.java.br.com.tech.ecommerce.domain.repository.PedidoRepositorio;
import src.main.java.br.com.tech.ecommerce.infra.util.ArquivoUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepositorioArquivo implements PedidoRepositorio {

    private final File arquivo;

    public PedidoRepositorioArquivo(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
    }

    @Override
    public void salvar(Pedido pedido) {
        List<Pedido> pedidos = listarTodos();
        pedidos.removeIf(pedido1 -> pedido.getId().equals(pedido.getId()));
        pedidos.add(pedido);
        ArquivoUtil.gravarListaEmArquivo(pedidos, arquivo);
    }

    @Override
    public Optional<Pedido> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Pedido> listarTodos() {
        return ArquivoUtil.lerListaDeArquivo(arquivo);
    }
}
