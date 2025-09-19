package src.main.java.br.com.tech.ecommerce.domain.validator;

import java.util.regex.Pattern;

public class EmailValidator {

    // Regex simples para validar formato de email
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public static boolean isValido(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}