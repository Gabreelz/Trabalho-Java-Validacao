public class Login {

    private static final String USUARIO_PADRAO = "admin";
    private static final String SENHA_PADRAO = "1234";

    public static boolean validarLogin(String usuario, String senha) {

        if (usuario == null || usuario.isBlank()) {
            System.out.println("Usuário não pode estar vazio.");
            return false;
        }

        if (senha == null || senha.isBlank()) {
            System.out.println("Senha não pode estar vazia.");
            return false;
        }

        if (usuario.equals(USUARIO_PADRAO) && senha.equals(SENHA_PADRAO)) {
            return true;
        }

        System.out.println("Usuário ou senha inválidos.");
        return false;
    }
}