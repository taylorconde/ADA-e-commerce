package src.main.java.br.com.tech.ecommerce.domain.validator;

public class DocumentoCPFValidator {

    public static boolean isValido(String cpf) {
        if (cpf == null) return false;
        String digits = cpf.replaceAll("\\D", "");
        return digits.length() == 11;
        // Aqui você pode adicionar a lógica completa de validação de CPF se quiser
    }
}