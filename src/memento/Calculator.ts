import { CalculatorMemento } from "./CalculatorMemento";

export class Calculator {
    private currentValue: number = 0;

    add(value: number): void {
      this.currentValue += value;
    }

    subtract(value: number): void {
      this.currentValue -= value;
    }

    multiply(value: number): void {
      this.currentValue *= value;
    }

    divide(value: number): void {
      if (value !== 0) {
        this.currentValue /= value;
      } else {
        throw new Error("No se puede dividir por cero.");
      }
    }

    clear(): void {
      this.currentValue = 0;
    }

    getValue(): number {
      return this.currentValue;
    }

    save(): CalculatorMemento {
      return new CalculatorMemento(this.currentValue);
    }

    restore(memento: CalculatorMemento): void {
      this.currentValue = memento.getState();
    }
}