import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CadastroCliente cadastro = new CadastroCliente();

        boolean logado = false;

        int opcao;

        // MENU DO SISTEMA
        do {
            System.out.println("\n===== MENU DE TESTES E SISTEMA =====");
            System.out.println("1 - Testar apenas Validacao de E-mail");
            System.out.println("2 - Testar apenas Validacao de CPF");
            System.out.println("3 - Testar apenas Validacao de CNPJ");
            System.out.println("4 - Testar Cadastro Completo de Cliente (Gravar no TXT)");
            System.out.println("5 - Ler Estados do CSV");
            System.out.println("6 - Gerar Relatorio de Clientes");
            System.out.println("7 - Realizar Login");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {

                case 1:
                    System.out.print("Digite o e-mail para testar: ");
                    String emailTeste = sc.nextLine();
                    String resultadoEmail = ValidacaoEmail.validar(emailTeste);
                    System.out.println("Resultado da Validacao: " + resultadoEmail);
                    break;

                case 2:
                    System.out.print("Digite o CPF para testar: ");
                    String cpfTeste = sc.nextLine();
                    if (ValidacaoCPF.cpfValido(cpfTeste)) {
                        System.out.println("Resultado da Validacao: CPF Valido!");
                    } else {
                        System.out.println("Resultado da Validacao: CPF Invalido!");
                    }
                    break;

                case 3:
                    System.out.print("Digite o CNPJ para testar: ");
                    String cnpjTeste = sc.nextLine();
                    if (ValidacaoCNPJ.cnpjValido(cnpjTeste)) {
                        System.out.println("Resultado da Validacao: CNPJ Valido!");
                    } else {
                        System.out.println("Resultado da Validacao: CNPJ Invalido!");
                    }
                    break;

                case 4:
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

                    String resultadoCadastro = cadastro.cadastrar(nome, documento, email, isCnpj);
                    System.out.println(resultadoCadastro);
                    break;

                case 5:
                    LeitorCSV leitor = new LeitorCSV("estados.csv");
                    leitor.lerEstados();
                    System.out.println("CSV processado!");
                    break;

                case 6:
                    RelatorioCliente.mostrarClientes();
                    break;

                case 7:
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