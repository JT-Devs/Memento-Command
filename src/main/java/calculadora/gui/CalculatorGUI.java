package calculadora.gui;

import calculadora.command.*;
import calculadora.memento.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private final Calculator calculator = new Calculator();
    private final History history = new History();
    private final Invoker invoker = new Invoker(calculator, history);
    private final JTextField display = new JTextField("0");

    // Nuevos atributos para lógica clásica
    private String pendingOperation = null;
    private double storedValue = 0.0;

    public CalculatorGUI() {
        setTitle("Calculadora con Command + Memento");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));

        // Botones numéricos
        for (int i = 1; i <= 9; i++) {
            int finalI = i;
            buttonPanel.add(createButton(String.valueOf(i), e -> appendInput(finalI)));
        }

        buttonPanel.add(createButton("0", e -> appendInput(0)));
        buttonPanel.add(createButton(".", e -> appendDot()));

        // Botones de operación (solo almacenan la operación)
        buttonPanel.add(createButton("+", e -> setPendingOperation("+")));
        buttonPanel.add(createButton("-", e -> setPendingOperation("-")));
        buttonPanel.add(createButton("×", e -> setPendingOperation("*")));
        buttonPanel.add(createButton("÷", e -> setPendingOperation("/")));

        // Botón igual (ejecuta la operación pendiente)
        buttonPanel.add(createButton("=", e -> executePendingOperation()));

        // Control: Clear, Undo, Redo
        buttonPanel.add(createButton("C", e -> {
            invoker.executeCommand(new ClearCommand(calculator));
            updateDisplay();
            resetOperation();
        }));

        buttonPanel.add(createButton("Undo", e -> {
            invoker.undo();
            updateDisplay();
            resetOperation();
        }));

        buttonPanel.add(createButton("Redo", e -> {
            invoker.redo();
            updateDisplay();
            resetOperation();
        }));

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(listener);
        return button;
    }

    private void appendInput(int digit) {
        if (display.getText().equals("0")) {
            display.setText(String.valueOf(digit));
        } else {
            display.setText(display.getText() + digit);
        }
    }

    private void appendDot() {
        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private double parseDisplayValue() {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void setPendingOperation(String op) {
        storedValue = parseDisplayValue();
        pendingOperation = op;
        display.setText("0");
    }

    private void executePendingOperation() {
        double input = parseDisplayValue();
        Command cmd = null;

        if (pendingOperation == null) {
            showMessage("No hay operación pendiente.");
            return;
        }

        switch (pendingOperation) {
            case "+" -> cmd = new AddCommand(calculator, input);
            case "-" -> cmd = new SubtractCommand(calculator, input);
            case "*" -> cmd = new MultiplyCommand(calculator, input);
            case "/" -> {
                if (input == 0) {
                    showMessage("No se puede dividir por cero.");
                    return;
                }
                cmd = new DivideCommand(calculator, input);
            }
        }

        // Primer valor se almacena manualmente (solo en primera operación)
        if (calculator.getValue() == 0 && pendingOperation != null) {
            calculator.add(storedValue);
        }

        if (cmd != null) {
            invoker.executeCommand(cmd);
            updateDisplay();
        }

        resetOperation();
    }

    private void updateDisplay() {
        double value = calculator.getValue();
        display.setText((value % 1 == 0) ? String.valueOf((int) value) : String.valueOf(value));
    }

    private void resetOperation() {
        pendingOperation = null;
        storedValue = 0.0;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorGUI::new);
    }
}
