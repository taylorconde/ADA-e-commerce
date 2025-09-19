package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

public class ListarProdutosAction implements MenuAction {

    private final ProdutoAppService produtoAppService;

    public ListarProdutosAction(ProdutoAppService produtoAppService) {
        this.produtoAppService = produtoAppService;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Lista de Produtos ---");
        for (Produto p : produtoAppService.listarTodos()) {
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() +
                    " | Preço: " + EcommerceConsoleApp.formatMoney(p.getPrecoBase()));
        }
    }

    @Override
    public String getTitle() {
        return "Listar Produtos";
    }
}