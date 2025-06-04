package calculadora.command;

/**
 *
 * @author vedz11
 */
import calculadora.memento.*;

public class DivideCommand implements Command {
    private final Calculator calculator;
    private final double value;
    private CalculatorMemento previousState;

    public DivideCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        previousState = calculator.save();
        calculator.divide(value);
    }

    @Override
    public void undo() {
        calculator.restore(previousState);
    }
}