import java.util.Scanner;

public class Login {

    private static final String USUARIO_PADRAO = "admin";
    private static final String SENHA_PADRAO = "Senha1@";

    public static boolean validarLogin(String usuario, String senha) {

        if (usuario == null || usuario.isBlank()) {
            System.out.println("Usuario nao pode estar vazio.");
            return false;
        }

        if (senha == null || senha.isBlank()) {
            System.out.println("Senha nao pode estar vazia.");
            return false;
        }

        if (usuario.equals(USUARIO_PADRAO) && senha.equals(SENHA_PADRAO)) {
            return true;
        }

        System.out.println("Usuario ou senha invalidos.");
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("TESTE DE LOGIN");
        System.out.print("Digite o usuario: ");
        String usuario = sc.nextLine();
        
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();
        
        System.out.println("\nVerificando credenciais...");
        boolean sucesso = validarLogin(usuario, senha);
        
        if (sucesso) {
            System.out.println("Resultado: Login Aprovado com Sucesso!");
        }
        
        sc.close();
    }
}