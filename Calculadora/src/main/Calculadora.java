package main;

import javax.swing.SwingUtilities;

import gui.Gui;

public class Calculadora {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new Gui();
		});

	}
}
