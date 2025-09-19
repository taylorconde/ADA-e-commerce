package src.main.java.br.com.tech.ecommerce.domain.validator;

import src.main.java.br.com.tech.ecommerce.domain.model.enums.TipoDocumento;

public class TipoDocumentoValidator {

    public static TipoDocumento parse(String entrada) {
        if (entrada == null) return null;
        try {
            return TipoDocumento.valueOf(entrada.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}