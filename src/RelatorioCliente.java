import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RelatorioCliente {

    public static void imprimir(String nome, String cpf, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorio.txt", true))) {
            writer.write("----- RELATÓRIO DO CLIENTE -----\n");
            writer.write("Nome: " + nome + "\n");
            writer.write("CPF: " + cpf + "\n");
            writer.write("E-mail: " + email + "\n");
            writer.write("-------------------------------\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
