package uo.cpm.modulo.ui;

import java.util.ResourceBundle;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelQuienesSomos extends JPanel {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private ResourceBundle textos;
	private JPanel panelEste;
	private JPanel panelCentro;
	private JPanel panelBoton;
	private JPanel panelTexto;
	private JPanel panelImg;
	private JScrollPane scrollTexto;
	private JTextArea textoQnSomos;
	private JButton btnPaginaPrincipal;
	private JLabel lblImg;

	/**
	 * Create the panel.
	 */
	public PanelQuienesSomos(VentanaPrincipal vp, ResourceBundle textos) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				vp.getRootPane().setDefaultButton(getBtnPaginaPrincipal());
			}
		});
		setBackground(Color.WHITE);
		this.vp = vp;
		this.textos = textos;
		setLayout(new BorderLayout(0, 0));
		add(getPanelEste(), BorderLayout.WEST);
		add(getPanelCentro(), BorderLayout.CENTER);
		actualizaTextos(this.textos);
	}

	private JPanel getPanelEste() {
		if (panelEste == null) {
			panelEste = new JPanel();
			panelEste.setBackground(Color.WHITE);
			panelEste.setLayout(new BorderLayout(0, 0));
			panelEste.add(getPanelImg(), BorderLayout.CENTER);
		}
		return panelEste;
	}
	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getPanelBoton(), BorderLayout.SOUTH);
			panelCentro.add(getPanelTexto(), BorderLayout.CENTER);
		}
		return panelCentro;
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
	private JPanel getPanelTexto() {
		if (panelTexto == null) {
			panelTexto = new JPanel();
			panelTexto.setBackground(Color.WHITE);
			panelTexto.setLayout(new BorderLayout(0, 0));
			panelTexto.add(getScrollTexto());
		}
		return panelTexto;
	}
	private JPanel getPanelImg() {
		if (panelImg == null) {
			panelImg = new JPanel();
			panelImg.setBackground(Color.WHITE);
			panelImg.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelImg.add(getLblImg());
		}
		return panelImg;
	}
	private JScrollPane getScrollTexto() {
		if (scrollTexto == null) {
			scrollTexto = new JScrollPane();
			scrollTexto.setViewportView(getTextoQnSomos());
		}
		return scrollTexto;
	}
	private JTextArea getTextoQnSomos() {
		if (textoQnSomos == null) {
			textoQnSomos = new JTextArea();
			textoQnSomos.setLineWrap(true);
			textoQnSomos.setWrapStyleWord(true);
			textoQnSomos.setBackground(Color.WHITE);
			textoQnSomos.setEditable(false);
			textoQnSomos.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return textoQnSomos;
	}
	
	void actualizaTextos(ResourceBundle textos) {
		this.textos = textos;
		
		getBtnPaginaPrincipal().setText(textos.getString("texto.boton_principal"));
		getBtnPaginaPrincipal().setToolTipText(textos.getString("texto.tooltip.boton_principal"));
		getTextoQnSomos().setText(textos.getString("quien_somos.texto"));
		getBtnPaginaPrincipal().setMnemonic(textos.getString("mnemonico.quienes_somos.btn.principal").charAt(0));
	}
	private JButton getBtnPaginaPrincipal() {
		if (btnPaginaPrincipal == null) {
			btnPaginaPrincipal = new JButton("");
			btnPaginaPrincipal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)vp.getContentPane().getLayout()).show(vp.getContentPane(),"panelPrincipal");
				}
			});
			btnPaginaPrincipal.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnPaginaPrincipal;
	}
	private JLabel getLblImg() {
		if (lblImg == null) {
			lblImg = new JLabel("");
			lblImg.setIcon(new ImageIcon(escalaImagenes("/img/agencia.png", 340, 340)));
		}
		return lblImg;
	}
	
	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}
}
