package src.main.java.br.com.tech.ecommerce.domain.validator;

public class DocumentoCNPJValidator {

    public static boolean isValido(String cnpj) {
        if (cnpj == null) return false;
        String digits = cnpj.replaceAll("\\D", "");
        return digits.length() == 14;
        // Aqui também pode entrar a lógica completa de validação de CNPJ
    }
}