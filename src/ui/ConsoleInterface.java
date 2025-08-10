package ui;

import models.Conversion;
import services.CurrencyConverter;

import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner;
    private final CurrencyConverter converter;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
        this.converter = new CurrencyConverter();
    }

    public void run() {
        showWelcome();

        while (true) {
            try {
                showMainMenu();
                int mainOption = getMenuOption();

                if (mainOption == 0) {
                    System.out.println("Obrigado por usar o Conversor de Moedas! Até logo!");
                    break;
                }

                if (mainOption == 99) {
                    converter.showHistory();
                    continue;
                }

                showConversionMenu();
                int conversionOption = getMenuOption();

                if (conversionOption == 0) {
                    continue; // Volta ao menu principal
                }

                double amount = getAmount();
                String result = converter.convertCurrency(conversionOption - 1, amount);
                System.out.println("\n" + result);

                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();

            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void showWelcome() {
        System.out.println("***************************************************");
        System.out.println("       Bem-vindo(a) ao Conversor de Moedas!       ");
        System.out.println("***************************************************");
    }

    private void showMainMenu() {
        System.out.println("\n=============== MENU PRINCIPAL ===============");
        System.out.println("1) Realizar Conversão de Moeda");
        System.out.println("99) Ver Histórico de Conversões");
        System.out.println("0) Sair");
        System.out.println("==============================================");
    }

    private void showConversionMenu() {
        System.out.println("\n=============== CONVERSÕES DISPONÍVEIS ===============");
        List<Conversion> conversions = converter.getAvailableConversions();
        for (int i = 0; i < conversions.size(); i++) {
            System.out.println((i + 1) + ") " + conversions.get(i).getDescription());
        }
        System.out.println("0) Voltar ao Menu Principal");
        System.out.println("=====================================================");
    }

    private int getMenuOption() {
        System.out.print("\nDigite a opção desejada: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número válido: ");
            scanner.next();
        }
        int option = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        return option;
    }

    private double getAmount() {
        System.out.print("\nDigite o valor que deseja converter: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, digite um valor numérico válido: ");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Limpa o buffer
        return amount;
    }
}
