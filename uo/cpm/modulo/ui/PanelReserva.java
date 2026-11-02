package uo.cpm.modulo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JCalendar;
import uo.cpm.modulo.model.Alojamiento;
import uo.cpm.modulo.model.Parque;
import uo.cpm.modulo.service.Agencia;

import javax.swing.ImageIcon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class PanelReserva extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame vp;
	private JPanel panelIzqda;
	private JPanel panelDcha;
	private JTabbedPane tabbedPaneFiltros;
	private JPanel panelHabitaciones;
	private JSpinner spinnerHabitaciones;
	private JLabel lblHabitaciones;
	private JPanel panelFiltros;
	private JPanel panelIzqdaFiltros;
	private JPanel panelDchaFiltros;
	private JCheckBox chckbxHotel;
	private JCheckBox chckbxApartamento;
	private JCheckBox chckbxApartaHotel;
	private JCheckBox chckbxEntradas;
	private JCheckBox chckbxDoble;
	private JLabel lblNuevasFechas;
	private JPanel panelNuevasFechas;
	private JPanel panelPagos;
	private JLabel lblPagos;
	private JPanel panelNorteDcha;
	private JPanel panelCentroDcha;
	private JPanel panelSurDcha;
	private ResourceBundle textos;
	private Agencia agencia;
	private JLabel lblAdultos;
	private JSpinner spinnerAdultos;
	private JLabel lblNinios;
	private JSpinner spinnerNinios;
	private JScrollPane scrollOfertas;
	private JButton btnReservar;
	private JButton btnCambiarParque;
	private JPanel panelOfertas;
	private PanelEntradas panelEntradas;
	private JCalendar calendar;
	private JPanel panelDias;
	private JLabel lblDias;
	private JSpinner spinnerDias;
	private boolean alojamientoSeleccionado;
	private PanelAlojamiento panelAlojamientoSeleccionado;
	private boolean entradasSeleccionadas;
	private HelpBroker hb;
	private HelpSet hs;

	/**
	 * Create the panel.
	 */
	public PanelReserva(JFrame vp, Agencia agencia, ResourceBundle textos, HelpBroker hb, HelpSet hs) {
		this.textos = textos;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				getCalendar().setDate(agencia.getDateEntrada());
				getSpinnerDias().setValue(agencia.getDiasEstancia());
				getSpinnerAdultos().setValue(agencia.getAdultos());
				getSpinnerNinios().setValue(agencia.getNinios());
				getPanelOfertas().removeAll();
				inicializarFiltros();
				mostrarOfertas(agencia.getParqueSeleccionado());
				vp.getRootPane().setDefaultButton(getBtnReservar());
			}
		});
		this.hb = hb;
		this.hs = hs;
		this.vp = vp;
		this.agencia = agencia;
		setLayout(new GridLayout(1, 0, 0, 0));
		add(getPanelIzqda());
		add(getPanelDcha());
		iniciaAyuda();
	}

	private void iniciaAyuda() {
		hb.enableHelpKey(this, "alojamiento", hs);
		hb.enableHelpKey(getCalendar(), "fecha", hs);
		hb.enableHelpKey(getSpinnerAdultos(), "personas", hs);
		hb.enableHelpKey(getSpinnerNinios(), "personas", hs);
		hb.enableHelpKey(getSpinnerDias(), "noches", hs);
		hb.enableHelpKey(getSpinnerHabitaciones(), "habitaciones", hs);
	}

	private void inicializarFiltros() {
		getChckbxApartaHotel().setSelected(false);
		getChckbxApartamento().setSelected(false);
		getChckbxEntradas().setSelected(false);
		getChckbxHotel().setSelected(false);
		getChckbxDoble().setSelected(true);
	}

	void reiniciaSeleccionados() {
		agencia.setEntradas(false);
		agencia.seleccionaAlojamiento(null);
	}

	private void mostrarOfertas(Parque parqueSeleccionado) {
		getPanelOfertas().removeAll();

		if (!getChckbxDoble().isSelected() || panelEntradas == null) {
			panelEntradas = new PanelEntradas(agencia, this,
					textos);
		}

		getPanelOfertas().add(panelEntradas);

		creaAlojamientos(agencia.getAlojamientos(parqueSeleccionado));

		repaint();
		revalidate();

	}

	private JPanel getPanelIzqda() {
		if (panelIzqda == null) {
			panelIzqda = new JPanel();
			panelIzqda.setBackground(Color.WHITE);
			panelIzqda.setLayout(new BoxLayout(panelIzqda, BoxLayout.Y_AXIS));
			panelIzqda.add(getTabbedPaneFiltros());
			panelIzqda.add(getPanelHabitaciones());
			panelIzqda.add(getPanelPagos());
		}
		return panelIzqda;
	}

	private JPanel getPanelDcha() {
		if (panelDcha == null) {
			panelDcha = new JPanel();
			panelDcha.setBorder(new EmptyBorder(5, 0, 0, 0));
			panelDcha.setBackground(Color.WHITE);
			panelDcha.setLayout(new BorderLayout(0, 0));
			panelDcha.add(getPanelNorteDcha(), BorderLayout.NORTH);
			panelDcha.add(getPanelCentroDcha(), BorderLayout.CENTER);
			panelDcha.add(getPanelSurDcha(), BorderLayout.SOUTH);
		}
		return panelDcha;
	}

	private JTabbedPane getTabbedPaneFiltros() {
		if (tabbedPaneFiltros == null) {
			tabbedPaneFiltros = new JTabbedPane(JTabbedPane.TOP);
			tabbedPaneFiltros.setBorder(null);
			tabbedPaneFiltros.setBounds(new Rectangle(300, 400, 300, 400));
			tabbedPaneFiltros.setName("");
			tabbedPaneFiltros.setBackground(Color.WHITE);
			tabbedPaneFiltros.addTab(textos.getString("reserva.tab.filtros"), null, getPanelFiltros(), null);
		}
		return tabbedPaneFiltros;
	}

	private JPanel getPanelHabitaciones() {
		if (panelHabitaciones == null) {
			panelHabitaciones = new JPanel();
			panelHabitaciones.setBackground(Color.WHITE);
			GridBagLayout gbl_panelHabitaciones = new GridBagLayout();
			gbl_panelHabitaciones.columnWidths = new int[] { 226, 0, 0, 0, 0, 0, 0, 0, 226, 0 };
			gbl_panelHabitaciones.rowHeights = new int[] { 20, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panelHabitaciones.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panelHabitaciones.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			panelHabitaciones.setLayout(gbl_panelHabitaciones);
			GridBagConstraints gbc_lblHabitaciones = new GridBagConstraints();
			gbc_lblHabitaciones.gridwidth = 3;
			gbc_lblHabitaciones.fill = GridBagConstraints.BOTH;
			gbc_lblHabitaciones.insets = new Insets(0, 0, 5, 5);
			gbc_lblHabitaciones.gridx = 0;
			gbc_lblHabitaciones.gridy = 1;
			panelHabitaciones.add(getLblHabitaciones(), gbc_lblHabitaciones);
			GridBagConstraints gbc_spinnerHabitaciones = new GridBagConstraints();
			gbc_spinnerHabitaciones.gridwidth = 3;
			gbc_spinnerHabitaciones.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerHabitaciones.fill = GridBagConstraints.BOTH;
			gbc_spinnerHabitaciones.gridx = 4;
			gbc_spinnerHabitaciones.gridy = 1;
			panelHabitaciones.add(getSpinnerHabitaciones(), gbc_spinnerHabitaciones);
		}
		return panelHabitaciones;
	}

	private JSpinner getSpinnerHabitaciones() {
		if (spinnerHabitaciones == null) {
			spinnerHabitaciones = new JSpinner();
			spinnerHabitaciones
					.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spinnerHabitaciones;
	}

	private JLabel getLblHabitaciones() {
		if (lblHabitaciones == null) {
			lblHabitaciones = new JLabel(textos.getString("reserva.label.hab_cantidad"));
			lblHabitaciones.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblHabitaciones;
	}

	private JPanel getPanelFiltros() {
		if (panelFiltros == null) {
			panelFiltros = new JPanel();
			panelFiltros.setBackground(Color.WHITE);
			panelFiltros.setLayout(new GridLayout(1, 0, 0, 0));
			panelFiltros.add(getPanelIzqdaFiltros());
			panelFiltros.add(getPanelDchaFiltros());
		}
		return panelFiltros;
	}

	private JPanel getPanelIzqdaFiltros() {
		if (panelIzqdaFiltros == null) {
			panelIzqdaFiltros = new JPanel();
			panelIzqdaFiltros.setBackground(Color.WHITE);
			panelIzqdaFiltros.setLayout(new GridLayout(0, 1, 0, 0));
			panelIzqdaFiltros.add(getChckbxHotel());
			panelIzqdaFiltros.add(getChckbxApartamento());
			panelIzqdaFiltros.add(getChckbxApartaHotel());
			panelIzqdaFiltros.add(getChckbxEntradas());
			panelIzqdaFiltros.add(getChckbxDoble());
		}
		return panelIzqdaFiltros;
	}

	private JPanel getPanelDchaFiltros() {
		if (panelDchaFiltros == null) {
			panelDchaFiltros = new JPanel();
			panelDchaFiltros.setBackground(Color.WHITE);
			panelDchaFiltros.setLayout(new BorderLayout(0, 0));
			panelDchaFiltros.add(getLblNuevasFechas(), BorderLayout.NORTH);
			panelDchaFiltros.add(getPanelNuevasFechas(), BorderLayout.CENTER);
		}
		return panelDchaFiltros;
	}

	private JCheckBox getChckbxHotel() {
		if (chckbxHotel == null) {
			chckbxHotel = new JCheckBox(textos.getString("reserva.filtro.hotel"));
			chckbxHotel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarOfertasSeleccionadas();
				}
			});
			chckbxHotel.setBackground(Color.WHITE);
		}
		return chckbxHotel;
	}

	private JCheckBox getChckbxApartamento() {
		if (chckbxApartamento == null) {
			chckbxApartamento = new JCheckBox(textos.getString("reserva.filtro.apartamento"));
			chckbxApartamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarOfertasSeleccionadas();
				}
			});
			chckbxApartamento.setBackground(Color.WHITE);
		}
		return chckbxApartamento;
	}

	private JCheckBox getChckbxApartaHotel() {
		if (chckbxApartaHotel == null) {
			chckbxApartaHotel = new JCheckBox(textos.getString("reserva.filtro.apartahotel"));
			chckbxApartaHotel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarOfertasSeleccionadas();
				}
			});
			chckbxApartaHotel.setBackground(Color.WHITE);
		}
		return chckbxApartaHotel;
	}

	private JCheckBox getChckbxEntradas() {
		if (chckbxEntradas == null) {
			chckbxEntradas = new JCheckBox(textos.getString("reserva.filtro.solo_entradas"));
			chckbxEntradas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarOfertasSeleccionadas();
				}
			});
			chckbxEntradas.setBackground(Color.WHITE);
		}
		return chckbxEntradas;
	}

	private JCheckBox getChckbxDoble() {
		if (chckbxDoble == null) {
			chckbxDoble = new JCheckBox(textos.getString("reserva.filtro.alojamiento_entradas"));
			chckbxDoble.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getChckbxDoble().isSelected())
						mostrarOfertas(agencia.getParqueSeleccionado());
					else
						mostrarOfertasSeleccionadas();
				}
			});
			chckbxDoble.setSelected(true);
			chckbxDoble.setBackground(Color.WHITE);
		}
		return chckbxDoble;
	}

	private void mostrarOfertasSeleccionadas() {
		if (!getChckbxDoble().isSelected()) {
			getPanelOfertas().removeAll();

			if (getChckbxEntradas().isSelected()) {
				getPanelOfertas().add(panelEntradas);
			}

			if (getChckbxHotel().isSelected()) {
				creaAlojamientos(agencia.getHoteles(agencia.getParqueSeleccionado()));
			}

			if (getChckbxApartaHotel().isSelected()) {
				creaAlojamientos(agencia.getApartaHoteles(agencia.getParqueSeleccionado()));
			}

			if (getChckbxApartamento().isSelected()) {
				creaAlojamientos(agencia.getApartamentos(agencia.getParqueSeleccionado()));
			}

		}

		repaint();
		revalidate();

	}

	private void creaAlojamientos(List<Alojamiento> alojamientos) {

		for (Alojamiento aloj : alojamientos) {
			if (panelAlojamientoSeleccionado != null && aloj.equals(panelAlojamientoSeleccionado.getAlojamiento()))
				getPanelOfertas().add(panelAlojamientoSeleccionado);
			else
				getPanelOfertas().add(new PanelAlojamiento(aloj, textos, this, agencia));
		}
	}

	private JLabel getLblNuevasFechas() {
		if (lblNuevasFechas == null) {
			lblNuevasFechas = new JLabel(textos.getString("reserva.label.nuevas_fechas"));
			lblNuevasFechas.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return lblNuevasFechas;
	}

	private JPanel getPanelNuevasFechas() {
		if (panelNuevasFechas == null) {
			panelNuevasFechas = new JPanel();
			panelNuevasFechas.setLayout(new GridLayout(0, 1, 0, 0));
			panelNuevasFechas.add(getCalendar());
			panelNuevasFechas.add(getPanelDias());
		}
		return panelNuevasFechas;
	}

	private JPanel getPanelPagos() {
		if (panelPagos == null) {
			panelPagos = new JPanel();
			panelPagos.setLayout(new BorderLayout(0, 0));
			panelPagos.add(getLblPagos());
			panelPagos.setMinimumSize(new Dimension(1, 1));
			panelPagos.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					redimensionarImagenPago();
				}
			});
		}
		return panelPagos;
	}

	private JLabel getLblPagos() {
		if (lblPagos == null) {
			lblPagos = new JLabel("");
		}
		return lblPagos;
	}

	private Image escalaImagenes(String ubi, int largo, int ancho) {
		Image imgOriginal = new ImageIcon(getClass().getResource(ubi)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
		return imgEscalada;
	}

	private JPanel getPanelNorteDcha() {
		if (panelNorteDcha == null) {
			panelNorteDcha = new JPanel();
			panelNorteDcha.setBackground(Color.WHITE);
			GridBagLayout gbl_panelNorteDcha = new GridBagLayout();
			gbl_panelNorteDcha.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panelNorteDcha.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
			gbl_panelNorteDcha.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panelNorteDcha.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panelNorteDcha.setLayout(gbl_panelNorteDcha);
			GridBagConstraints gbc_lblAdultos = new GridBagConstraints();
			gbc_lblAdultos.insets = new Insets(0, 0, 5, 5);
			gbc_lblAdultos.gridx = 5;
			gbc_lblAdultos.gridy = 1;
			panelNorteDcha.add(getLblAdultos(), gbc_lblAdultos);
			GridBagConstraints gbc_spinnerAdultos = new GridBagConstraints();
			gbc_spinnerAdultos.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerAdultos.gridx = 6;
			gbc_spinnerAdultos.gridy = 1;
			panelNorteDcha.add(getSpinnerAdultos(), gbc_spinnerAdultos);
			GridBagConstraints gbc_lblNinios = new GridBagConstraints();
			gbc_lblNinios.insets = new Insets(0, 0, 5, 5);
			gbc_lblNinios.gridx = 8;
			gbc_lblNinios.gridy = 1;
			panelNorteDcha.add(getLblNinios(), gbc_lblNinios);
			GridBagConstraints gbc_spinnerNinios = new GridBagConstraints();
			gbc_spinnerNinios.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerNinios.gridx = 9;
			gbc_spinnerNinios.gridy = 1;
			panelNorteDcha.add(getSpinnerNinios(), gbc_spinnerNinios);
		}
		return panelNorteDcha;
	}

	private JPanel getPanelCentroDcha() {
		if (panelCentroDcha == null) {
			panelCentroDcha = new JPanel();
			panelCentroDcha.setBackground(Color.WHITE);
			panelCentroDcha.setLayout(new BorderLayout(0, 0));
			panelCentroDcha.add(getScrollOfertas());
		}
		return panelCentroDcha;
	}

	private JPanel getPanelSurDcha() {
		if (panelSurDcha == null) {
			panelSurDcha = new JPanel();
			panelSurDcha.setBackground(Color.WHITE);
			panelSurDcha.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelSurDcha.add(getBtnCambiarParque());
			panelSurDcha.add(getBtnReservar());
		}
		return panelSurDcha;
	}

	private JLabel getLblAdultos() {
		if (lblAdultos == null) {
			lblAdultos = new JLabel(textos.getString("reserva.label.adultos"));
			lblAdultos.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblAdultos.setBackground(Color.WHITE);
		}
		return lblAdultos;
	}

	private JSpinner getSpinnerAdultos() {
		if (spinnerAdultos == null) {
			spinnerAdultos = new JSpinner();
			spinnerAdultos
					.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spinnerAdultos.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					calculaMinHabitaciones(agencia.getAlojamiento());
				}
			});
			spinnerAdultos.setBackground(Color.WHITE);
		}
		return spinnerAdultos;
	}

	private JLabel getLblNinios() {
		if (lblNinios == null) {
			lblNinios = new JLabel(textos.getString("reserva.label.ninios"));
			lblNinios.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			lblNinios.setBackground(Color.WHITE);
		}
		return lblNinios;
	}

	private JSpinner getSpinnerNinios() {
		if (spinnerNinios == null) {
			spinnerNinios = new JSpinner();
			spinnerNinios
					.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
			spinnerNinios.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					calculaMinHabitaciones(agencia.getAlojamiento());
				}
			});
			spinnerNinios.setBackground(Color.WHITE);
		}
		return spinnerNinios;
	}

	private JScrollPane getScrollOfertas() {
		if (scrollOfertas == null) {
			scrollOfertas = new JScrollPane();
			scrollOfertas.setViewportView(getPanelOfertas());
			;
		}
		return scrollOfertas;
	}

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton(textos.getString("reserva.btn.reservar"));
			btnReservar.setEnabled(false);
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inicializarReserva();
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelPago");
				}
			});
			btnReservar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		}
		return btnReservar;
	}

	private void inicializarReserva() {
		agencia.inicializarReserva(getCalendar().getDate(), (int) getSpinnerDias().getValue(),
				(Integer) getSpinnerAdultos().getValue(), (Integer) getSpinnerNinios().getValue(),
				agencia.getParqueSeleccionado(), (int) getSpinnerHabitaciones().getValue());
	}

	private JButton getBtnCambiarParque() {
		if (btnCambiarParque == null) {
			btnCambiarParque = new JButton(textos.getString("reserva.btn.cambiar_parque"));
			btnCambiarParque.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
			btnCambiarParque.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agencia.guardarFechasYPers((int) getSpinnerAdultos().getValue(),
							(int) getSpinnerNinios().getValue(), getCalendar().getDate(),
							(int) getSpinnerDias().getValue());
					panelEntradas = null;
					((CardLayout) vp.getContentPane().getLayout()).show(vp.getContentPane(), "panelPrincipal");
				}
			});
		}
		return btnCambiarParque;
	}

	private void redimensionarImagenPago() {
		if (getPanelPagos().getWidth() > 0 && getPanelPagos().getHeight() > 0) {

			int ancho = getPanelPagos().getWidth();
			int alto = getPanelPagos().getHeight();

			Image imgEscalada = escalaImagenes("/img/pago.png", alto, ancho);

			getLblPagos().setIcon(new ImageIcon(imgEscalada));
		}
	}

	private JPanel getPanelOfertas() {
		if (panelOfertas == null) {
			panelOfertas = new JPanel();
			panelOfertas.setBackground(Color.WHITE);
			panelOfertas.setLayout(new BoxLayout(panelOfertas, BoxLayout.Y_AXIS));
		}
		return panelOfertas;
	}

	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (panelEntradas != null) {
						agencia.setFechaEntrada(getCalendar().getDate());
						panelEntradas.actualizarDias();
					}
				}
			});
			calendar.setMinSelectableDate(new Date());
		}
		return calendar;
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

	private JLabel getLblDias() {
		if (lblDias == null) {
			lblDias = new JLabel(textos.getString("reserva.label.dias_estancia"));
			lblDias.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
		}
		return lblDias;
	}

	private JSpinner getSpinnerDias() {
		if (spinnerDias == null) {
			spinnerDias = new JSpinner();
			spinnerDias
					.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spinnerDias.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (panelEntradas != null) {
						agencia.setDiasEstancia((int)getSpinnerDias().getValue());
						panelEntradas.actualizarDias();
					}
				}
			});
		}
		return spinnerDias;
	}

	void seleccionado(boolean seleccion, PanelAlojamiento alojamiento) {
		this.alojamientoSeleccionado = seleccion;
		this.panelAlojamientoSeleccionado = alojamiento;
		Component[] components = getPanelOfertas().getComponents();

		for (Component component : components) {
			if (!component.equals(panelEntradas)) {
				PanelAlojamiento panel = (PanelAlojamiento) component;
				panel.getChckbxSeleccion()
						.setEnabled(!alojamientoSeleccionado || panel.getChckbxSeleccion().isSelected());
			}
		}
	}

	void calculaMinHabitaciones(Alojamiento aloj) {
		if (aloj != null) {
			int minimo = agencia.getMinimoHabitaciones(aloj, (int) getSpinnerAdultos().getValue(),
					(int) getSpinnerNinios().getValue());
			int maximo = agencia.getMaximoHabitaciones(aloj, (int) getSpinnerAdultos().getValue(),
					(int) getSpinnerNinios().getValue());
			getSpinnerHabitaciones().setModel(new SpinnerNumberModel(minimo, minimo, maximo, 1));
		}
	}

	void entradas(boolean seleccionEntradas) {
		this.entradasSeleccionadas = seleccionEntradas;
		agencia.setEntradas(seleccionEntradas);
		actualizaBtnReservar();
	}

	void actualizaBtnReservar() {
		getBtnReservar().setEnabled(alojamientoSeleccionado || entradasSeleccionadas);
	}

	void eliminarPanelesFiltro() {
		this.panelAlojamientoSeleccionado = null;
		this.alojamientoSeleccionado = false;
		this.panelEntradas = null;
		getBtnReservar().setEnabled(false);
	}

	void actualizarTextos(ResourceBundle textos) {
		this.textos = textos;
		getLblHabitaciones().setText(textos.getString("reserva.label.hab_cantidad"));
		getLblNuevasFechas().setText(textos.getString("reserva.label.nuevas_fechas"));
		getLblAdultos().setText(textos.getString("reserva.label.adultos"));
		getLblNinios().setText(textos.getString("reserva.label.ninios"));
		getLblDias().setText(textos.getString("reserva.label.dias_estancia"));
		getBtnReservar().setText(textos.getString("reserva.btn.reservar"));
		getBtnCambiarParque().setText(textos.getString("reserva.btn.cambiar_parque"));
		getChckbxHotel().setText(textos.getString("reserva.filtro.hotel"));
		getChckbxApartamento().setText(textos.getString("reserva.filtro.apartamento"));
		getChckbxApartaHotel().setText(textos.getString("reserva.filtro.apartahotel"));
		getChckbxEntradas().setText(textos.getString("reserva.filtro.solo_entradas"));
		getChckbxDoble().setText(textos.getString("reserva.filtro.alojamiento_entradas"));
		getTabbedPaneFiltros().setTitleAt(0, textos.getString("reserva.tab.filtros"));
		getCalendar().setLocale(textos.getLocale());
		getChckbxHotel().setMnemonic(textos.getString("mnemonico.filtro.hotel").charAt(0));
		getChckbxApartamento().setMnemonic(textos.getString("mnemonico.filtro.apartamento").charAt(0));
		getChckbxApartaHotel().setMnemonic(textos.getString("mnemonico.filtro.apartahotel").charAt(0));
		getChckbxEntradas().setMnemonic(textos.getString("mnemonico.filtro.solo_entradas").charAt(0));
		getChckbxDoble().setMnemonic(textos.getString("mnemonico.filtro.alojamiento_entradas").charAt(0));
		getBtnReservar().setMnemonic(textos.getString("mnemonico.btn.reservar").charAt(0));
		getBtnCambiarParque().setMnemonic(textos.getString("mnemonico.btn.cambiar_parque").charAt(0));
		validate();
		repaint();
	}

	PanelAlojamiento getpanelAlojamiento() {
		return panelAlojamientoSeleccionado;
	}
}
