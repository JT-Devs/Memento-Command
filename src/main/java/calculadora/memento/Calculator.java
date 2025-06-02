package calculadora.memento;

/**
 *
 * @author vedz11
 */
public class Calculator {
    private double currentValue = 0.0;

    public void add(double value) {
        currentValue += value;
    }

    public void subtract(double value) {
        currentValue -= value;
    }

    public void multiply(double value) {
        currentValue *= value;
    }

    public void divide(double value) {
        if (value != 0) {
            currentValue /= value;
        } else {
            throw new ArithmeticException("No se puede dividir por cero.");
        }
    }

    public void clear() {
        currentValue = 0.0;
    }

    public double getValue() {
        return currentValue;
    }

    public CalculatorMemento save() {
        return new CalculatorMemento(currentValue);
    }

    public void restore(CalculatorMemento memento) {
        this.currentValue = memento.getState();
    }
}
