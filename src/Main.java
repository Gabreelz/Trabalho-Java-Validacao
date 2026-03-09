public class Main {

    public static void main(String[] args) {

        LeitorCSV leitor = new LeitorCSV("estados.csv");

        leitor.lerEstados();
        
        String nome = "João Silva";
        String cpf = "123.456.789-09";
        String email = "joao.silva@email.com";
        
        RelatorioCliente.imprimir(nome, cpf, email);
        
    }
}