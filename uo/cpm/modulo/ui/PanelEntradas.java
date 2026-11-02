package uo.cpm.modulo.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import uo.cpm.modulo.service.Agencia;

import java.awt.Color;
import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelEntradas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblAdultos;
	private JLabel lblNinios;
	private JTextField textPrecioNinios;
	private JLabel lblPrecios;
	private JLabel lblImagen;
	private JLabel lblNombre;
	private JCheckBox chckbxSeleccion;
	private Agencia agencia;
	private JTextField textPrecioAdultos;
	private JLabel lblFechas;
	private JScrollPane scrollPaneFechas;
	private JPanel panelFechas;
	private ProcesaCheck pc = new ProcesaCheck();
	private int diasSeleccionados = 1;
	private PanelReserva pr;
	private ResourceBundle textos;

	/**
	 * Create the panel.
	 */
	public PanelEntradas(Agencia agencia, PanelReserva pr, ResourceBundle textos) {
		this.textos = textos;
		setMaximumSize(new Dimension(1000000, 116));
		setPreferredSize(new Dimension(500, 116));
		setBackground(Color.WHITE);
		setLayout(null);
		this.agencia = agencia;
		this.pr = pr;
		add(getLblAdultos());
		add(getLblNinios());
		add(getTextPrecioNinios());
		add(getLblPrecios());
		add(getLblImagen());
		add(getLblNombre());
		add(getChckbxSeleccion());
		add(getTextPrecioAdultos());
		add(getLblFechas());
		add(getScrollPaneFechas());
		generarOpcionesEntradas();
	}

	private JLabel getLblAdultos() {
		if (lblAdultos == null) {
			lblAdultos = new JLabel(textos.getString("entradas.label.adultos"));
			lblAdultos.setBounds(147, 41, 57, 20);
		}
		return lblAdultos;
	}

	private JLabel getLblNinios() {
		if (lblNinios == null) {
			lblNinios = new JLabel(textos.getString("entradas.label.ninios"));
			lblNinios.setBounds(266, 41, 54, 20);
		}
		return lblNinios;
	}

	private JTextField getTextPrecioNinios() {
		if (textPrecioNinios == null) {
			textPrecioNinios = new JTextField();
			textPrecioNinios.setBounds(334, 41, 57, 20);
			textPrecioNinios.setText(String.format("%.2f ", agencia.getPrecioParqueNinio()) + textos.getString("moneda"));
			textPrecioNinios.setEditable(false);
			textPrecioNinios.setColumns(10);
		}
		return textPrecioNinios;
	}

	private JLabel getLblPrecios() {
		if (lblPrecios == null) {
			lblPrecios = new JLabel(textos.getString("entradas.label.precios"));
			lblPrecios.setBounds(83, 41, 46, 20);
		}
		return lblPrecios;
	}

	private JLabel getLblImagen() {
		if (lblImagen == null) {
			lblImagen = new JLabel("");
			lblImagen.setIcon(new ImageIcon(escalaImagenes("/img/" + agencia.getCodigoParque() + ".png", 62, 55)));
			lblImagen.setBounds(10, 11, 62, 55);
		}
		return lblImagen;
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(agencia.getNombreParque() + " - " + agencia.getCiudad() + ", " + agencia.getPais());
			lblNombre.setBounds(107, 11, 355, 22);
		}
		return lblNombre;
	}

	private JCheckBox getChckbxSeleccion() {
		if (chckbxSeleccion == null) {
			chckbxSeleccion = new JCheckBox("");
			chckbxSeleccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chckbxSeleccion.isSelected()) {
						setBackground(Color.BLUE);
						chckbxSeleccion.setBackground(Color.BLUE);
						pr.entradas(true);
					} else {
						setBackground(Color.WHITE);
						chckbxSeleccion.setBackground(Color.WHITE);
						pr.entradas(false);
					}
					pr.actualizaBtnReservar();
				}
			});
			chckbxSeleccion.setBounds(440, 18, 97, 73);
		}
		return chckbxSeleccion;
	}

	private JTextField getTextPrecioAdultos() {
		if (textPrecioAdultos == null) {
			textPrecioAdultos = new JTextField();
			textPrecioAdultos.setEditable(false);
			textPrecioAdultos.setText(String.format("%.2f ", agencia.getPrecioParqueAdulto())+ textos.getString("moneda"));
			textPrecioAdultos.setBounds(199, 41, 57, 20);
			textPrecioAdultos.setColumns(10);
		}
		return textPrecioAdultos;
	}

	private JLabel getLblFechas() {
		if (lblFechas == null) {
			lblFechas = new JLabel(textos.getString("entradas.label.seleccione_fechas"));
			lblFechas.setBounds(10, 91, 219, 20);
		}
		return lblFechas;
	}

	private JScrollPane getScrollPaneFechas() {
		if (scrollPaneFechas == null) {
			scrollPaneFechas = new JScrollPane();
			scrollPaneFechas.setBounds(215, 69, 165, 39);
			scrollPaneFechas.setViewportView(getPanelFechas());
		}
		return scrollPaneFechas;
	}

	private JPanel getPanelFechas() {
		if (panelFechas == null) {
			panelFechas = new JPanel();
			panelFechas.setBackground(Color.WHITE);
		}
		return panelFechas;
	}

	public void generarOpcionesEntradas() {
		getPanelFechas().removeAll();
		agencia.eliminaFechasParque();
		int dias = agencia.getDiasEstancia() + 1;
		diasSeleccionados = 1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(agencia.getDateEntrada());
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);

		SimpleDateFormat formato = new SimpleDateFormat(textos.getString("formato.fechas"));
		while (dias != 0) {
			Date diaActual = cal.getTime();

			JCheckBox check = new JCheckBox();
			check.setBackground(Color.WHITE);
			check.setText(formato.format(diaActual));
			if (dias == agencia.getDiasEstancia() + 1) {
				check.setSelected(true);
				agencia.addFechaParque(diaActual);
			}
			check.addActionListener(pc);

			getPanelFechas().add(check);

			cal.add(Calendar.DAY_OF_MONTH, 1);
			dias--;
		}
		getChckbxSeleccion().setEnabled(true);
		getChckbxSeleccion().setSelected(true);
		setBackground(Color.BLUE);
		getChckbxSeleccion().setBackground(Color.BLUE);
		pr.entradas(true);
		getPanelFechas().revalidate();
		getPanelFechas().repaint();
	}

	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}

	private class ProcesaCheck implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox box = (JCheckBox) e.getSource();
			if (box.isSelected()) {
				diasSeleccionados++;
				agencia.addFechaParque(parseaFecha(box));
			} else {
				agencia.eliminaFecha(parseaFecha(box));
				diasSeleccionados--;
			}
			compruebaOpcionSeleccion();
		}

	}

	private void compruebaOpcionSeleccion() {
		getChckbxSeleccion().setEnabled(!(diasSeleccionados == 0));
		if (diasSeleccionados == 0) {
			getChckbxSeleccion().setSelected(false);
			setBackground(Color.WHITE);
			getChckbxSeleccion().setBackground(Color.WHITE);
			pr.entradas(false);
		} else {
			getChckbxSeleccion().setSelected(true);
			pr.entradas(true);
			getChckbxSeleccion().setBackground(Color.BLUE);
			setBackground(Color.BLUE);
		}
		pr.actualizaBtnReservar();
	}

	private Date parseaFecha(JCheckBox box) {
		SimpleDateFormat sdf = new SimpleDateFormat(textos.getString("formato.fechas"));

		try {
			return sdf.parse(box.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void actualizarDias() {
		generarOpcionesEntradas();
	}
}