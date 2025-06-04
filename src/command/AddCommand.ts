import type { Command } from "./Command";
import { Calculator } from "../memento/Calculator";
import { CalculatorMemento } from "../memento/CalculatorMemento";

export class AddCommand implements Command {
    private calculator: Calculator;
    private value: number;
    private previousState?: CalculatorMemento;

    constructor(calculator: Calculator, value: number) {
        this.calculator = calculator;
        this.value = value;
    }

    execute(): void {
        this.previousState = this.calculator.save();
        this.calculator.add(this.value);
    }

    undo(): void {
        if (this.previousState) {
            this.calculator.restore(this.previousState);
        }
    }
}