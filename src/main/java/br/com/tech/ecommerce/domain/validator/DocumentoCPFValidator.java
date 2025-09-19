package src.main.java.br.com.tech.ecommerce.domain.validator;

public class DocumentoCPFValidator {

    public static boolean isValido(String cpf) {
        if (cpf == null) return false;
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) return false;

        // Rejeita CPFs com todos os dígitos iguais
        if (digits.matches("(\\d)\\1{10}")) return false;

        // Validação dos dígitos verificadores
        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += (digits.charAt(i) - '0') * (10 - i);
            int check1 = 11 - (sum % 11);
            if (check1 >= 10) check1 = 0;
            if (check1 != (digits.charAt(9) - '0')) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) sum += (digits.charAt(i) - '0') * (11 - i);
            int check2 = 11 - (sum % 11);
            if (check2 >= 10) check2 = 0;
            return check2 == (digits.charAt(10) - '0');
        } catch (NumberFormatException e) {
            return false;
        }
    }
}