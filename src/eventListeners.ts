export function inputOnly() {
  const display = document.getElementById('display') as HTMLInputElement;
  display.addEventListener('keydown', (e) => {
    const allowedKeys = [
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '+', '-', '*', '/', '.', 'Backspace', 'ArrowLeft', 'ArrowRight', 'Delete',
      '(', ')'
    ];

    // Allow Ctrl+A, Ctrl+C, Ctrl+V, etc.
    if (e.ctrlKey || e.metaKey) return;

    if (!allowedKeys.includes(e.key)) {
      e.preventDefault();
    }
    });
}
