import ui.ConsoleInterface;

/**
 * Classe principal do Conversor de Moedas
 * Utiliza a ExchangeRate-API para obter cotações em tempo real
 *
 * @author Projeto Alura
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            ConsoleInterface ui = new ConsoleInterface();
            ui.run();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar a aplicação: " + e.getMessage());
            System.exit(1);
        }
    }
}
