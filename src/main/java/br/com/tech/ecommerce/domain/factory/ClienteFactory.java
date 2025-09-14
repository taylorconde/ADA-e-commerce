package src.main.java.br.com.tech.ecommerce.domain.factory;

import src.main.java.br.com.tech.ecommerce.domain.model.Cliente;
import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;

import java.util.UUID;

public class ClienteFactory {

    public static Cliente criar(String nome, String documentoId, TipoDocumento tipoDocumento, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        String id = UUID.randomUUID().toString();
        return new Cliente(id, nome, documentoId, tipoDocumento, email);
    }
}