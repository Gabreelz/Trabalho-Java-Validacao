public class Main {

    public static void main(String[] args) {

        LeitorCSV leitor = new LeitorCSV("estados.csv");

        leitor.lerEstados();
    }
}