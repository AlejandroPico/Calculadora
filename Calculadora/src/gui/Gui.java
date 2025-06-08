package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import operations.*;
import java.text.DecimalFormat;
import java.util.Locale;

@SuppressWarnings("serial")
public class Gui extends JFrame {

    private JTextField display;
    private double firstNumber;
    private double secondNumber;
    private Operation currentOperation;
    private boolean isResultDisplayed = false;
    private double memory = 0.0;

    public Gui() {
        super("Calculadora");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true);
    }

    private void initComponents() {
        // Campo de texto en la parte superior
        display = new JTextField(20);
        display.setEditable(false); // Para que el usuario no escriba manualmente
        display.setFont(display.getFont().deriveFont(60f));
        add(display, BorderLayout.NORTH);

        // Panel con botones en una cuadrícula 8x4
        JPanel panel = new JPanel(new GridLayout(8, 4, 5, 5));

        // Etiquetas de los botones que vamos a crear
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "±", "=", "+",
            ".", "C", "√", "%",
            "xʸ", "ⁿ√", "π", "e",
            "ln", "M+", "MR", "MC",
            "sin", "cos", "tan", ""
        };

        // Crear y añadir cada botón al panel
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.setFont(btn.getFont().deriveFont(20f));
            btn.addActionListener(new ButtonClickListener());
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    // Manejador de eventos para todos los botones
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "0": case "1": case "2": case "3": case "4":
                case "5": case "6": case "7": case "8": case "9":
                    if (isResultDisplayed) {
                        display.setText(""); // Clear display if previous action was '='
                        isResultDisplayed = false;
                    }
                    display.setText(display.getText() + command);
                    break;

                case ".":
                    if (isResultDisplayed) {
                        display.setText("0.");
                        isResultDisplayed = false;
                    } else {
                        String currentText = display.getText();
                        if (currentText.isEmpty()) {
                            display.setText("0.");
                        } else if (!currentText.contains(".")) {
                            display.setText(currentText + ".");
                        }
                    }
                    break;

                case "√": {
                    String currentTextSqrt = display.getText();
                    if (currentTextSqrt.isEmpty() || currentTextSqrt.startsWith("Error:")) {
                        break;
                    }

                    double operandSqrt = parseDisplay();
                    Operation sqrtOp = new RaizCuadrada();

                    try {
                        double resultSqrt = sqrtOp.calcular(operandSqrt, 0);
                        display.setText(formatResult(resultSqrt));
                        firstNumber = resultSqrt;
                        currentOperation = null;
                        isResultDisplayed = true;
                    } catch (ArithmeticException ex) {
                        display.setText("Error: Neg sqrt");
                        firstNumber = 0;
                        secondNumber = 0;
                        currentOperation = null;
                        isResultDisplayed = true;
                    }
                    break;
                }

                case "±":
                    String currentText = display.getText();
                    if (currentText.isEmpty() || currentText.startsWith("Error:")) { // More general error check
                        break;
                    }

                    if (isResultDisplayed) {
                        firstNumber *= -1;
                        display.setText(formatResult(firstNumber));
                    } else {
                        double val = parseDisplay();
                        if (val != 0) {
                            display.setText(formatResult(val * -1));
                        }
                    }
                    break;

                case "%":
                    String currentTextPercent = display.getText();
                    if (currentTextPercent.isEmpty() || currentTextPercent.startsWith("Error:")) {
                        break;
                    }

                    double currentValue = parseDisplay();
                    // Ensure Porcentaje is imported (covered by operations.*)
                    Operation percentageOp = new Porcentaje();
                    double result = percentageOp.calcular(currentValue, 0); // Second param is dummy

                    display.setText(formatResult(result));
                    isResultDisplayed = false;
                    break;

                case "C":
                    // Limpiar todo
                    display.setText("");
                    firstNumber = 0;
                    secondNumber = 0;
                    currentOperation = null;
                    isResultDisplayed = false; // Reset this flag
                    break;

                case "+":
                    setOperation(new Suma());
                    isResultDisplayed = false;
                    break;
                case "-":
                    setOperation(new Resta());
                    isResultDisplayed = false;
                    break;
                case "*":
                    setOperation(new Multiplicacion());
                    isResultDisplayed = false;
                    break;
                case "/":
                    setOperation(new Division());
                    isResultDisplayed = false;
                    break;

                case "xʸ":
                    setOperation(new Potencia());
                    isResultDisplayed = false; // Consistent with other binary operations
                    break;

                case "ⁿ√":
                    setOperation(new Raiz());
                    isResultDisplayed = false;
                    break;

                case "π":
                    display.setText(formatResult(Math.PI));
                    isResultDisplayed = true;
                    currentOperation = null;
                    break;

                case "e":
                    display.setText(formatResult(Math.E));
                    isResultDisplayed = true;
                    currentOperation = null;
                    break;

                case "ln": {
                    String currentTextLn = display.getText();
                    if (currentTextLn.isEmpty() || currentTextLn.startsWith("Error:")) {
                        break;
                    }
                    double operandLn = parseDisplay();
                    Operation logOp = new Logaritmo();
                    try {
                        double resLn = logOp.calcular(operandLn, 0);
                        display.setText(formatResult(resLn));
                        firstNumber = resLn;
                        currentOperation = null;
                        isResultDisplayed = true;
                    } catch (ArithmeticException ex) {
                        display.setText("Error: ln");
                        isResultDisplayed = true;
                    }
                    break;
                }

                case "M+":
                    memory = parseDisplay();
                    isResultDisplayed = true;
                    break;

                case "MR":
                    display.setText(formatResult(memory));
                    isResultDisplayed = true;
                    break;

                case "MC":
                    memory = 0.0;
                    break;

                case "sin":
                case "cos":
                case "tan": {
                    String currentTextTrig = display.getText();
                    if (currentTextTrig.isEmpty() || currentTextTrig.startsWith("Error:")) {
                        break;
                    }

                    double operandTrig = parseDisplay();
                    Operation trigOp = null;
                    String commandAction = e.getActionCommand();

                    switch (commandAction) {
                        case "sin":
                            trigOp = new Seno();
                            break;
                        case "cos":
                            trigOp = new Coseno();
                            break;
                        case "tan":
                            trigOp = new Tangente();
                            break;
                    }

                    if (trigOp != null) {
                        double resTrig = trigOp.calcular(operandTrig, 0);
                        display.setText(formatResult(resTrig));
                        firstNumber = resTrig;
                        currentOperation = null;
                        isResultDisplayed = true;
                    }
                    break;
                }

                case "=": {
                    if (currentOperation != null) {
                        secondNumber = parseDisplay();
                        try {
                            double resultEq = currentOperation.calcular(firstNumber, secondNumber);
                            display.setText(formatResult(resultEq));
                            firstNumber = resultEq;
                            isResultDisplayed = true;
                            currentOperation = null;
                        } catch (ArithmeticException ex) {
                            display.setText("Error: Div by 0");
                            firstNumber = 0;
                            secondNumber = 0;
                            currentOperation = null;
                            isResultDisplayed = true;
                        }
                    }
                    break;
                }
            }
        }
    }

    private String formatResult(double number) {
        if (Double.isNaN(number)) {
            return "Error: NaN";
        }
        if (Double.isInfinite(number)) {
            return number > 0 ? "Infinity" : "-Infinity";
        }

        if (number == (long) number) {
            if (Math.abs(number) >= 1E10) {
                 DecimalFormat scientificFormat = new DecimalFormat("0.######E0");
                 return scientificFormat.format(number);
            }
            return String.format(Locale.US, "%d", (long) number);
        } else {
            if ((Math.abs(number) >= 1E10 || (Math.abs(number) < 1E-4 && number != 0.0))) {
                DecimalFormat scientificFormat = new DecimalFormat("0.######E0");
                return scientificFormat.format(number);
            } else {
                DecimalFormat decimalFormatter = new DecimalFormat("0.########", new java.text.DecimalFormatSymbols(Locale.US));
                return decimalFormatter.format(number);
            }
        }
    }

    // Guarda la operación y el primer número
    private void setOperation(Operation op) {
        if (!isResultDisplayed) {
            if (!display.getText().isEmpty()) {
                firstNumber = parseDisplay();
            } else {
                 // This case implies an operator is pressed when display is empty,
                 // and it's not after a result. e.g. C, then +, then a number.
                 // Or, if user just starts with an operator.
                 // We can default firstNumber to 0 in such scenarios if no operation is pending.
                 // If an operation *is* pending (e.g. 5 + then user presses *),
                 // firstNumber is already set. This logic path is tricky.
                 // For now, let's assume if display is empty and not a result,
                 // and no current op, firstNumber starts at 0.
                 // This part of the logic might need further refinement based on desired calculator behavior
                 // for chained operations without intermediate '='.
                 // However, given the existing structure, operations are typically like num-op-num-=.
                 // The crucial part is that if isResultDisplayed is true, firstNumber is already correct.
            }
        }
        // If isResultDisplayed is true, firstNumber already holds the result from the previous calculation.

        currentOperation = op;
        display.setText(""); // Clear display for the next number
        isResultDisplayed = false; // Reset this flag as we are starting/continuing an operation
    }

    // Convierte el contenido del display a double (manejando si está vacío)
    private double parseDisplay() {
        String text = display.getText();
        if (text.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(text);
    }
}
