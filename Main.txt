import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CadastroCliente cadastro = new CadastroCliente();

        boolean logado = false;

     while (!logado) {

            System.out.println(" LOGIN ");

            System.out.print("Usuario: ");
            String usuario = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            if (Login.validarLogin(usuario, senha)) {
                logado = true;
                System.out.println("Login realizado com sucesso!");
            } else {
                System.out.println("Usuario ou senha invalidos.\n");
            }
        }

        int opcao;

        // MENU DO SISTEMA
        do {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Ler Estados do CSV");
            System.out.println("3 - Gerar Relatorio de Clientes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.println("Tipo documento:");
                    System.out.println("1 - CPF");
                    System.out.println("2 - CNPJ");

                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Documento: ");
                    String documento = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    boolean isCnpj = (tipo == 2);

                    String resultado = cadastro.cadastrar(nome, documento, email, isCnpj);

                    System.out.println(resultado);

                    break;

                case 2:

                    LeitorCSV leitor = new LeitorCSV("estados.csv");
                    leitor.lerEstados();

                    System.out.println("CSV processado!");

                    break;

                case 3:

                    RelatorioCliente.mostrarClientes();

                    break;

                case 0:

                    System.out.println("Sistema encerrado.");

                    break;

                default:

                    System.out.println("Opcao invalida.");

            }

        } while (opcao != 0);

        sc.close();
    }
}