package uo.cpm.modulo.ui;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import uo.cpm.modulo.service.Agencia;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PanelPago extends JPanel {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private Agencia agencia;
	private ResourceBundle textos;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JPanel panelTexto;
	private JTextArea textReserva;
	private JPanel panelModifica;
	private JButton btnModificaReserva;
	private JButton btnCancelar;
	private JButton btnProceder;
	private JPanel panelBotones;
	private JPanel panelCentroIzq;
	private JPanel panelCentroDcha;
	private JLabel lblNYAp;
	private JTextField textNYAp;
	private JLabel lblDNI;
	private JTextField textDNI;
	private JLabel lblObservaciones;
	private JLabel lblCorreo;
	private JTextField textCorreo;
	private JPanel panelSeparacion;
	private JPanel panelEspacioNombre;
	private JPanel panelSeparadorCorreo;
	private JTextArea textObs;
	private JLabel lblNombOblig;
	private JLabel lblDniOblg;
	private JLabel lblCorreoOblig;
	private HelpBroker hb;
	private HelpSet hs;

	/**
	 * Create the panel.
	 */
	public PanelPago(VentanaPrincipal vp, Agencia agencia, ResourceBundle textos, HelpBroker hb, HelpSet hs) {
		this.textos = textos;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				getTextReserva().setText(agencia.getString());
				vp.getRootPane().setDefaultButton(getBtnProceder());
			}
		});
		this.hb = hb;
		this.hs = hs;
		setBackground(Color.WHITE);
		this.vp = vp;
		this.agencia = agencia;
		setLayout(new BorderLayout(0, 0));
		add(getPanelNorte(), BorderLayout.NORTH);
		add(getPanelCentro(), BorderLayout.CENTER);
		add(getPanelSur(), BorderLayout.SOUTH);
		inicializaAyuda();
	}

	private void inicializaAyuda() {
		hb.enableHelpKey(this, "datos", hs);
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setBackground(Color.WHITE);
			panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
			panelNorte.add(getPanelTexto());
			panelNorte.add(getPanelModifica());
		}
		return panelNorte;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new GridLayout(0, 2, 0, 0));
			panelCentro.add(getPanelCentroIzq());
			panelCentro.add(getPanelCentroDcha());
		}
		return panelCentro;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setBackground(Color.WHITE);
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getPanelBotones(), BorderLayout.EAST);
		}
		return panelSur;
	}

	private JPanel getPanelTexto() {
		if (panelTexto == null) {
			panelTexto = new JPanel();
			panelTexto.setBackground(Color.WHITE);
			panelTexto.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelTexto.add(getTextReserva());
		}
		return panelTexto;
	}

	private JTextArea getTextReserva() {
		if (textReserva == null) {
			textReserva = new JTextArea();
			textReserva.setEditable(false);
			textReserva.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			textReserva.setText("");
			textReserva.setBackground(Color.WHITE);
		}
		return textReserva;
	}

	private JPanel getPanelModifica() {
		if (panelModifica == null) {
			panelModifica = new JPanel();
			panelModifica.setBackground(Color.WHITE);
			panelModifica.add(getBtnModificaReserva());
		}
		return panelModifica;
	}

	private JButton getBtnModificaReserva() {
		if (btnModificaReserva == null) {
			btnModificaReserva = new JButton(textos.getString("pago.btn.modificar"));
			btnModificaReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelReserva");
				}
			});
			btnModificaReserva.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnModificaReserva;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton(textos.getString("pago.btn.cancelar"));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(null, textos.getString("pago.dialog.cancelar.mensaje"),
							textos.getString("pago.dialog.cancelar.titulo"), JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
						borrarTxt();
						agencia.iniciar();
						vp.eliminarPanelesFiltro();
						((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelPrincipal");
					}
				}
			});
			btnCancelar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnCancelar;
	}

	void borrarTxt() {
		getTextCorreo().setText("");
		getTextDNI().setText("");
		getTextNYAp().setText("");
		getTextObs().setText("");
	}

	private JButton getBtnProceder() {
		if (btnProceder == null) {
			btnProceder = new JButton(textos.getString("pago.btn.proceder"));
			btnProceder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (datosObligatoriosRellenos()) {
						agencia.setDatos(getTextNYAp().getText(), getTextDNI().getText(),getTextCorreo().getText(), getTextObs().getText());
						((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelFinal");
					} else {
						JOptionPane.showMessageDialog(null, textos.getString("pago.dialog.error.mensaje"),
								textos.getString("pago.dialog.error.titulo"), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnProceder.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnProceder;
	}

	private boolean datosObligatoriosRellenos() {
		return !getTextCorreo().getText().isBlank() && !getTextDNI().getText().isBlank()
				&& !getTextNYAp().getText().isBlank();
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBackground(Color.WHITE);
			panelBotones.add(getBtnCancelar());
			panelBotones.add(getBtnProceder());
		}
		return panelBotones;
	}

	private JPanel getPanelCentroIzq() {
		if (panelCentroIzq == null) {
			panelCentroIzq = new JPanel();
			panelCentroIzq.setBackground(Color.WHITE);
			GridBagLayout gbl_panelCentroIzq = new GridBagLayout();
			gbl_panelCentroIzq.columnWidths = new int[] { 0, 0, 0, 0 };
			gbl_panelCentroIzq.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panelCentroIzq.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
			gbl_panelCentroIzq.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0,
					0.0, 0.0, Double.MIN_VALUE };
			panelCentroIzq.setLayout(gbl_panelCentroIzq);
			GridBagConstraints gbc_panelEspacioNombre = new GridBagConstraints();
			gbc_panelEspacioNombre.gridheight = 2;
			gbc_panelEspacioNombre.gridwidth = 3;
			gbc_panelEspacioNombre.insets = new Insets(0, 0, 5, 0);
			gbc_panelEspacioNombre.fill = GridBagConstraints.BOTH;
			gbc_panelEspacioNombre.gridx = 0;
			gbc_panelEspacioNombre.gridy = 0;
			panelCentroIzq.add(getPanelEspacioNombre(), gbc_panelEspacioNombre);
			GridBagConstraints gbc_lblNYAp = new GridBagConstraints();
			gbc_lblNYAp.insets = new Insets(0, 0, 5, 5);
			gbc_lblNYAp.anchor = GridBagConstraints.EAST;
			gbc_lblNYAp.gridx = 0;
			gbc_lblNYAp.gridy = 3;
			panelCentroIzq.add(getLblNYAp(), gbc_lblNYAp);
			GridBagConstraints gbc_textNYAp = new GridBagConstraints();
			gbc_textNYAp.insets = new Insets(0, 0, 5, 5);
			gbc_textNYAp.fill = GridBagConstraints.HORIZONTAL;
			gbc_textNYAp.gridx = 1;
			gbc_textNYAp.gridy = 3;
			panelCentroIzq.add(getTextNYAp(), gbc_textNYAp);
			GridBagConstraints gbc_lblNombOblig = new GridBagConstraints();
			gbc_lblNombOblig.insets = new Insets(0, 0, 5, 0);
			gbc_lblNombOblig.gridx = 2;
			gbc_lblNombOblig.gridy = 3;
			gbc_lblNombOblig.anchor = GridBagConstraints.WEST;
			panelCentroIzq.add(getLblNombOblig(), gbc_lblNombOblig);
			GridBagConstraints gbc_lblDNI = new GridBagConstraints();
			gbc_lblDNI.anchor = GridBagConstraints.EAST;
			gbc_lblDNI.insets = new Insets(0, 0, 5, 5);
			gbc_lblDNI.gridx = 0;
			gbc_lblDNI.gridy = 6;
			panelCentroIzq.add(getLblDNI(), gbc_lblDNI);
			GridBagConstraints gbc_textDNI = new GridBagConstraints();
			gbc_textDNI.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDNI.insets = new Insets(0, 0, 5, 5);
			gbc_textDNI.gridx = 1;
			gbc_textDNI.gridy = 6;
			panelCentroIzq.add(getTextDNI(), gbc_textDNI);
			GridBagConstraints gbc_lblDniOblg = new GridBagConstraints();
			gbc_lblDniOblg.insets = new Insets(0, 0, 5, 0);
			gbc_lblDniOblg.gridx = 2;
			gbc_lblDniOblg.gridy = 6;
			gbc_lblDniOblg.anchor = GridBagConstraints.WEST;
			panelCentroIzq.add(getLblDniOblg(), gbc_lblDniOblg);
			GridBagConstraints gbc_lblObservaciones = new GridBagConstraints();
			gbc_lblObservaciones.anchor = GridBagConstraints.EAST;
			gbc_lblObservaciones.insets = new Insets(0, 0, 5, 5);
			gbc_lblObservaciones.gridx = 0;
			gbc_lblObservaciones.gridy = 7;
			panelCentroIzq.add(getLblObservaciones(), gbc_lblObservaciones);
			GridBagConstraints gbc_textObs = new GridBagConstraints();
			gbc_textObs.insets = new Insets(0, 0, 0, 5);
			gbc_textObs.gridheight = 7;
			gbc_textObs.fill = GridBagConstraints.BOTH;
			gbc_textObs.gridx = 1;
			gbc_textObs.gridy = 7;
			panelCentroIzq.add(getTextObs(), gbc_textObs);
		}
		return panelCentroIzq;
	}

	private JPanel getPanelCentroDcha() {
		if (panelCentroDcha == null) {
			panelCentroDcha = new JPanel();
			panelCentroDcha.setBackground(Color.WHITE);
			GridBagLayout gbl_panelCentroDcha = new GridBagLayout();
			gbl_panelCentroDcha.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			gbl_panelCentroDcha.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panelCentroDcha.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			gbl_panelCentroDcha.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			panelCentroDcha.setLayout(gbl_panelCentroDcha);
			GridBagConstraints gbc_panelSeparadorCorreo = new GridBagConstraints();
			gbc_panelSeparadorCorreo.gridheight = 4;
			gbc_panelSeparadorCorreo.gridwidth = 6;
			gbc_panelSeparadorCorreo.insets = new Insets(0, 0, 5, 0);
			gbc_panelSeparadorCorreo.fill = GridBagConstraints.BOTH;
			gbc_panelSeparadorCorreo.gridx = 0;
			gbc_panelSeparadorCorreo.gridy = 1;
			panelCentroDcha.add(getPanelSeparadorCorreo(), gbc_panelSeparadorCorreo);
			GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
			gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
			gbc_lblCorreo.anchor = GridBagConstraints.EAST;
			gbc_lblCorreo.gridx = 0;
			gbc_lblCorreo.gridy = 5;
			panelCentroDcha.add(getLblCorreo(), gbc_lblCorreo);
			GridBagConstraints gbc_textCorreo = new GridBagConstraints();
			gbc_textCorreo.gridwidth = 4;
			gbc_textCorreo.insets = new Insets(0, 0, 5, 5);
			gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
			gbc_textCorreo.gridx = 1;
			gbc_textCorreo.gridy = 5;
			panelCentroDcha.add(getTextCorreo(), gbc_textCorreo);
			GridBagConstraints gbc_lblCorreoOblig = new GridBagConstraints();
			gbc_lblCorreoOblig.insets = new Insets(0, 0, 5, 0);
			gbc_lblCorreoOblig.gridx = 5;
			gbc_lblCorreoOblig.gridy = 5;
			gbc_lblCorreoOblig.anchor = GridBagConstraints.WEST;
			panelCentroDcha.add(getLblCorreoOblig(), gbc_lblCorreoOblig);
			GridBagConstraints gbc_panelSeparacion = new GridBagConstraints();
			gbc_panelSeparacion.gridheight = 4;
			gbc_panelSeparacion.gridwidth = 6;
			gbc_panelSeparacion.insets = new Insets(0, 0, 5, 0);
			gbc_panelSeparacion.fill = GridBagConstraints.BOTH;
			gbc_panelSeparacion.gridx = 0;
			gbc_panelSeparacion.gridy = 6;
			panelCentroDcha.add(getPanelSeparacion(), gbc_panelSeparacion);
		}
		return panelCentroDcha;
	}

	private JLabel getLblNYAp() {
		if (lblNYAp == null) {
			lblNYAp = new JLabel(textos.getString("pago.label.nombre"));
			lblNYAp.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblNYAp;
	}

	private JTextField getTextNYAp() {
		if (textNYAp == null) {
			textNYAp = new JTextField();
			textNYAp.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			textNYAp.setColumns(10);
		}
		return textNYAp;
	}

	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel(textos.getString("pago.label.dni"));
			lblDNI.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblDNI;
	}

	private JTextField getTextDNI() {
		if (textDNI == null) {
			textDNI = new JTextField();
			textDNI.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			textDNI.setColumns(10);
		}
		return textDNI;
	}

	private JLabel getLblObservaciones() {
		if (lblObservaciones == null) {
			lblObservaciones = new JLabel(textos.getString("pago.label.observaciones"));
			lblObservaciones.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblObservaciones;
	}

	private JLabel getLblCorreo() {
		if (lblCorreo == null) {
			lblCorreo = new JLabel(textos.getString("pago.label.correo"));
			lblCorreo.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblCorreo;
	}

	private JTextField getTextCorreo() {
		if (textCorreo == null) {
			textCorreo = new JTextField();
			textCorreo.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			textCorreo.setColumns(10);
		}
		return textCorreo;
	}

	private JPanel getPanelSeparacion() {
		if (panelSeparacion == null) {
			panelSeparacion = new JPanel();
			panelSeparacion.setBackground(Color.WHITE);
		}
		return panelSeparacion;
	}

	private JPanel getPanelEspacioNombre() {
		if (panelEspacioNombre == null) {
			panelEspacioNombre = new JPanel();
			panelEspacioNombre.setBackground(Color.WHITE);
		}
		return panelEspacioNombre;
	}

	private JPanel getPanelSeparadorCorreo() {
		if (panelSeparadorCorreo == null) {
			panelSeparadorCorreo = new JPanel();
			panelSeparadorCorreo.setBackground(Color.WHITE);
		}
		return panelSeparadorCorreo;
	}

	private JTextArea getTextObs() {
		if (textObs == null) {
			textObs = new JTextArea();
			textObs.setBorder(new JTextField().getBorder());
		}
		return textObs;
	}
	private JLabel getLblNombOblig() {
		if (lblNombOblig == null) {
			lblNombOblig = new JLabel(textos.getString("pago.label.obligatorio"));
			lblNombOblig.setHorizontalAlignment(SwingConstants.LEFT);
			lblNombOblig.setHorizontalTextPosition(SwingConstants.LEFT);
			lblNombOblig.setForeground(Color.RED);
		}
		return lblNombOblig;
	}
	private JLabel getLblDniOblg() {
		if (lblDniOblg == null) {
			lblDniOblg = new JLabel(textos.getString("pago.label.obligatorio"));
			lblDniOblg.setHorizontalAlignment(SwingConstants.LEFT);
			lblDniOblg.setHorizontalTextPosition(SwingConstants.LEFT);
			lblDniOblg.setForeground(Color.RED);
		}
		return lblDniOblg;
	}
	private JLabel getLblCorreoOblig() {
		if (lblCorreoOblig == null) {
			lblCorreoOblig = new JLabel(textos.getString("pago.label.obligatorio"));
			lblCorreoOblig.setHorizontalAlignment(SwingConstants.LEFT);
			lblCorreoOblig.setHorizontalTextPosition(SwingConstants.LEFT);
			lblCorreoOblig.setForeground(Color.RED);
		}
		return lblCorreoOblig;
	}

	void actualizarTextos(ResourceBundle textos) {
		this.textos = textos;
		getBtnModificaReserva().setText(textos.getString("pago.btn.modificar"));
	    getBtnCancelar().setText(textos.getString("pago.btn.cancelar"));
	    getBtnProceder().setText(textos.getString("pago.btn.proceder"));
	    getLblNYAp().setText(textos.getString("pago.label.nombre"));
	    getLblDNI().setText(textos.getString("pago.label.dni"));
	    getLblObservaciones().setText(textos.getString("pago.label.observaciones"));
	    getLblCorreo().setText(textos.getString("pago.label.correo"));
	    getLblNombOblig().setText(textos.getString("pago.label.obligatorio"));
	    getLblDniOblg().setText(textos.getString("pago.label.obligatorio"));
	    getLblCorreoOblig().setText(textos.getString("pago.label.obligatorio"));
	    getBtnModificaReserva().setMnemonic(textos.getString("mnemonico.pago.btn.modificar").charAt(0));
	    getBtnCancelar().setMnemonic(textos.getString("mnemonico.pago.btn.cancelar").charAt(0));
	    getBtnProceder().setMnemonic(textos.getString("mnemonico.pago.btn.proceder").charAt(0));
	    validate();
	    repaint();
	}
}
