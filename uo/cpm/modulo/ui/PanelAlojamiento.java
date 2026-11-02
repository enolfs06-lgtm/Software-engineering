package uo.cpm.modulo.ui;

import javax.swing.JPanel;

import uo.cpm.modulo.model.Alojamiento;
import uo.cpm.modulo.service.Agencia;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class PanelAlojamiento extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblImagen;
	private JLabel lblNombre;
	private Alojamiento aloj;
	private JLabel lblPrecios;
	private JCheckBox chckbxSeleccion;
	private JTextField textPrecio;
	private ResourceBundle textos;
	private PanelReserva panel;
	private Agencia agencia;

	/**
	 * Create the panel.
	 */
	public PanelAlojamiento(Alojamiento aloj, ResourceBundle textos, PanelReserva panel, Agencia agencia) {
		this.textos = textos;
		setMaximumSize(new Dimension(1100000, 116));
		setPreferredSize(new Dimension(500, 116));
		setBackground(Color.WHITE);
		this.aloj = aloj;
		this.panel = panel;
		this.agencia = agencia;
		setLayout(null);
		add(getLblImagen());
		add(getLblNombre());
		add(getLblPrecios());
		add(getChckbxSeleccion());
		add(getTextPrecio());

	}
	private JLabel getLblImagen() {
		if (lblImagen == null) {
			lblImagen = new JLabel("");
			lblImagen.setBounds(10, 11, 62, 55);
			lblImagen.setIcon(new ImageIcon(escalaImagenes("/img/" + aloj.getCodigo() + ".png", 62, 55)));
		}
		return lblImagen;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(aloj.getCategoria() +" - " + aloj.getDenominacion() + " " + aloj.getCategoria() + "★");
			lblNombre.setBounds(107, 11, 355, 22);
		}
		return lblNombre;
	}
	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}
	private JLabel getLblPrecios() {
		if (lblPrecios == null) {
			lblPrecios = new JLabel();
			if(aloj.isHotel())
				lblPrecios.setText(textos.getString("alojamiento.precio.noche_persona"));
			else
				lblPrecios.setText(textos.getString("alojamiento.precio.noche"));
			lblPrecios.setBounds(20, 77, 167, 20);
		}
		return lblPrecios;
	}
	JCheckBox getChckbxSeleccion() {
		if (chckbxSeleccion == null) {
			chckbxSeleccion = new JCheckBox("");
			chckbxSeleccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxSeleccion.isSelected()) {
						setBackground(Color.BLUE);
						guardaSeleccion();
						agencia.seleccionaAlojamiento(aloj);
						panel.calculaMinHabitaciones(aloj);
					}else {
						panel.seleccionado(false, null);
						setBackground(Color.WHITE);
						agencia.seleccionaAlojamiento(null);
					}
					panel.actualizaBtnReservar();
				}
			});
			if(panel.getpanelAlojamiento() != null){
				chckbxSeleccion.setEnabled(false);
			}
			chckbxSeleccion.setBounds(440, 18, 97, 73);
		}
		return chckbxSeleccion;
	}
	private void guardaSeleccion() {
		panel.seleccionado(true, this);
	}
	private JTextField getTextPrecio() {
		if (textPrecio == null) {
			textPrecio = new JTextField();
			textPrecio.setEditable(false);
			textPrecio.setText(String.format("%.2f ", aloj.getPrice())+ textos.getString("moneda"));
			textPrecio.setBounds(197, 77, 62, 20);
			textPrecio.setColumns(10);
		}
		return textPrecio;
	}
	Alojamiento getAlojamiento() {
		return aloj;
	}
}