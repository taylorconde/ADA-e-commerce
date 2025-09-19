package src.main.java.br.com.tech.ecommerce.domain.validator;

public class NomeValidator {

    // Aceita letras (com acentos), espaços e hífen
    private static final String REGEX_NOME = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:[\\s'-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$";

    public static boolean isValido(String nome) {
        if (nome == null) return false;
        String trimmed = nome.trim();
        if (trimmed.length() < 2) return false; // mínimo de 2 caracteres
        return trimmed.matches(REGEX_NOME);
    }
}