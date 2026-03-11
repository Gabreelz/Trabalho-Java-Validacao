public class ValidacaoEmail {

    // Se o email estiver errado a função retorna o motivo por estar invalido

    public static String validar(String email) {
        if (email == null || email.isBlank()) {
            return "O e-mail não pode estar vazio";
        }

        if (email.contains(" ")) {
            return "O e-mail não pode conter espaços em branco";
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
            return "O nome de usuário (antes do @) está vazio";
        }

        if (partes.length < 2 || partes[1].isEmpty()) {
            return "O domínio (depois do @) está vazio";
        }

        String dominio = partes[1];
        if (!dominio.contains(".")) {
            return "O domínio deve conter um ponto (ex: .com ou .com.br)";
        }

        if (dominio.startsWith(".") || dominio.endsWith(".")) {
            return "O domínio não pode começar ou terminar com ponto.";
        }

        String[] partesDominio = dominio.split("\\.");
        String ultimaExtensao = partesDominio[partesDominio.length - 1];
        if (ultimaExtensao.length() < 2) {
            return "A extensão final do domínio deve ter pelo menos 2 caracteres.";
        }

        return "E-mail válido!";
    }

}