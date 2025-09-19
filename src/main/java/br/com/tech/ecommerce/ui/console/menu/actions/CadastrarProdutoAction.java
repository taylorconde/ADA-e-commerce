package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ProdutoAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.Produto;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class CadastrarProdutoAction implements MenuAction {

    private final ProdutoAppService produtoAppService;

    public CadastrarProdutoAction(ProdutoAppService produtoAppService) {
        this.produtoAppService = produtoAppService;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();
        System.out.print("Preço base: ");
        BigDecimal precoBase = new BigDecimal(sc.nextLine());
        System.out.print("Ativo? (true/false): ");
        boolean ativo = Boolean.parseBoolean(sc.nextLine());

        Produto produto = new Produto(UUID.randomUUID().toString(), nome, descricao, precoBase, ativo);
        produtoAppService.cadastrarProduto(produto);
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    @Override
    public String getTitle() {
        return "Cadastrar Produto";
    }
}