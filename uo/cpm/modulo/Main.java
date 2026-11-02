package uo.cpm.modulo;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatLightLaf;

import uo.cpm.modulo.ui.VentanaPrincipal;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatLightLaf.setup();
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
