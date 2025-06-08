package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.Gui;

public class Calculadora {

	public static void main(String[] args) {

                SwingUtilities.invokeLater(() -> {
                        try {
                                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        } catch (Exception ex) {
                                // ignore and use default
                        }
                        new Gui();
                });

	}
}
