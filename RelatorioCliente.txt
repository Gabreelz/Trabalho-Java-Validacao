import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RelatorioCliente {

    public static void mostrarClientes() {

        try (BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"))) {

            String linha;

            System.out.println("===== CLIENTES CADASTRADOS =====");

            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }

            System.out.println("===== FIM DA LISTA =====");

        } catch (IOException e) {
            System.out.println("Erro ao ler clientes: " + e.getMessage());
        }
    }
}