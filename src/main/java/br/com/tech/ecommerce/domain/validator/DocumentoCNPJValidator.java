package src.main.java.br.com.tech.ecommerce.domain.validator;

public class DocumentoCNPJValidator {

    public static boolean isValido(String cnpj) {
        if (cnpj == null) return false;
        String digits = cnpj.replaceAll("\\D", "");
        if (digits.length() != 14) return false;

        // Rejeita CNPJs com todos os dígitos iguais
        if (digits.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) soma += (digits.charAt(i) - '0') * peso1[i];
            int resto = soma % 11;
            int digito1 = (resto < 2) ? 0 : 11 - resto;

            soma = 0;
            for (int i = 0; i < 13; i++) soma += (digits.charAt(i) - '0') * peso2[i];
            resto = soma % 11;
            int digito2 = (resto < 2) ? 0 : 11 - resto;

            return digito1 == (digits.charAt(12) - '0') && digito2 == (digits.charAt(13) - '0');
        } catch (NumberFormatException e) {
            return false;
        }
    }
}