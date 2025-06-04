package calculadora.command;

/**
 *
 * @author vedz11
 */
import calculadora.memento.*;

public class ClearCommand implements Command {
    private final Calculator calculator;
    private CalculatorMemento previousState;

    public ClearCommand(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void execute() {
        previousState = calculator.save();
        calculator.clear();
    }

    @Override
    public void undo() {
        calculator.restore(previousState);
    }
}