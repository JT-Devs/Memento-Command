import { CalculatorMemento } from "./CalculatorMemento";
import { Calculator } from "./Calculator";

export class History {
    private undoStack: CalculatorMemento[] = [];
    private redoStack: CalculatorMemento[] = [];

    save(memento: CalculatorMemento): void {
        this.undoStack.push(memento);
        this.redoStack = [];
    }

    undo(calculator: Calculator): CalculatorMemento | undefined {
        if (this.undoStack.length > 0) {
            this.redoStack.push(calculator.save());
            return this.undoStack.pop();
        }
        return undefined;
    }

    redo(calculator: Calculator): CalculatorMemento | undefined {
        if (this.redoStack.length > 0) {
            this.undoStack.push(calculator.save());
            return this.redoStack.pop();
        }
        return undefined;
    }

    canUndo(): boolean {
        return this.undoStack.length > 0;
    }

    canRedo(): boolean {
        return this.redoStack.length > 0;
    }
}