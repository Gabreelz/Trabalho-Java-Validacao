package service;

import model.Cliente;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Serviço de cadastro de clientes.
 * Estrutura: service/ClienteService.java
 * (Portado de CadastroCliente.java da 1ª entrega)
 */
public class ClienteService {

    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public String cadastrar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            return "Erro: Nome obrigatorio.";
        }

        if (cliente.getDocumento() == null || cliente.getDocumento().isBlank()) {
            return "Erro: Documento obrigatorio.";
        }

        if (cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
            return "Erro: E-mail invalido.";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES, true))) {
            writer.write("------------------------------");
            writer.newLine();
            writer.write("Nome: " + cliente.getNome());
            writer.newLine();
            writer.write(cliente.getTipoDocumento() + ": " + cliente.getDocumento());
            writer.newLine();
            writer.write("Email: " + cliente.getEmail());
            writer.newLine();
            writer.write("------------------------------");
            writer.newLine();
            return "Cliente cadastrado com sucesso!";
        } catch (IOException e) {
            return "Erro ao gravar no arquivo: " + e.getMessage();
        }
    }
}
