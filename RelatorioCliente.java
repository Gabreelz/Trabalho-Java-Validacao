import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("TESTE DE RELATORIO DE CLIENTES");
        System.out.println("Pressiona ENTER para gerar o relatorio a partir do ficheiro clientes.txt...");
        sc.nextLine();
        
        mostrarClientes();
        
        sc.close();
    }
}