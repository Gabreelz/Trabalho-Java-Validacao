import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public String cadastrar(String nome, String cpf, String email) {
        
        if (nome == null || nome.isBlank()) {
            return "Erro: O nome é obrigatório.";
        }

        if (!ValidacaoCPF.cpfValido(cpf)) {
            return "Erro: CPF informado é inválido.";
        }

        String statusEmail = ValidacaoEmail.validar(email);
        if (!statusEmail.equals("E-mail válido!")) {
            return "Erro no e-mail: " + statusEmail;
        }

        return "Dados validados. Pronto para salvar.";
    }
}