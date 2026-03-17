import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidacaoCNPJ {

    private static boolean SequenciaRepetida(String cnpj) {
        return cnpj.chars().allMatch(c -> c == cnpj.charAt(0));
    }

    private static char calcularDigito(String cnpj, int pesoInicial, int limite) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < limite; i++) {
            int num = (int) (cnpj.charAt(i) - 48);
            soma += (num * peso);
            peso = (peso == 2) ? 9 : peso - 1;
        }

        int resto = soma % 11;
        return (resto < 2) ? '0' : (char) ((11 - resto) + 48);
    }

    public static boolean cnpjValido(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14 || SequenciaRepetida(cnpj)) {
            return false;
        }

        try {
            char dig13 = calcularDigito(cnpj, 5, 12);
            char dig14 = calcularDigito(cnpj, 6, 13);

            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("TESTE DE VALIDACAO DE CNPJ");
        System.out.print("Digite o CNPJ: ");
        String cnpjTeste = sc.nextLine();
        
        boolean resultado = cnpjValido(cnpjTeste);
        System.out.println("O CNPJ digitado e valido? " + (resultado ? "SIM" : "NAO"));
        
        sc.close();
    }
}