package uo.cpm.modulo.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import uo.cpm.modulo.model.Parque;
import uo.cpm.modulo.service.Agencia;

import com.toedter.calendar.JCalendar;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FlowLayout;

public class PanelPrincipal extends JPanel {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private JPanel panelNorte;
	private JLabel lblImg;
	private JPanel panelCentral;
	private JButton btnAyuda;
	private JButton btnQnesSomos;
	private JButton btnServicios;
	private JButton btnParquesAsociados;
	private JPanel panelizq;
	private JPanel paneldcha;
	private JButton btnOferta;
	private ResourceBundle textos;
	private JPanel PanelFechasYPersonas;
	private JPanel panelPersonas;
	private JLabel lblAdultos;
	private JSpinner spinnerNinios;
	private JLabel lblNinios;
	private JSpinner spinnerAdultos;
	private JPanel panelFechas;
	private JPanel panelBuscYComb;
	private JPanel panelBuscar;
	private JButton btnBuscar;
	private JComboBox<Parque> cmBoxParques;
	private Agencia agencia;
	private JPanel panelImg;
	private JLabel lblParque;
	private JCalendar calendar;
	private JSpinner spinnerDias;
	private JLabel lblDias;
	private JPanel panelDias;
	private JPanel panelPaises;
	private JButton btnEspaniol;
	private JButton btnIngles;
	private HelpBroker hb;
	private HelpSet hs;
	/**
	 * Create the panel.
	 */
	public PanelPrincipal(VentanaPrincipal vp, Agencia agencia, ResourceBundle textos, HelpBroker hb, HelpSet hs) {
		this.textos = textos; 
		setMinimumSize(new Dimension(950, 500));
		setBackground(Color.WHITE);
		this.vp = vp;
		this.agencia = agencia;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				getCalendar().setDate(agencia.getDateEntrada());
				getSpinnerDias().setValue(agencia.getDiasEstancia());
				getSpinnerAdultos().setValue(agencia.getAdultos());
				getSpinnerNinios().setValue(agencia.getNinios());
				getCmBoxParques().setSelectedIndex(0);
				actualizaImagen();
				vp.getRootPane().setDefaultButton(getBtnBuscar());
			}
		});
		this.hb = hb;
		this.hs = hs;
		setLayout(new BorderLayout(0, 0));
		this.add(getPanelNorte(), BorderLayout.NORTH);
		add(getPanelCentral(), BorderLayout.CENTER);
		vp.getRootPane().setDefaultButton(getBtnBuscar());
		inicializaAyuda();
	}

	

	private void inicializaAyuda() {
		hb.enableHelpKey(this,"agencia", hs);
		hb.enableHelpOnButton(getBtnAyuda(), "agencia", hs);
		hb.enableHelpKey(getCalendar(), "fecha", hs);
		hb.enableHelpKey(getSpinnerAdultos(), "personas", hs);
		hb.enableHelpKey(getSpinnerNinios(), "personas", hs);
		hb.enableHelpKey(getSpinnerDias(), "noches", hs);
		hb.enableHelpKey(getBtnOferta(), "oferta", hs);
		hb.enableHelpKey(getBtnBuscar(), "buscar", hs);
		hb.enableHelpKey(getCmBoxParques(), "buscar", hs);
	}



	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setBorder(new LineBorder(new Color(255, 255, 255), 10));
			panelNorte.setBackground(Color.WHITE);
			panelNorte.setLayout(new GridLayout(0, 6, 0, 0));
			panelNorte.add(getLblImg());
			panelNorte.add(getBtnParquesAsociados());
			panelNorte.add(getBtnServicios());
			panelNorte.add(getBtnQnesSomos());
			panelNorte.add(getBtnAyuda());
			panelNorte.add(getPanelPaises());
		}
		return panelNorte;
	}

	private JLabel getLblImg() {
		if (lblImg == null) {
			lblImg = new JLabel("");
			lblImg.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
			lblImg.setBackground(Color.WHITE);
			Image imgEscalada = escalaImagenes("/img/imagenCompañia.png", 70, 85);
			lblImg.setIcon(new ImageIcon(imgEscalada));
		}
		return lblImg;
	}

	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			panelCentral = new JPanel();
			panelCentral.setBackground(Color.WHITE);
			panelCentral.setLayout(new GridLayout(0, 2, 0, 0));
			panelCentral.add(getPanelizq());
			panelCentral.add(getPaneldcha());
		}
		return panelCentral;
	}

	private JButton getBtnAyuda() {
		if (btnAyuda == null) {
			btnAyuda = new JButton(textos.getString("principal.btn.ayuda"));
			btnAyuda.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAyuda.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnAyuda.setBorderPainted(false);
			btnAyuda.setBorder(null);
			btnAyuda.setBackground(Color.WHITE);
		}
		return btnAyuda;
	}

	private JButton getBtnQnesSomos() {
		if (btnQnesSomos == null) {
			btnQnesSomos = new JButton(textos.getString("principal.btn.quienes_somos"));
			btnQnesSomos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)vp.getContentPane().getLayout()).show(vp.getContentPane(),"panelQuienesSomos");
				}
			});
			btnQnesSomos.setHorizontalTextPosition(SwingConstants.CENTER);
			btnQnesSomos.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnQnesSomos.setBorderPainted(false);
			btnQnesSomos.setBorder(null);
			btnQnesSomos.setBackground(Color.WHITE);
		}
		return btnQnesSomos;
	}

	private JButton getBtnServicios() {
		if (btnServicios == null) {
			btnServicios = new JButton(textos.getString("principal.btn.servicios"));
			btnServicios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelServicios");
				}
			});
			btnServicios.setHorizontalTextPosition(SwingConstants.CENTER);
			btnServicios.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnServicios.setBorderPainted(false);
			btnServicios.setBorder(null);
			btnServicios.setBackground(Color.WHITE);
		}
		return btnServicios;
	}

	private JButton getBtnParquesAsociados() {
		if (btnParquesAsociados == null) {
			btnParquesAsociados = new JButton(textos.getString("principal.btn.parques_asociados"));
			btnParquesAsociados.setHorizontalTextPosition(SwingConstants.CENTER);
			btnParquesAsociados.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnParquesAsociados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelParqAsoc");
				}
			});
			btnParquesAsociados.setBorderPainted(false);
			btnParquesAsociados.setBorder(null);
			btnParquesAsociados.setBackground(Color.WHITE);
		}
		return btnParquesAsociados;
	}

	private JPanel getPanelizq() {
		if (panelizq == null) {
			panelizq = new JPanel();
			panelizq.setBorder(new LineBorder(Color.WHITE, 2));
			panelizq.setBackground(Color.WHITE);
			panelizq.setLayout(new GridLayout(0, 1, 1, 20));
			panelizq.add(getBtnOferta());
			panelizq.add(getPanelBuscYComb());
		}
		return panelizq;
	}

	private JPanel getPaneldcha() {
		if (paneldcha == null) {
			paneldcha = new JPanel();
			paneldcha.setBorder(new LineBorder(Color.WHITE, 2));
			paneldcha.setBackground(Color.WHITE);
			paneldcha.setLayout(new BorderLayout(0, 0));
			paneldcha.add(getPanelFechasYPersonas(), BorderLayout.CENTER);
		}
		return paneldcha;
	}

	private JButton getBtnOferta() {
		if (btnOferta == null) {
			btnOferta = new JButton("");
			btnOferta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelReserva");
					vp.reiniciaSeleccionados();
					inicializarReservaOferta();
				}
			});
			btnOferta.setBorderPainted(false);
			btnOferta.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnOferta.setEnabled(false);
			btnOferta.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnOferta.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOferta.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					imagenOferta();
				}
			});
			btnOferta.setVerticalAlignment(SwingConstants.BOTTOM);
			btnOferta.setFocusPainted(false);
			btnOferta.setBackground(Color.WHITE);
			btnOferta.setAlignmentY(1.0f);
			btnOferta.setText(textos.getString("principal.btn.oferta.texto") + " " + agencia.getParqueOfertado().getNombre());
			btnOferta.setToolTipText(textos.getString("principal.btn.oferta.tooltip"));
		}
		return btnOferta;
	}

	private void inicializarReservaOferta() {
		agencia.inicializarReserva(getCalendar().getDate(),(int) getSpinnerDias().getValue() ,
				(Integer) getSpinnerAdultos().getValue(), (Integer) getSpinnerNinios().getValue(),
				agencia.getParqueOfertado(), 1);
		vp.eliminarPanelesFiltro();;
	}



	private void imagenOferta() {
		String ubi = "/img/" + agencia.getParqueOfertado().getCodigo() + ".png";
		btnOferta.setIcon(new ImageIcon(escalaImagenes(ubi, btnOferta.getHeight() - 30, btnOferta.getWidth() / 2)));
	}

	private JPanel getPanelFechasYPersonas() {
		if (PanelFechasYPersonas == null) {
			PanelFechasYPersonas = new JPanel();
			PanelFechasYPersonas.setLayout(new BorderLayout(0, 0));
			PanelFechasYPersonas.add(getPanelPersonas(), BorderLayout.NORTH);
			PanelFechasYPersonas.add(getPanelFechas());
		}
		return PanelFechasYPersonas;
	}

	private JPanel getPanelPersonas() {
		if (panelPersonas == null) {
			panelPersonas = new JPanel();
			panelPersonas.setBackground(Color.WHITE);
			GridBagLayout gbl_panelPersonas = new GridBagLayout();
			gbl_panelPersonas.columnWidths = new int[] { 113, 113, 0, 0, 0, 0, 113, 0, 113, 0 };
			gbl_panelPersonas.rowHeights = new int[] { 20, 0 };
			gbl_panelPersonas.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panelPersonas.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			panelPersonas.setLayout(gbl_panelPersonas);
			GridBagConstraints gbc_lblAdultos = new GridBagConstraints();
			gbc_lblAdultos.fill = GridBagConstraints.BOTH;
			gbc_lblAdultos.insets = new Insets(0, 0, 0, 5);
			gbc_lblAdultos.gridx = 2;
			gbc_lblAdultos.gridy = 0;
			panelPersonas.add(getLblAdultos(), gbc_lblAdultos);
			GridBagConstraints gbc_spinnerAdultos = new GridBagConstraints();
			gbc_spinnerAdultos.fill = GridBagConstraints.BOTH;
			gbc_spinnerAdultos.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerAdultos.gridx = 3;
			gbc_spinnerAdultos.gridy = 0;
			panelPersonas.add(getSpinnerAdultos(), gbc_spinnerAdultos);
			GridBagConstraints gbc_lblNinios = new GridBagConstraints();
			gbc_lblNinios.anchor = GridBagConstraints.WEST;
			gbc_lblNinios.fill = GridBagConstraints.VERTICAL;
			gbc_lblNinios.insets = new Insets(0, 0, 0, 5);
			gbc_lblNinios.gridx = 4;
			gbc_lblNinios.gridy = 0;
			panelPersonas.add(getLblNinios(), gbc_lblNinios);
			GridBagConstraints gbc_spinnerNinios = new GridBagConstraints();
			gbc_spinnerNinios.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerNinios.fill = GridBagConstraints.BOTH;
			gbc_spinnerNinios.gridx = 5;
			gbc_spinnerNinios.gridy = 0;
			panelPersonas.add(getSpinnerNinios(), gbc_spinnerNinios);
		}
		return panelPersonas;
	}

	private JLabel getLblAdultos() {
		if (lblAdultos == null) {
			lblAdultos = new JLabel(textos.getString("principal.label.adultos"));
			lblAdultos.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblAdultos.setBackground(Color.WHITE);
		}
		return lblAdultos;
	}

	private JSpinner getSpinnerNinios() {
		if (spinnerNinios == null) {
			spinnerNinios = new JSpinner();
			spinnerNinios
					.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
			spinnerNinios.setBackground(Color.WHITE);
		}
		return spinnerNinios;
	}

	private JLabel getLblNinios() {
		if (lblNinios == null) {
			lblNinios = new JLabel(textos.getString("principal.label.ninios"));
			lblNinios.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblNinios.setBackground(Color.WHITE);
		}
		return lblNinios;
	}

	private JSpinner getSpinnerAdultos() {
		if (spinnerAdultos == null) {
			spinnerAdultos = new JSpinner();
			spinnerAdultos
					.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spinnerAdultos.setBackground(Color.WHITE);
		}
		return spinnerAdultos;
	}

	private JPanel getPanelFechas() {
		if (panelFechas == null) {
			panelFechas = new JPanel();
			panelFechas.setBorder(new EmptyBorder(10, 10, 10, 10));
			panelFechas.setBackground(Color.WHITE);
			panelFechas.setLayout(new BorderLayout(0, 0));
			panelFechas.add(getCalendar(), BorderLayout.CENTER);
			panelFechas.add(getPanelDias(), BorderLayout.NORTH);
		}
		return panelFechas;
	}

	private JPanel getPanelBuscYComb() {
		if (panelBuscYComb == null) {
			panelBuscYComb = new JPanel();
			panelBuscYComb.setBorder(new EmptyBorder(10, 5, 0, 5));
			panelBuscYComb.setBackground(Color.WHITE);
			panelBuscYComb.setLayout(new BoxLayout(panelBuscYComb, BoxLayout.Y_AXIS));
			panelBuscYComb.add(getPanelBuscar());
			panelBuscYComb.add(getPanelImg());
		}
		return panelBuscYComb;
	}

	private JPanel getPanelBuscar() {
		if (panelBuscar == null) {
			panelBuscar = new JPanel();
			panelBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));
			panelBuscar.setBackground(Color.WHITE);
			GridBagLayout gbl_panelBuscar = new GridBagLayout();
			gbl_panelBuscar.columnWidths = new int[] { 0, 0, 0 };
			gbl_panelBuscar.rowHeights = new int[] { 0, 0 };
			gbl_panelBuscar.columnWeights = new double[] { 0.3, 0.7, Double.MIN_VALUE };
			gbl_panelBuscar.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			panelBuscar.setLayout(gbl_panelBuscar);
			GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
			gbc_btnBuscar.insets = new Insets(10, 10, 10, 5);
			gbc_btnBuscar.fill = GridBagConstraints.BOTH;
			gbc_btnBuscar.ipady = 15;
			gbc_btnBuscar.gridx = 0;
			gbc_btnBuscar.gridy = 0;
			panelBuscar.add(getBtnBuscar(), gbc_btnBuscar);
			GridBagConstraints gbc_cmBoxParques = new GridBagConstraints();
			gbc_cmBoxParques.fill = GridBagConstraints.BOTH;
			gbc_cmBoxParques.ipady = 15;
			gbc_cmBoxParques.gridx = 1;
			gbc_cmBoxParques.gridy = 0;
			gbc_cmBoxParques.insets = new Insets(10, 5, 10, 10);
			panelBuscar.add(getCmBoxParques(), gbc_cmBoxParques);
		}
		return panelBuscar;
	}

	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(textos.getString("principal.btn.buscar"));
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelReserva");
					vp.reiniciaSeleccionados();
					inicializarReserva();
				}
			});
			btnBuscar.setPreferredSize(new Dimension(150, 25));
			btnBuscar.setForeground(Color.WHITE);
			btnBuscar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnBuscar.setToolTipText(textos.getString("principal.btn.buscar.tooltip"));
			btnBuscar.setEnabled(false);
			btnBuscar.setBackground(Color.BLUE);
		}
		return btnBuscar;
	}

	private void inicializarReserva() {
		agencia.inicializarReserva(getCalendar().getDate(),(int) getSpinnerDias().getValue() ,
				(Integer) getSpinnerAdultos().getValue(), (Integer) getSpinnerNinios().getValue(),
				(Parque) getCmBoxParques().getSelectedItem(), 1);
		vp.eliminarPanelesFiltro();
	}

	private JComboBox<Parque> getCmBoxParques() {
		if (cmBoxParques == null) {
			cmBoxParques = new JComboBox<Parque>();
			cmBoxParques.setPreferredSize(new Dimension(250, 20));
			cmBoxParques.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
			cmBoxParques.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizaImagen();
				}
			});
			cmBoxParques.setModel(new DefaultComboBoxModel<Parque>(agencia.getParques()));
			cmBoxParques.setBackground(Color.WHITE);
		}
		return cmBoxParques;
	}

	private void actualizaImagen() {

		String ubicacion = "/img/" + ((Parque) getCmBoxParques().getSelectedItem()).getCodigo() + ".png";

		getLblParque().setIcon(
				new ImageIcon(escalaImagenes(ubicacion, getPanelImg().getHeight() - 50, getPanelImg().getWidth() / 2)));
	}

	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}

	private JPanel getPanelImg() {
		if (panelImg == null) {
			panelImg = new JPanel();
			panelImg.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					actualizaImagen();
				}
			});
			panelImg.setBackground(Color.WHITE);
			panelImg.setLayout(new BorderLayout(0, 0));
			panelImg.add(getLblParque());
		}
		return panelImg;
	}
	
	private JCalendar getCalendar() {
		if(calendar == null) {
			calendar = new JCalendar();
			calendar.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
						getBtnBuscar().setEnabled(calendar.getDate() != null);
						getBtnOferta().setEnabled(calendar.getDate() != null);
				}
			});
			calendar.setMinSelectableDate(new Date());
		}
		return calendar;
	}

	private JLabel getLblParque() {
		if (lblParque == null) {
			lblParque = new JLabel("");
			lblParque.setHorizontalTextPosition(SwingConstants.CENTER);
			lblParque.setHorizontalAlignment(SwingConstants.RIGHT);
			lblParque.setBorder(new EmptyBorder(10, 10, 10, 10));
			lblParque.setBackground(Color.WHITE);
		}
		return lblParque;
	}
	private JSpinner getSpinnerDias() {
		if (spinnerDias == null) {
			spinnerDias = new JSpinner();
			spinnerDias.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spinnerDias;
	}
	private JLabel getLblDias() {
		if (lblDias == null) {
			lblDias = new JLabel(textos.getString("principal.label.dias_estancia"));
			lblDias.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblDias;
	}
	private JPanel getPanelDias() {
		if (panelDias == null) {
			panelDias = new JPanel();
			panelDias.setBackground(Color.WHITE);
			panelDias.add(getLblDias());
			panelDias.add(getSpinnerDias());
		}
		return panelDias;
	}
	private JPanel getPanelPaises() {
		if (panelPaises == null) {
			panelPaises = new JPanel();
			panelPaises.setBackground(Color.WHITE);
			panelPaises.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
			panelPaises.add(getBtnEspaniol());
			panelPaises.add(getBtnIngles());
		}
		return panelPaises;
	}
	private JButton getBtnEspaniol() {
		if (btnEspaniol == null) {
			btnEspaniol = new JButton("");
			btnEspaniol.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vp.localizar(Locale.of("es"));
				}
			});
			btnEspaniol.setBorder(new LineBorder(new Color(0, 0, 0), 5));
			btnEspaniol.setFocusPainted(false);
			btnEspaniol.setBorderPainted(false);
			btnEspaniol.setIcon(new ImageIcon(escalaImagenes("/img/espaniol.png" , 30, 30)));
		}
		return btnEspaniol;
	}
	private JButton getBtnIngles() {
		if (btnIngles == null) {
			btnIngles = new JButton("");
			btnIngles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vp.localizar(Locale.of("en"));
				}
			});
			btnIngles.setBorder(new LineBorder(new Color(0, 0, 0), 5));
			btnIngles.setFocusPainted(false);
			btnIngles.setBorderPainted(false);
			btnIngles.setIcon(new ImageIcon(escalaImagenes("/img/ingles.png" , 30, 30)));
		}
		return btnIngles;
	}



	void actualizarTextos(ResourceBundle textos) {
		this.textos = textos;
		getBtnAyuda().setText(textos.getString("principal.btn.ayuda"));
		getBtnQnesSomos().setText(textos.getString("principal.btn.quienes_somos"));
		getBtnServicios().setText(textos.getString("principal.btn.servicios"));
		getBtnParquesAsociados().setText(textos.getString("principal.btn.parques_asociados"));
		getBtnOferta().setText(textos.getString("principal.btn.oferta.texto") + " " + agencia.getParqueOfertado().getNombre());
		getBtnOferta().setToolTipText(textos.getString("principal.btn.oferta.tooltip"));
		getLblAdultos().setText(textos.getString("principal.label.adultos"));
		getLblNinios().setText(textos.getString("principal.label.ninios"));
		getBtnBuscar().setText(textos.getString("principal.btn.buscar"));
		getBtnBuscar().setToolTipText(textos.getString("principal.btn.buscar.tooltip"));
		getLblDias().setText(textos.getString("principal.label.dias_estancia"));
		getBtnEspaniol().setToolTipText(textos.getString("tooltip.espaniol"));
		getBtnIngles().setToolTipText(textos.getString("tooltip.ingles"));
		getCalendar().setLocale(textos.getLocale());
		getCmBoxParques().setModel(new DefaultComboBoxModel<Parque>(agencia.getParques()));
		getBtnQnesSomos().setMnemonic(textos.getString("mnemonico.principal.btn.quienes_somos").charAt(0));
		getBtnServicios().setMnemonic(textos.getString("mnemonico.principal.btn.servicios").charAt(0));
		getBtnParquesAsociados().setMnemonic(textos.getString("mnemonico.principal.btn.parques_asociados").charAt(0));
		getBtnOferta().setMnemonic(textos.getString("mnemonico.principal.btn.oferta").charAt(0));
		getBtnBuscar().setMnemonic(textos.getString("mnemonico.principal.btn.buscar").charAt(0));
		getBtnAyuda().setMnemonic(textos.getString("mnemonico.ayuda").charAt(0));
		validate();
		repaint();
	}
}
