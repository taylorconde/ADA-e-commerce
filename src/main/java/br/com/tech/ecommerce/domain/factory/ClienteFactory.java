package src.main.java.br.com.tech.ecommerce.domain.factory;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;
import src.main.java.br.com.tech.ecommerce.domain.validator.*;

import java.util.UUID;

public class ClienteFactory {

    public static Cliente criar(String nome, String documentoId, TipoDocumento tipoDocumento, String email) {
        if (!NomeValidator.isValido(nome)) {
            throw new IllegalArgumentException("Nome do cliente é inválido");
        }

        if (tipoDocumento == null) {
            throw new IllegalArgumentException("Tipo de documento é obrigatório");
        }

        boolean documentoValido = tipoDocumento == TipoDocumento.CPF
                ? DocumentoCPFValidator.isValido(documentoId)
                : DocumentoCNPJValidator.isValido(documentoId);

        if (!documentoValido) {
            throw new IllegalArgumentException("Documento inválido para o tipo informado");
        }

        if (!EmailValidator.isValido(email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        String id = UUID.randomUUID().toString();
        return new Cliente(id, nome, documentoId, tipoDocumento, email);
    }
}