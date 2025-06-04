import type { Command } from "./Command";
import { Calculator } from "../memento/Calculator";
import { CalculatorMemento } from "../memento/CalculatorMemento";

export class ClearCommand implements Command {
    private calculator: Calculator;
    private previousState?: CalculatorMemento;

    constructor(calculator: Calculator) {
        this.calculator = calculator;
    }

    execute(): void {
        this.previousState = this.calculator.save();
        this.calculator.clear();
    }

    undo(): void {
        if (this.previousState) {
            this.calculator.restore(this.previousState);
        }
    }
}