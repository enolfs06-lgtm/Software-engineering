package uo.cpm.modulo.ui;

import java.util.ResourceBundle;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;

public class PanelServicios extends JPanel {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private ResourceBundle textos;
	private JPanel panelEste;
	private JPanel panelCentro;
	private JTextArea textServicios;
	private JPanel panelImagenes;
	private JPanel panelBoton;
	private JButton btnPrincipal;
	private JLabel lblImg1;
	private JLabel lblImg2;
	private JLabel lblImg3;
	private JScrollPane scrollTexto;

	/**
	 * Create the panel.
	 */
	public PanelServicios(VentanaPrincipal vp, ResourceBundle textos) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				vp.getRootPane().setDefaultButton(getBtnPrincipal());
			}
		});
		setBackground(Color.WHITE);
		this.textos = textos;
		this.vp = vp;
		setLayout(new BorderLayout(0, 0));
		add(getPanelEste(), BorderLayout.EAST);
		add(getPanelCentro(), BorderLayout.CENTER);
		actualizaTextos(this.textos);
	}

	void actualizaTextos(ResourceBundle textos) {
		this.textos = textos;
		
		getBtnPrincipal().setText(textos.getString("texto.boton_principal"));
		getBtnPrincipal().setToolTipText(textos.getString("texto.tooltip.boton_principal"));
		getTextServicios().setText(textos.getString("servicios.texto"));
		getBtnPrincipal().setMnemonic(textos.getString("mnemonico.quienes_somos.btn.principal").charAt(0));
	}

	private JPanel getPanelEste() {
		if (panelEste == null) {
			panelEste = new JPanel();
			panelEste.setBackground(Color.WHITE);
			panelEste.setLayout(new BorderLayout(0, 0));
			panelEste.add(getPanelImagenes(), BorderLayout.CENTER);
			panelEste.add(getPanelBoton(), BorderLayout.SOUTH);
		}
		return panelEste;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getScrollTexto(), BorderLayout.CENTER);
		}
		return panelCentro;
	}

	private JTextArea getTextServicios() {
		if (textServicios == null) {
			textServicios = new JTextArea();
			textServicios.setBackground(Color.WHITE);
			textServicios.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			textServicios.setLineWrap(true);
			textServicios.setWrapStyleWord(true);
			textServicios.setEditable(false);
		}
		return textServicios;
	}

	private JPanel getPanelImagenes() {
		if (panelImagenes == null) {
			panelImagenes = new JPanel();
			panelImagenes.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					actualizaImagenes();
				}
			});
			panelImagenes.setBackground(Color.WHITE);
			panelImagenes.setLayout(new BoxLayout(panelImagenes, BoxLayout.Y_AXIS));
			panelImagenes.add(getLblImg1());
			panelImagenes.add(getLblImg2());
			panelImagenes.add(getLblImg3());
		}
		return panelImagenes;
	}

	private void actualizaImagenes() {
		getLblImg2().setIcon(
				new ImageIcon(escalaImagenes("/img/PT008.png", getLblImg1().getHeight(), getLblImg1().getWidth())));
		getLblImg3().setIcon(
				new ImageIcon(escalaImagenes("/img/PT009.png", getLblImg1().getHeight(), getLblImg1().getWidth())));
	}

	private JPanel getPanelBoton() {
		if (panelBoton == null) {
			panelBoton = new JPanel();
			panelBoton.setBackground(Color.WHITE);
			panelBoton.add(getBtnPrincipal());
		}
		return panelBoton;
	}

	private JButton getBtnPrincipal() {
		if (btnPrincipal == null) {
			btnPrincipal = new JButton("");
			btnPrincipal.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnPrincipal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)vp.getContentPane().getLayout()).show(vp.getContentPane(),"panelPrincipal");
				}
			});
		}
		return btnPrincipal;
	}

	private JLabel getLblImg1() {
		if (lblImg1 == null) {
			lblImg1 = new JLabel("");
			lblImg1.setIcon(new ImageIcon(PanelServicios.class.getResource("/img/PT001.png")));
		}
		return lblImg1;
	}

	private JLabel getLblImg2() {
		if (lblImg2 == null) {
			lblImg2 = new JLabel("");
		}
		return lblImg2;
	}

	private JLabel getLblImg3() {
		if (lblImg3 == null) {
			lblImg3 = new JLabel("");
		}
		return lblImg3;
	}

	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}
	private JScrollPane getScrollTexto() {
		if (scrollTexto == null) {
			scrollTexto = new JScrollPane();
			scrollTexto.setViewportView(getTextServicios());
		}
		return scrollTexto;
	}
}
