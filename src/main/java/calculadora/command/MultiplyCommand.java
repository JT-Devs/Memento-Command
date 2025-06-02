package calculadora.command;

/**
 *
 * @author vedz11
 */
import calculadora.memento.*;

public class MultiplyCommand implements Command {
    private final Calculator calculator;
    private final double value;
    private CalculatorMemento previousState;

    public MultiplyCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        previousState = calculator.save();
        calculator.multiply(value);
    }

    @Override
    public void undo() {
        calculator.restore(previousState);
    }
}