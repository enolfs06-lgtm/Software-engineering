package uo.cpm.modulo.ui;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JTextArea;

import uo.cpm.modulo.model.Parque;
import uo.cpm.modulo.service.Agencia;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ParquesAsociados extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame ventanaPrincipal;
	private ResourceBundle textos;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JPanel panelLbl;
	private JPanel panelCombo;
	private JLabel lblTexto;
	private JComboBox<Parque> comboBoxParques;
	private JPanel panelBoton;
	private JButton btnPaginaPrincipal;
	private Agencia agencia;
	private JPanel panelDesc;
	private JTextArea textDesc;
	private JPanel panelImagen;
	private JLabel lblImagenes;

	/**
	 * Create the panel.
	 */
	public ParquesAsociados(JFrame vp, ResourceBundle textos, Agencia agencia) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				vp.getRootPane().setDefaultButton(getBtnPaginaPrincipal());
			}
		});
		setBackground(Color.WHITE);
		this.ventanaPrincipal = vp;
		this.agencia = agencia;
		setLayout(new BorderLayout(0, 0));
		add(getPanelNorte(), BorderLayout.NORTH);
		add(getPanelCentro(), BorderLayout.CENTER);
		add(getPanelSur(), BorderLayout.SOUTH);
		actualizaTextos(textos);
	}

	void actualizaTextos(ResourceBundle textos) {
		this.textos = textos;

		getLblTexto().setText(this.textos.getString("texto.interesado"));
		getBtnPaginaPrincipal().setText(textos.getString("texto.boton_principal"));
		getBtnPaginaPrincipal().setToolTipText(textos.getString("texto.tooltip.boton_principal"));
		getComboBoxParques().setModel(new DefaultComboBoxModel<Parque>(agencia.getParques()));
		getBtnPaginaPrincipal().setMnemonic(textos.getString("mnemonico.quienes_somos.btn.principal").charAt(0));
		actualizaDescripcion();
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setBackground(Color.WHITE);
			panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
			panelNorte.add(getPanelLbl());
			panelNorte.add(getPanelCombo());
		}
		return panelNorte;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
			panelCentro.add(getPanelImagen());
			panelCentro.add(getPanelDesc());
		}
		return panelCentro;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setBackground(Color.WHITE);
			panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
			panelSur.add(getPanelBoton());
		}
		return panelSur;
	}

	private JPanel getPanelLbl() {
		if (panelLbl == null) {
			panelLbl = new JPanel();
			panelLbl.setBackground(Color.WHITE);
			panelLbl.add(getLblTexto());
		}
		return panelLbl;
	}

	private JPanel getPanelCombo() {
		if (panelCombo == null) {
			panelCombo = new JPanel();
			panelCombo.setBackground(Color.WHITE);
			panelCombo.add(getComboBoxParques());
		}
		return panelCombo;
	}

	private JLabel getLblTexto() {
		if (lblTexto == null) {
			lblTexto = new JLabel("");
			lblTexto.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblTexto;
	}

	private JComboBox<Parque> getComboBoxParques() {
		if (comboBoxParques == null) {
			comboBoxParques = new JComboBox<Parque>();
			comboBoxParques.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizaImagenYTexto();
				}
			});
			comboBoxParques.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			comboBoxParques.setModel(new DefaultComboBoxModel<Parque>(agencia.getParques()));
		}
		return comboBoxParques;
	}

	private void actualizaImagenYTexto() {
		redimensionarImagen();
		actualizaDescripcion();
	}
	
	private void redimensionarImagen() {
		if (getPanelImagen().getWidth() > 0 && getPanelImagen().getHeight() > 0) {

			int ancho = getPanelImagen().getWidth() / 2;
			int alto = getPanelImagen().getHeight() - 100;

			Image imgEscalada = escalaImagenes("/img/" +((Parque)getComboBoxParques().getSelectedItem()).getCodigo()  +".png", alto, ancho);

			getLblImagenes().setIcon(new ImageIcon(imgEscalada));
		}
	}

	private void actualizaDescripcion() {
		getTextDesc().setText(((Parque) getComboBoxParques().getSelectedItem()).getDescripcion());
	}
	
	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}

	private JPanel getPanelBoton() {
		if (panelBoton == null) {
			panelBoton = new JPanel();
			panelBoton.setBackground(Color.WHITE);
			panelBoton.setLayout(new BorderLayout(0, 0));
			panelBoton.add(getBtnPaginaPrincipal(), BorderLayout.EAST);
		}
		return panelBoton;
	}

	private JButton getBtnPaginaPrincipal() {
		if (btnPaginaPrincipal == null) {
			btnPaginaPrincipal = new JButton("");
			btnPaginaPrincipal.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnPaginaPrincipal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)ventanaPrincipal.getContentPane().getLayout()).show(ventanaPrincipal.getContentPane(),"panelPrincipal");
				}
			});
		}
		return btnPaginaPrincipal;
	}
	private JPanel getPanelDesc() {
		if (panelDesc == null) {
			panelDesc = new JPanel();
			panelDesc.setBackground(Color.WHITE);
			panelDesc.setLayout(new GridLayout(0, 1, 0, 0));
			panelDesc.add(getTextDesc());
		}
		return panelDesc;
	}
	private JTextArea getTextDesc() {
		if (textDesc == null) {
			textDesc = new JTextArea();
			textDesc.setBackground(Color.WHITE);
			textDesc.setEditable(false);
			textDesc.setLineWrap(true);
			textDesc.setWrapStyleWord(true);
			textDesc.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return textDesc;
	}
	private JPanel getPanelImagen() {
		if (panelImagen == null) {
			panelImagen = new JPanel();
			panelImagen.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					actualizaImagenYTexto();
				}
			});
			panelImagen.setBackground(Color.WHITE);
			panelImagen.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelImagen.add(getLblImagenes());
		}
		return panelImagen;
	}
	private JLabel getLblImagenes() {
		if (lblImagenes == null) {
			lblImagenes = new JLabel("");
		}
		return lblImagenes;
	}
}
