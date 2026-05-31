package service;

/**
 * Serviço de validação de E-mail.
 * Estrutura: service/ValidadorEmail.java
 * (Portado de ValidacaoEmail.java da 1ª entrega)
 */
public class ValidadorEmail {

    public static String validar(String email) {
        if (email == null || email.isBlank()) {
            return "O e-mail nao pode estar vazio";
        }

        if (email.contains(" ")) {
            return "O e-mail nao pode conter espacos em branco";
        }

        if (!email.contains("@")) {
            return "Falta o caractere '@'";
        }

        String[] partes = email.split("@");

        if (partes.length > 2) {
            return "O e-mail deve conter apenas um caractere '@'";
        }

        String usuario = partes[0];
        if (usuario.isEmpty()) {
            return "O nome de usuario (antes do @) esta vazio";
        }

        if (partes.length < 2 || partes[1].isEmpty()) {
            return "O dominio (depois do @) esta vazio";
        }

        String dominio = partes[1];
        if (!dominio.contains(".")) {
            return "O dominio deve conter um ponto (ex: .com ou .com.br)";
        }

        if (dominio.startsWith(".") || dominio.endsWith(".")) {
            return "O dominio nao pode comecar ou terminar com ponto.";
        }

        String[] partesDominio = dominio.split("\\.");
        String ultimaExtensao = partesDominio[partesDominio.length - 1];
        if (ultimaExtensao.length() < 2) {
            return "A extensao final do dominio deve ter pelo menos 2 caracteres.";
        }

        return "E-mail valido!";
    }

    public static boolean emailValido(String email) {
        return validar(email).equals("E-mail valido!");
    }
}
