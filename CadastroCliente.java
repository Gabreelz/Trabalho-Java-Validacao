import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CadastroCliente {

    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public String cadastrar(String nome, String documento, String email, boolean isCnpj) {
        if (nome == null || nome.isBlank()) return "Erro: Nome obrigatorio.";

        // Sem validação de CPF/CNPJ
        if (documento == null || documento.isBlank()) {
            return "Erro: Documento obrigatorio.";
        }

        // Validação simples de email
        if (email == null || !email.contains("@")) {
            return "Erro: E-mail invalido.";
        }

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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CadastroCliente cadastro = new CadastroCliente();

        System.out.println("TESTE DE CADASTRO DE CLIENTE");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.println("Tipo documento (1 - CPF | 2 - CNPJ): ");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("Documento: ");
        String documento = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        boolean isCnpj = (tipo == 2);

        String resultado = cadastro.cadastrar(nome, documento, email, isCnpj);
        System.out.println("Retorno: " + resultado);

        sc.close();
    }
}