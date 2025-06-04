package calculadora.memento;

/**
 *
 * @author vedz11
 */
import java.util.Stack;

public class History {
    private final Stack<CalculatorMemento> undoStack = new Stack<>();
    private final Stack<CalculatorMemento> redoStack = new Stack<>();

    public void save(CalculatorMemento memento) {
        undoStack.push(memento);
        redoStack.clear(); // limpiamos el redo al hacer nueva operación
    }

    public CalculatorMemento undo(Calculator calculator) {
        if (!undoStack.isEmpty()) {
            // Guardar el estado actual para posible redo
            redoStack.push(calculator.save());
            return undoStack.pop();
        }
        return null;
    }

    public CalculatorMemento redo(Calculator calculator) {
        if (!redoStack.isEmpty()) {
            // Guardar el estado actual para posible undo
            undoStack.push(calculator.save());
            return redoStack.pop();
        }
        return null;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
