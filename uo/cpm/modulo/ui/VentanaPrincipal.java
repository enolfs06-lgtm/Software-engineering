package uo.cpm.modulo.ui;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.cpm.modulo.service.Agencia;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Agencia agencia;
	private JMenuBar menuBarPr;
	private JMenu mnOpciones;
	private JMenu mnContenidos;
	private JMenuItem mntmNuevaRes;
	private JSeparator separador;
	private JMenuItem mntmSalir;
	private JMenuItem mntmAcercaDe;
	private JMenuItem mntmAyuda;
	private JSeparator separador1;
	private ResourceBundle textos;
	private PanelPrincipal panelPrincipal;
	private ParquesAsociados parquesAsociados;
	private PanelReserva panelReserva;
	private PanelPago panelPago;
	private PanelFinal panelFinal;
	private PanelServicios panelServicios;
	private PanelQuienesSomos panelQuienesSomos;
	private HelpBroker hb;
	private HelpSet hs;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, textos.getString("confirmar.salir"),
						textos.getString("titulo.acercaDe"), JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		localizar(Locale.getDefault(Locale.Category.FORMAT));
		agencia = new Agencia(textos);
		cargaAyuda();
		setTitle(textos.getString("ventana.titulo"));
		setMinimumSize(new Dimension(1000, 717));
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/imagenCompañia.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 833, 494);
		setLocationRelativeTo(null);
		panelPrincipal = new PanelPrincipal(this, this.agencia, this.textos, hb, hs);
		parquesAsociados = new ParquesAsociados(this, this.textos, this.agencia);
		panelReserva = new PanelReserva(this, this.agencia, this.textos, hb, hs);
		panelPago = new PanelPago(this, this.agencia, this.textos, hb, hs);
		panelFinal = new PanelFinal(this, this.agencia, this.textos, hb, hs);
		panelServicios = new PanelServicios(this, this.textos);
		panelQuienesSomos = new PanelQuienesSomos(this, this.textos);
		setJMenuBar(getMenuBarPr());
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		contentPane.add(panelPrincipal, "panelPrincipal");
		contentPane.add(parquesAsociados, "panelParqAsoc");
		contentPane.add(panelReserva, "panelReserva");
		contentPane.add(panelPago, "panelPago");
		contentPane.add(panelFinal, "panelFinal");
		contentPane.add(panelServicios, "panelServicios");
		contentPane.add(panelQuienesSomos, "panelQuienesSomos");
		localizar(Locale.getDefault(Locale.Category.FORMAT));
	}

	private void cargaAyuda() {

		URL hsURL;

		try {
			File fichero = new File("help/ayuda.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}

		catch (Exception e) {
			System.out.println("Ayuda no encontrada");
			return;
		}

		hb = hs.createHelpBroker();
		hb.initPresentation();

		hb.enableHelpKey(getRootPane(), "agencia", hs);
		hb.enableHelpOnButton(getMntmAyuda(), "agencia", hs);
	}

	void localizar(Locale localizacion) {
		textos = ResourceBundle.getBundle("rsc/Textos", localizacion);
		actualizarTextos();
		if (panelPrincipal != null) {
			agencia.actualizarTexto(textos);
			panelPrincipal.actualizarTextos(textos);
			panelReserva.actualizarTextos(textos);
			panelPago.actualizarTextos(textos);
			panelFinal.actualizarTextos(textos);
			parquesAsociados.actualizaTextos(textos);
			panelServicios.actualizaTextos(textos);
			panelQuienesSomos.actualizaTextos(textos);
		}
	}

	private void actualizarTextos() {
		setTitle(textos.getString("ventana.titulo"));
		getMnOpciones().setText(textos.getString("menu.opciones"));
		getMnContenidos().setText(textos.getString("menu.contenidos"));
		getMntmNuevaRes().setText(textos.getString("menu.nueva_reserva"));
		getMntmSalir().setText(textos.getString("menu.salir"));
		getMntmAcercaDe().setText(textos.getString("menu.acerca_de"));
		getMntmAyuda().setText(textos.getString("principal.btn.ayuda"));
		getMnOpciones().setMnemonic(textos.getString("mnemonico.opciones").charAt(0));
		getMnContenidos().setMnemonic(textos.getString("mnemonico.contenidos").charAt(0));
		getMntmNuevaRes().setMnemonic(textos.getString("mnemonico.nueva_reserva").charAt(0));
		getMntmSalir().setMnemonic(textos.getString("mnemonico.salir").charAt(0));
		getMntmAcercaDe().setMnemonic(textos.getString("mnemonico.acerca_de").charAt(0));
		getMntmAyuda().setMnemonic(textos.getString("mnemonico.ayuda").charAt(0));
	}

	private JMenuBar getMenuBarPr() {
		if (menuBarPr == null) {
			menuBarPr = new JMenuBar();
			menuBarPr.add(getMnOpciones());
			menuBarPr.add(getMnContenidos());
		}
		return menuBarPr;
	}

	private JMenu getMnOpciones() {
		if (mnOpciones == null) {
			mnOpciones = new JMenu(textos.getString("menu.opciones"));
			mnOpciones.add(getMntmNuevaRes());
			mnOpciones.add(getSeparador());
			mnOpciones.add(getMntmSalir());
		}
		return mnOpciones;
	}

	private JMenu getMnContenidos() {
		if (mnContenidos == null) {
			mnContenidos = new JMenu(textos.getString("menu.contenidos"));
			mnContenidos.add(getMntmAyuda());
			mnContenidos.add(getSeparador1());
			mnContenidos.add(getMntmAcercaDe());
		}
		return mnContenidos;
	}

	private JMenuItem getMntmNuevaRes() {
		if (mntmNuevaRes == null) {
			mntmNuevaRes = new JMenuItem(textos.getString("menu.nueva_reserva"));
			mntmNuevaRes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agencia.iniciar();
					panelPago.borrarTxt();
					activarBotonesFinal();
					eliminarPanelesFiltro();
					((CardLayout) getContentPane().getLayout()).show(getContentPane(), "panelPrincipal");
				}
			});
			mntmNuevaRes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		}
		return mntmNuevaRes;
	}

	private JSeparator getSeparador() {
		if (separador == null) {
			separador = new JSeparator();
		}
		return separador;
	}

	private JMenuItem getMntmSalir() {
		if (mntmSalir == null) {
			mntmSalir = new JMenuItem(textos.getString("menu.salir"));
			mntmSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		}
		return mntmSalir;
	}

	private JMenuItem getMntmAcercaDe() {
		if (mntmAcercaDe == null) {
			mntmAcercaDe = new JMenuItem(textos.getString("menu.acerca_de"));
			mntmAcercaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, textos.getString("texto.acercaDe"),
							textos.getString("titulo.acercaDe"), JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return mntmAcercaDe;
	}

	private JMenuItem getMntmAyuda() {
		if (mntmAyuda == null) {
			mntmAyuda = new JMenuItem(textos.getString("principal.btn.ayuda"));
			mntmAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return mntmAyuda;
	}

	private JSeparator getSeparador1() {
		if (separador1 == null) {
			separador1 = new JSeparator();
		}
		return separador1;
	}

	void eliminarPanelesFiltro() {
		panelReserva.eliminarPanelesFiltro();
	}

	void eliminarTxt() {
		panelPago.borrarTxt();
	}

	public void activarBotonesFinal() {
		panelFinal.activaBotones();
	}

	void reiniciaSeleccionados() {
		panelReserva.reiniciaSeleccionados();
	}
}