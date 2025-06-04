
export class CalculatorMemento {
  private state: number;

  constructor(state: number) {
    this.state = state;
  }

  getState(): number {
    return this.state;
  }
}