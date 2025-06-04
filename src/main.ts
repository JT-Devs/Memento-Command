import './style.css'


document.querySelector<HTMLDivElement>('#app')!.innerHTML

// --- Calculator button input logic ---

const display = document.getElementById('display') as HTMLInputElement | null;
const equalsButton = document.getElementById('equals');
const historyLog = document.getElementById('history-log') as HTMLUListElement | null;

// Helper to add an entry to the history log
function addHistoryEntry(operation: string, result: string) {
  if (!historyLog) return;
  const li = document.createElement('li');
  li.textContent = `${operation} = ${result}`;
  historyLog.appendChild(li);
}

equalsButton?.addEventListener('click', () => {
  if (!display) return;
  const operation = display.value;
  try {
    const result = eval(operation);
    display.value = String(result);
    addHistoryEntry(operation, String(result));
  } catch (error) {
    display.value = 'Error';
  }
});

document.addEventListener('DOMContentLoaded', () => {
  const display = document.getElementById('display') as HTMLInputElement;
  if (!display) return;

  // Handle number and dot buttons
  document.querySelectorAll('[data-value]').forEach(btn => {
    btn.addEventListener('click', () => {
      const value = (btn as HTMLElement).getAttribute('data-value') ?? '';
      display.value += value;
    });
  });

  // Handle operator and control buttons (except equals)
  document.querySelectorAll('[data-action]').forEach(btn => {
    const action = (btn as HTMLElement).getAttribute('data-action');
    if (!action || action === 'equals') return;
    btn.addEventListener('click', () => {
      if (["+", "-", "*", "/"].includes(action)) {
        display.value += action;
      } else if (action === 'clear') {
        display.value = '';
      }
      // Undo/Redo handled elsewhere
    });
  });
});


