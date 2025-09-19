package src.main.java.br.com.tech.ecommerce.ui.console.menu.actions;

import src.main.java.br.com.tech.ecommerce.application.ClienteAppService;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;
import src.main.java.br.com.tech.ecommerce.domain.validator.DocumentoCPFValidator;
import src.main.java.br.com.tech.ecommerce.domain.validator.DocumentoCNPJValidator;
import src.main.java.br.com.tech.ecommerce.domain.validator.NomeValidator;
import src.main.java.br.com.tech.ecommerce.domain.validator.TipoDocumentoValidator;
import src.main.java.br.com.tech.ecommerce.domain.validator.EmailValidator;
import src.main.java.br.com.tech.ecommerce.ui.console.menu.MenuAction;

import java.util.Scanner;

public class CadastrarClienteAction implements MenuAction {

    private final ClienteAppService clienteAppService;

    public CadastrarClienteAction(ClienteAppService clienteAppService) {
        this.clienteAppService = clienteAppService;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);

        // 1. Tipo de documento
        TipoDocumento tipoDocumento = null;
        while (tipoDocumento == null) {
            System.out.print("Tipo de documento (CPF/CNPJ): ");
            tipoDocumento = TipoDocumentoValidator.parse(sc.nextLine());
            if (tipoDocumento == null) {
                System.out.println("❌ Tipo inválido. Digite apenas CPF ou CNPJ.");
            }
        }

        // 2. Documento
        String documento;
        while (true) {
            System.out.print("Número do documento: ");
            documento = sc.nextLine().trim();

            boolean valido = tipoDocumento == TipoDocumento.CPF
                    ? DocumentoCPFValidator.isValido(documento)
                    : DocumentoCNPJValidator.isValido(documento);

            if (valido) break;
            System.out.println("❌ Documento inválido para o tipo " + tipoDocumento);
        }

        // 3. Nome
        String nome;
        while (true) {
            System.out.print("Nome do cliente: ");
            nome = sc.nextLine().trim();
            if (NomeValidator.isValido(nome)) break;
            System.out.println("❌ Nome inválido. Digite um nome válido.");
        }

        // 4. Email
        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            if (EmailValidator.isValido(email)) break;
            System.out.println("❌ Email inválido. Digite novamente.");
        }

        // Cadastro
        clienteAppService.cadastrar(nome, documento, tipoDocumento, email);
        System.out.println("✅ Cliente cadastrado com sucesso!");
    }

    @Override
    public String getTitle() {
        return "Cadastrar Cliente";
    }
}