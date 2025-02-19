package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import operations.*;

@SuppressWarnings("serial")
public class Gui extends JFrame {

    private JTextField display;
    private double firstNumber;
    private double secondNumber;
    private Operation currentOperation;

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
        display = new JTextField(10);
        display.setEditable(false); // Para que el usuario no escriba manualmente
        add(display, BorderLayout.NORTH);

        // Panel con botones en una cuadrícula 4x4
        JPanel panel = new JPanel(new GridLayout(4, 4));

        // Etiquetas de los botones que vamos a crear
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        // Crear y añadir cada botón al panel
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
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
                    // Agregar dígito al display
                    display.setText(display.getText() + command);
                    break;

                case "C":
                    // Limpiar todo
                    display.setText("");
                    firstNumber = 0;
                    secondNumber = 0;
                    currentOperation = null;
                    break;

                case "+":
                    setOperation(new Suma());
                    break;
                case "-":
                    setOperation(new Resta());
                    break;
                case "*":
                    setOperation(new Multiplicacion());
                    break;
                case "/":
                    setOperation(new Division());
                    break;

                case "=":
                    // Realizar la operación si hay una en curso
                    if (currentOperation != null) {
                        secondNumber = parseDisplay();
                        double result = currentOperation.calcular(firstNumber, secondNumber);
                        display.setText(String.valueOf(result));
                        // Guardamos el resultado como primer número para permitir operaciones encadenadas
                        firstNumber = result;
                        currentOperation = null;
                    }
                    break;
            }
        }
    }

    // Guarda la operación y el primer número
    private void setOperation(Operation op) {
        firstNumber = parseDisplay();
        currentOperation = op;
        display.setText("");
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
