import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCSV {

    private String caminhoArquivo;

    public LeitorCSV(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void lerEstados() {

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            String linha;

            // pula o cabeçalho
            br.readLine();

            System.out.println("----- ESTADOS DO BRASIL -----");

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(",");

                String sigla = dados[0];
                String nome = dados[1];
                String descricao = dados[2];

                System.out.println("Sigla: " + sigla);
                System.out.println("Nome: " + nome);
                System.out.println("Descricao: " + descricao);
                System.out.println("-----------------------------");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}