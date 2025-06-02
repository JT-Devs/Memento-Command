package calculadora.command;

/**
 *
 * @author vedz11
 */
import calculadora.memento.*;

public class Invoker {
    private final Calculator calculator;
    private final History history;

    public Invoker(Calculator calculator, History history) {
        this.calculator = calculator;
        this.history = history;
    }

    public void executeCommand(Command command) {
        // Guardar estado previo antes de ejecutar
        history.save(calculator.save());
        command.execute();
    }

    public void undo() {
        if (history.canUndo()) {
            CalculatorMemento m = history.undo(calculator);
            calculator.restore(m);
        } else {
            System.out.println("No hay acciones para deshacer.");
        }
}

    public void redo() {
        if (history.canRedo()) {
            CalculatorMemento m = history.redo(calculator);
            calculator.restore(m);
        } else {
            System.out.println("No hay acciones para rehacer.");
        }
    }

}