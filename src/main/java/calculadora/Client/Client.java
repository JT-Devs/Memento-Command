package calculadora.Client;

import calculadora.memento.*;
import calculadora.command.*;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        History history = new History();
        Invoker invoker = new Invoker(calculator, history);
        Scanner scanner = new Scanner(System.in);

        System.out.println("🧮 Calculadora Interactiva (Command + Memento)");
        System.out.println("Comandos disponibles: sumar, restar, multiplicar, dividir, clear, undo, redo, value, exit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Saliendo...");
                break;
            }

            switch (input) {
                case "sumar":
                case "restar":
                case "multiplicar":
                case "dividir":
                    System.out.print("Valor: ");
                    double value;
                    try {
                        value = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Entrada inválida. Usa un número.");
                        break;
                    }

                    Command cmd = null;
                    switch (input) {
                        case "sumar" -> cmd = new AddCommand(calculator, value);
                        case "restar" -> cmd = new SubtractCommand(calculator, value);
                        case "multiplicar" -> cmd = new MultiplyCommand(calculator, value);
                        case "dividir" -> {
                            if (value == 0) {
                                System.out.println("⚠️ No se puede dividir por cero.");
                                continue;
                            }
                            cmd = new DivideCommand(calculator, value);
                        }
                    }
                    invoker.executeCommand(cmd);
                    break;

                case "clear":
                    invoker.executeCommand(new ClearCommand(calculator));
                    break;

                case "undo":
                    invoker.undo();
                    break;

                case "redo":
                    invoker.redo();
                    break;

                case "value":
                    System.out.println("📌 Valor actual: " + calculator.getValue());
                    break;

                default:
                    System.out.println("❓ Comando no reconocido.");
            }
        }

        scanner.close();
    }
}
