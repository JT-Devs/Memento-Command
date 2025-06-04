import { Calculator } from "../memento/Calculator";
import { History } from "../memento/History";
import type { Command } from "./Command";

export class Invoker {
    private calculator: Calculator;
    private history: History;

    constructor(calculator: Calculator, history: History) {
      this.calculator = calculator;
      this.history = history;
    }

    executeCommand(command: Command): void {
      this.history.save(this.calculator.save());
      command.execute();
    }

    undo(): void {
      if (this.history.canUndo()) {
        const m = this.history.undo(this.calculator);
        if (m) this.calculator.restore(m);
      }
    }

    redo(): void {
      if (this.history.canRedo()) {
        const m = this.history.redo(this.calculator);
        if (m) this.calculator.restore(m);
      }
    }
}