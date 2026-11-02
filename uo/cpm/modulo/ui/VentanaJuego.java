package uo.cpm.modulo.ui;

import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JPanel;

import uo.cpm.modulo.service.Agencia;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class VentanaJuego extends JDialog {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private Agencia agencia;
	private ResourceBundle textos;
	private JPanel contentPane;
	private JButton btnCas1;
	private JButton btnCas2;
	private JButton btnCas3;
	private JButton btnCas4;
	private JButton btnCas5;
	private JLabel lblSeleccione;
	private JLabel lblCondiciones;
	private JTextArea txtrElDescuentoSolo;
	private JButton btnFinalizar;
	private DescubreCasilla dC = new DescubreCasilla();
	private JLabel lblResultado;
	private HelpBroker hb;
	private HelpSet hs;
	private BloqueaSalirSinSeleccion bloqueo = new BloqueaSalirSinSeleccion();

	/**
	 * Create the dialog.
	 */
	public VentanaJuego(VentanaPrincipal vp, Agencia agencia, ResourceBundle textos, HelpBroker hb, HelpSet hs) {
		this.textos = textos;
		setTitle(textos.getString("juego.titulo"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaJuego.class.getResource("/img/imagenCompañia.png")));
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 730, 430);
		setLocationRelativeTo(null);
		addWindowListener(bloqueo);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		this.hb = hb;
		this.hs = hs;
		this.vp = vp;
		this.agencia = agencia;
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnCas1());
		contentPane.add(getBtnCas2());
		contentPane.add(getBtnCas3());
		contentPane.add(getBtnCas4());
		contentPane.add(getBtnCas5());
		contentPane.add(getLblSeleccione());
		contentPane.add(getLblCondiciones());
		contentPane.add(getTxtrElDescuentoSolo());
		contentPane.add(getBtnFinalizar());
		contentPane.add(getLblResultado());
		vp.getRootPane().setDefaultButton(getBtnCas1());
		actualizaAyuda();
		actualizaNemónicos();
	}

	private void actualizaNemónicos() {
		getBtnCas1().setMnemonic(textos.getString("mnemonico.juego.btn.casilla1").charAt(0));
		getBtnCas2().setMnemonic(textos.getString("mnemonico.juego.btn.casilla2").charAt(0));
		getBtnCas3().setMnemonic(textos.getString("mnemonico.juego.btn.casilla3").charAt(0));
		getBtnCas4().setMnemonic(textos.getString("mnemonico.juego.btn.casilla4").charAt(0));
		getBtnCas5().setMnemonic(textos.getString("mnemonico.juego.btn.casilla5").charAt(0));
		getBtnFinalizar().setMnemonic(textos.getString("mnemonico.juego.btn.finalizar").charAt(0));
	}

	private void actualizaAyuda() {
		hb.enableHelpKey(getRootPane(), "juego", hs);
	}

	private JButton getBtnCas1() {
		if (btnCas1 == null) {
			btnCas1 = new JButton(textos.getString("juego.btn.casilla1"));
			btnCas1.setBackground(Color.WHITE);
			btnCas1.setActionCommand("0");
			btnCas1.addActionListener(dC);
			btnCas1.setBounds(160, 123, 101, 47);
		}
		return btnCas1;
	}

	private JButton getBtnCas2() {
		if (btnCas2 == null) {
			btnCas2 = new JButton(textos.getString("juego.btn.casilla2"));
			btnCas2.setBackground(Color.WHITE);
			btnCas2.setActionCommand("1");
			btnCas2.addActionListener(dC);
			btnCas2.setBounds(160, 228, 101, 47);
		}
		return btnCas2;
	}

	private JButton getBtnCas3() {
		if (btnCas3 == null) {
			btnCas3 = new JButton(textos.getString("juego.btn.casilla3"));
			btnCas3.setBackground(Color.WHITE);
			btnCas3.setActionCommand("2");
			btnCas3.addActionListener(dC);
			btnCas3.setBounds(308, 173, 101, 47);
		}
		return btnCas3;
	}

	private JButton getBtnCas4() {
		if (btnCas4 == null) {
			btnCas4 = new JButton(textos.getString("juego.btn.casilla4"));
			btnCas4.setBackground(Color.WHITE);
			btnCas4.setActionCommand("3");
			btnCas4.addActionListener(dC);
			btnCas4.setBounds(447, 123, 101, 47);
		}
		return btnCas4;
	}

	private JButton getBtnCas5() {
		if (btnCas5 == null) {
			btnCas5 = new JButton(textos.getString("juego.btn.casilla5"));
			btnCas5.setBackground(Color.WHITE);
			btnCas5.setActionCommand("4");
			btnCas5.addActionListener(dC);
			btnCas5.setBounds(447, 228, 101, 47);
		}
		return btnCas5;
	}

	private JLabel getLblSeleccione() {
		if (lblSeleccione == null) {
			lblSeleccione = new JLabel(textos.getString("juego.label.seleccione"));
			lblSeleccione.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
			lblSeleccione.setBounds(254, 32, 248, 31);
		}
		return lblSeleccione;
	}

	private JLabel getLblCondiciones() {
		if (lblCondiciones == null) {
			lblCondiciones = new JLabel(textos.getString("juego.label.condiciones"));
			lblCondiciones.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblCondiciones.setBounds(10, 41, 91, 17);
		}
		return lblCondiciones;
	}

	private JTextArea getTxtrElDescuentoSolo() {
		if (txtrElDescuentoSolo == null) {
			txtrElDescuentoSolo = new JTextArea();
			txtrElDescuentoSolo.setEditable(false);
			txtrElDescuentoSolo.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
			txtrElDescuentoSolo.setText(textos.getString("juego.texto.condiciones"));
			txtrElDescuentoSolo.setBounds(10, 69, 91, 311);
		}
		return txtrElDescuentoSolo;
	}

	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton(textos.getString("juego.btn.finalizar"));
			btnFinalizar.setEnabled(false);
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agencia.finalizarReserva();
					vp.eliminarPanelesFiltro();
					vp.eliminarTxt();
					vp.activarBotonesFinal();
					dispose();
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelPrincipal");
				}
			});
			btnFinalizar.setBounds(603, 342, 101, 38);
		}
		return btnFinalizar;
	}

	private class DescubreCasilla implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int casilla = Integer.parseInt(e.getActionCommand());

			compruebaVictoria(casilla, (JButton) e.getSource());
		}

	}

	private void compruebaVictoria(int casilla, JButton boton) {
		if (agencia.isCasillaPremiada(casilla)) {
			boton.setText("");
			boton.setDisabledIcon(
					new ImageIcon(escalaImagenes("/img/premio.png", boton.getHeight(), boton.getWidth())));
			boton.setIcon(new ImageIcon(escalaImagenes("/img/premio.png", boton.getHeight(), boton.getWidth())));
			getLblResultado().setText(textos.getString("juego.resultado.exito"));
			agencia.resultadoJuego(true);
		} else {
			boton.setText("");
			boton.setDisabledIcon(
					new ImageIcon(escalaImagenes("/img/derrota.png", boton.getHeight(), boton.getWidth())));
			boton.setIcon(new ImageIcon(escalaImagenes("/img/derrota.png", boton.getHeight(), boton.getWidth())));
			getLblResultado().setText(textos.getString("juego.resultado.fracaso"));
			agencia.resultadoJuego(false);
			muestraCorrecto();
		}
		getBtnFinalizar().setEnabled(true);
		desactivaBotones();
		removeWindowListener(bloqueo);
		addWindowListener(new BloqueaSalir());
	}

	private void muestraCorrecto() {
		int posicionVictoria = agencia.getCasillaPremiada();

		switch (posicionVictoria) {
		case 0 -> muestraCasilla(getBtnCas1());
		case 1 -> muestraCasilla(getBtnCas2());
		case 2 -> muestraCasilla(getBtnCas3());
		case 3 -> muestraCasilla(getBtnCas4());
		case 4 -> muestraCasilla(getBtnCas5());
		}

	}

	private void muestraCasilla(JButton boton) {
		boton.setText("");
		boton.setDisabledIcon(new ImageIcon(escalaImagenes("/img/premio.png", boton.getHeight(), boton.getWidth())));
		boton.setIcon(new ImageIcon(escalaImagenes("/img/premio.png", boton.getHeight(), boton.getWidth())));
	}

	private class BloqueaSalir extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			JDialog dialog = (JDialog) e.getSource();

			if (dialog.getDefaultCloseOperation() == JDialog.DO_NOTHING_ON_CLOSE) {
				JOptionPane.showMessageDialog(dialog, textos.getString("mensaje.final.cierre"),
						textos.getString("mensaje.atencion"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class BloqueaSalirSinSeleccion extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			JDialog dialog = (JDialog) e.getSource();

			if (dialog.getDefaultCloseOperation() == JDialog.DO_NOTHING_ON_CLOSE) {
				JOptionPane.showMessageDialog(dialog, textos.getString("mensaje.final.cierre.juego"),
						textos.getString("mensaje.atencion"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void desactivaBotones() {
		getBtnCas1().setEnabled(false);
		getBtnCas2().setEnabled(false);
		getBtnCas3().setEnabled(false);
		getBtnCas4().setEnabled(false);
		getBtnCas5().setEnabled(false);
	}

	private JLabel getLblResultado() {
		if (lblResultado == null) {
			lblResultado = new JLabel("");
			lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
			lblResultado.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblResultado.setBounds(160, 304, 388, 76);
		}
		return lblResultado;
	}

	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}
}