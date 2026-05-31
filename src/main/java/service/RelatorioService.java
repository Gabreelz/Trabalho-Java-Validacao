package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço de relatório de clientes.
 * Estrutura: service/RelatorioService.java
 * (Portado de RelatorioCliente.java da 1ª entrega)
 */
public class RelatorioService {

    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public static List<String> listarClientes() {
        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CLIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            linhas.add("Erro ao ler clientes: " + e.getMessage());
        }

        return linhas;
    }

    public static String getConteudo() {
        StringBuilder sb = new StringBuilder();
        for (String linha : listarClientes()) {
            sb.append(linha).append("\n");
        }
        return sb.toString();
    }
}
