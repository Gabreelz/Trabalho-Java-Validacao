import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public String cadastrar(String nome, String cpf, String email) {
        if (nome == null || nome.isBlank()) return "Erro: O nome é obrigatório.";

        // Validações externas
        if (!ValidacaoCPF.cpfValido(cpf)) return "Erro: CPF inválido.";
        
        String statusEmail = ValidacaoEmail.validar(email);
        if (!statusEmail.equals("E-mail válido!")) return "Erro: " + statusEmail;

        // Bloco de escrita persistente
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES, true))) {
            // Salva no formato CSV simples: Nome;CPF;Email
            writer.write(nome + ";" + cpf + ";" + email);
            writer.newLine();
            return "Cliente cadastrado e salvo com sucesso!";
        } catch (IOException e) {
            return "Erro técnico ao salvar no arquivo: " + e.getMessage();
        }
    }
}