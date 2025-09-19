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

        // Nome
        String nome;
        while (true) {
            System.out.print("Nome do produto: ");
            nome = sc.nextLine().trim();
            if (!nome.isEmpty() && nome.length() >= 2) break;
            System.out.println("❌ Nome inválido. Digite pelo menos 2 caracteres.");
        }

        // Descrição
        String descricao;
        while (true) {
            System.out.print("Descrição: ");
            descricao = sc.nextLine().trim();
            if (!descricao.isEmpty() && descricao.length() >= 5) break;
            System.out.println("❌ Descrição inválida. Digite pelo menos 5 caracteres.");
        }

        // Preço base
        BigDecimal precoBase;
        while (true) {
            System.out.print("Preço base: ");
            try {
                precoBase = new BigDecimal(sc.nextLine().trim());
                if (precoBase.compareTo(BigDecimal.ZERO) > 0) break;
                System.out.println("❌ O preço deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Valor inválido. Digite um número válido.");
            }
        }

        // Status ativo
        Boolean ativo = null;
        while (ativo == null) {
            System.out.print("Ativo? (true/false): ");
            String entrada = sc.nextLine().trim().toLowerCase();
            if (entrada.equals("true") || entrada.equals("false")) {
                ativo = Boolean.parseBoolean(entrada);
            } else {
                System.out.println("❌ Digite apenas 'true' ou 'false'.");
            }
        }

        // Criação e cadastro
        Produto produto = new Produto(UUID.randomUUID().toString(), nome, descricao, precoBase, ativo);
        produtoAppService.cadastrarProduto(produto);
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    @Override
    public String getTitle() {
        return "Cadastrar Produto";
    }
}