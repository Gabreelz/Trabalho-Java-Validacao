import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LeitorCSV {

    private String caminhoArquivo;

    public LeitorCSV(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void lerEstados() {

        try (
            BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter("saida_estados.txt"))
        ) {

            String linha;

            br.readLine();

            bw.write("----- ESTADOS DO BRASIL -----");
            bw.newLine();

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(",");

                String sigla = dados[1];
                String nome = dados[2];
                String cod = dados[0];

                bw.write("Codigo IBGE: " + cod);
                bw.newLine();

                bw.write("Sigla: " + sigla);
                bw.newLine();

                bw.write("Nome: " + nome);
                bw.newLine();

                bw.write("-----------------------------");
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}