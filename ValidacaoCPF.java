import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidacaoCPF {

    public static boolean cpfValido(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || SequenciaRepetida(cpf)) {
            return false;
        }

        try {
            char dig10 = calcularDigito(cpf, 10);
            char dig11 = calcularDigito(cpf, 11);

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    private static char calcularDigito(String cpf, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;
        int limite = pesoInicial - 1; 

        for (int i = 0; i < limite; i++) {
            int num = (int) (cpf.charAt(i) - 48); 
            soma += (num * peso);
            peso--;
        }

        int resto = 11 - (soma % 11);
        return (resto > 9) ? '0' : (char) (resto + 48);
    }

    private static boolean SequenciaRepetida(String cpf) {
        return cpf.chars().allMatch(c -> c == cpf.charAt(0));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("TESTE DE VALIDACAO DE CPF");
        System.out.print("Digite o CPF: ");
        String cpfTeste = sc.nextLine();
        
        boolean resultado = cpfValido(cpfTeste);
        System.out.println("O CPF digitado e valido? " + (resultado ? "SIM" : "NAO"));
        
        sc.close();
    }
}