package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.List;

public class ListarProdutosAction implements MenuAction {

    private final ProdutoAppService produtoAppService;

    public ListarProdutosAction(ProdutoAppService produtoAppService) {
        this.produtoAppService = produtoAppService;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Produtos ---");

        List<Produto> produtos = produtoAppService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("⚠ Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            String uuidAbreviado = abreviarUUID(p.getId());
            String status = p.isAtivo() ? "Ativo" : "Inativo";

            System.out.println(
                    (i + 1) + " (" + uuidAbreviado + ")" +
                            " | Nome: " + p.getNome() +
                            " | Preço: " + EcommerceConsoleApp.formatMoney(p.getPrecoBase()) +
                            " | Status: " + status
            );
        }
    }

    private String abreviarUUID(String uuid) {
        if (uuid == null || uuid.length() < 8) return uuid;
        return uuid.substring(0, 8) + "...";
    }

    @Override
    public String getTitle() {
        return "Listar Produtos";
    }
}