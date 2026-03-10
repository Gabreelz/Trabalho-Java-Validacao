import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroCliente {
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public String cadastrar(String nome, String documento, String email, boolean isCnpj) {
        if (nome == null || nome.isBlank()) return "Erro: Nome obrigatório.";

        if (isCnpj) {
            if (!ValidacaoCNPJ.cnpjValido(documento)) return "Erro: CNPJ inválido."; //
        } else {
            if (!ValidacaoCPF.cpfValido(documento)) return "Erro: CPF inválido."; //
        }
        
        String statusEmail = ValidacaoEmail.validar(email); 
        if (!statusEmail.equals("E-mail válido!")) return "Erro: " + statusEmail;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES, true))) {
            writer.write("------------------------------");
            writer.newLine();
            writer.write("Nome: " + nome);
            writer.newLine();
            writer.write((isCnpj ? "CNPJ: " : "CPF: ") + documento);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("------------------------------");
            writer.newLine();
            return "Cliente cadastrado com sucesso no arquivo TXT!";
        } catch (IOException e) {
            return "Erro ao gravar no arquivo: " + e.getMessage();
        }
    }
}