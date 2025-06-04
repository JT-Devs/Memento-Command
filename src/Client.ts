import { Calculator } from "./memento/Calculator";
import { History } from "./memento/History";
import { Invoker } from "./command/Invoker";
import { AddCommand } from "./command/AddCommand";
import { SubstractCommand } from "./command/SubstractCommand";
import { MultiplyCommand } from "./command/MultiplyCommand";
import { DivideCommand } from "./command/DivideCommand";
import { ClearCommand } from "./command/ClearCommand";
import type { Command } from "./command/Command";

const calculator = new Calculator();
const history = new History();
const invoker = new Invoker(calculator, history);

console.log("🧮 Calculadora Interactiva (Command + Memento)");
console.log("Comandos disponibles: sumar, restar, multiplicar, dividir, clear, undo, redo, value, exit");

export function prompt() {
  // Use window.prompt for browser compatibility (no arguments in strict mode)
  const inputRaw = window.prompt(
      "🧮 Calculadora Interactiva (Command + Memento)\nComandos disponibles: sumar, restar, multiplicar, dividir, clear, undo, redo, value, exit\n\n>"
  ) ?? "";
  if (inputRaw === "") {
      alert("Saliendo...");
      return;
  }
  const input = inputRaw.trim().toLowerCase();
  if (input === "exit") {
      alert("Saliendo...");
      return;
  }
  switch (input) {
      case "sumar":
      case "restar":
      case "multiplicar":
      case "dividir": {
          const valueRaw = window.prompt("Valor:") ?? "";
          if (valueRaw === "") {
              prompt();
              return;
          }
          const value = parseFloat(valueRaw);
          if (isNaN(value)) {
              alert("⚠️ Entrada inválida. Usa un número.");
              prompt();
              return;
          }
          let cmd: Command | null = null;
          switch (input) {
              case "sumar":
                  cmd = new AddCommand(calculator, value);
                  break;
              case "restar":
                  cmd = new SubstractCommand(calculator, value);
                  break;
              case "multiplicar":
                  cmd = new MultiplyCommand(calculator, value);
                  break;
              case "dividir":
                  if (value === 0) {
                      alert("⚠️ No se puede dividir por cero.");
                      prompt();
                      return;
                  }
                  cmd = new DivideCommand(calculator, value);
                  break;
          }
          if (cmd) {
              invoker.executeCommand(cmd);
          }
          prompt();
          break;
      }
      case "clear":
          invoker.executeCommand(new ClearCommand(calculator));
          prompt();
          break;
      case "undo":
          invoker.undo();
          prompt();
          break;
      case "redo":
          invoker.redo();
          prompt();
          break;
      case "value":
          alert("📌 Valor actual: " + calculator.getValue());
          prompt();
          break;
      default:
          alert("❓ Comando no reconocido.");
          prompt();
  }
}