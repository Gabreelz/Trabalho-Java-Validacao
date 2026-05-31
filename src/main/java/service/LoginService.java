package service;

/**
 * Serviço de autenticação/login.
 * Estrutura: service/LoginService.java
 * (Portado de Login.java da 1ª entrega)
 */
public class LoginService {

    private static final String USUARIO_PADRAO = "admin";
    private static final String SENHA_PADRAO = "Senha1@";

    public static boolean validarLogin(String usuario, String senha) {
        if (usuario == null || usuario.isBlank()) {
            return false;
        }

        if (senha == null || senha.isBlank()) {
            return false;
        }

        return usuario.equals(USUARIO_PADRAO) && senha.equals(SENHA_PADRAO);
    }
}
