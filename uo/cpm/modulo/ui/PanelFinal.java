package uo.cpm.modulo.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JTextArea;

import uo.cpm.modulo.service.Agencia;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelFinal extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JTextArea textReserva;
	private Agencia agencia;
	private JButton btnFinalizar;
	private VentanaPrincipal vp;
	private ResourceBundle textos;
	private JPanel panelBoton;
	private JButton btnModificar;
	private HelpBroker hb;
	private HelpSet hs;

	/**
	 * Create the panel.
	 */
	public PanelFinal(VentanaPrincipal vp, Agencia agencia, ResourceBundle textos, HelpBroker hb, HelpSet hs) {
		this.textos = textos;
		setBackground(Color.WHITE);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				getTextReserva().setText(agencia.getString());
				vp.getRootPane().setDefaultButton(getBtnFinalizar());
			}
		});
		this.hb = hb;
		this.hs = hs;
		this.vp = vp;
		this.agencia = agencia;
		setLayout(new BorderLayout(0, 0));
		add(getPanelNorte(), BorderLayout.NORTH);
		add(getPanelCentro(), BorderLayout.CENTER);
		add(getPanelSur(), BorderLayout.SOUTH);
		inicializaAyuda();
	}

	private void inicializaAyuda() {
		hb.enableHelpKey(this,"finalizar", hs);
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setBackground(Color.WHITE);
			panelNorte.add(getTextReserva());
		}
		return panelNorte;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
			panelCentro.add(getPanelBoton());
		}
		return panelCentro;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setBackground(Color.WHITE);
			panelSur.add(getBtnFinalizar());
		}
		return panelSur;
	}

	private JTextArea getTextReserva() {
		if (textReserva == null) {
			textReserva = new JTextArea();
			textReserva.setBackground(Color.WHITE);
			textReserva.setEditable(false);
			textReserva.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return textReserva;
	}

	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton(textos.getString("final.btn.finalizar"));
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getBtnModificar().setEnabled(false);
					getBtnFinalizar().setEnabled(false);
					VentanaJuego frame =new VentanaJuego(vp, agencia, textos, hb, hs);
					frame.setVisible(true);
				}
			});
			btnFinalizar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnFinalizar;
	}

	private JPanel getPanelBoton() {
		if (panelBoton == null) {
			panelBoton = new JPanel();
			panelBoton.setBackground(Color.WHITE);
			panelBoton.add(getBtnModificar());
		}
		return panelBoton;
	}
	

	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton(textos.getString("final.btn.modificar"));
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelReserva");
				}
			});
			btnModificar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnModificar;
	}

	public void activaBotones() {
		getBtnModificar().setEnabled(true);
		getBtnFinalizar().setEnabled(true);
	}

	void actualizarTextos(ResourceBundle textos) {
		this.textos = textos;
	    getBtnFinalizar().setText(textos.getString("final.btn.finalizar"));
	    getBtnModificar().setText(textos.getString("final.btn.modificar"));
	    getTextReserva().setText(agencia.getString());
	    getBtnFinalizar().setMnemonic(textos.getString("mnemonico.final.btn.finalizar").charAt(0));
	    getBtnModificar().setMnemonic(textos.getString("mnemonico.final.btn.modificar").charAt(0));
	    validate();
	    repaint();
	}
}