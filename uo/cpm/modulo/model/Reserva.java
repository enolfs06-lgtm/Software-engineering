package uo.cpm.modulo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Reserva {
	private Parque parque;
	private Alojamiento alojamiento;
	private double importe;
	private String nombreyAp;
	private String dni;
	private int adultos = 1;
	private int ninios = 0;
	private Date fechaEntrada = new Date();
	private int diasEstancia = 1;
	private boolean entradas;
	private boolean juegoGanado;
	private String correo;
	private String observaciones;

	public String getNombreyAp() {
		return nombreyAp;
	}

	public String getDni() {
		return dni;
	}

	public Parque getParque() {
		return parque;
	}

	public Alojamiento getAlojamiento() {
		return alojamiento;
	}

	public double getImporte() {
		return importe;
	}

	public int getAdultos() {
		return adultos;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public int getDiasEstancia() {
		return diasEstancia;
	}

	public void inicializar(Date dateEntrada, int dias, int adultos, int ninios, Parque parque, int habitaciones) {
		fechaEntrada = dateEntrada;
		this.diasEstancia = dias;
		this.adultos = adultos;
		this.ninios = ninios;
		this.parque = parque;
		if(alojamiento != null)
			alojamiento.setHabitaciones(habitaciones);
	}

	public int getNinios() {
		return ninios;
	}

	public void guardarFechasYPers(int adultos, int ninios, Date date, int dias) {
		this.adultos = adultos;
		this.ninios = ninios;
		this.fechaEntrada = date;
		this.diasEstancia = dias;
	}

	public void seleccionaAlojamiento(Alojamiento aloj) {
		this.alojamiento = aloj;
	}

	public String toString(ResourceBundle textos) {
	    StringBuilder sb = new StringBuilder();

	    SimpleDateFormat formato = new SimpleDateFormat(textos.getString("formato.fechas"));
	    
	    if(parque != null)
	    	sb.append(parque.getNombre() + "\n");
	    
	    if(dni != null) {
	        sb.append(dni + " - " + nombreyAp + "\n");
	    }


	    sb.append(textos.getString("agencia.recibo.separador") + "\n\n");


	    sb.append(textos.getString("reserva.titulo.datos") + "\n\n");
	    
	
	    if (entradas) {
	        sb.append(textos.getString("reserva.titulo.entradas") + "\n");

	        sb.append(textos.getString("reserva.label.fecha_inicio") + " " + formato.format(parque.getPrimeraFecha()) + 
	                textos.getString("reserva.label.num_dias") + " " + parque.getDias() + "\n");

	        sb.append(textos.getString("reserva.label.num_adultos") + " " + adultos + 
	                textos.getString("reserva.label.num_ninios") + " " + ninios + "\n");
	    }


	    if (alojamiento != null) {
	        sb.append(textos.getString("reserva.titulo.alojamiento") + "\n");
	        sb.append(textos.getString("reserva.label.alojamiento") + " " + alojamiento.getCodigo());
	        

	        sb.append(" / " + alojamiento.getTipo() + " / " + alojamiento.getCategoria() + 
	                textos.getString("reserva.label.estrellas") +
	                alojamiento.getDenominacion() + "\n");
	        

	        sb.append(textos.getString("reserva.label.fecha_inicio") + " " + formato.format(fechaEntrada) + 
	                textos.getString("reserva.label.num_noches") + " " + (diasEstancia) + "\n");

	        sb.append(textos.getString("reserva.label.num_personas") + " " + (ninios + adultos) + "\n");
	    }

	    sb.append(textos.getString("reserva.titulo.importe") + "\n");
	    
	    boolean ofertado = false;
	    double importeParque = 0;
	    double importeAlojamiento = 0;
	    double descuento = 0;
	    
	    if (entradas && parque != null) {
	        importeParque = parque.getImporte(adultos, ninios);
	        sb.append(textos.getString("reserva.pago.entradas") + String.format("%.2f", importeParque) + textos.getString("moneda") + "\n");
	        ofertado = parque.isOferta();
	    }
	    
	    if (alojamiento != null) {
	        importeAlojamiento = alojamiento.getImporte(adultos, ninios, diasEstancia);
	        sb.append(textos.getString("reserva.pago.alojamiento") + String.format("%.2f", importeAlojamiento) + textos.getString("moneda") + "\n");
	        ofertado = alojamiento.isOfertado();
	    }

	    if (ofertado) {
	    	descuento = calculaDescuento();
	        sb.append(textos.getString("reserva.pago.descuento") + String.format("%.2f", descuento ) + textos.getString("moneda") + "\n\n");
	    }
	    
	    if(juegoGanado) {
	        sb.append(textos.getString("reserva.pago.bono") + "\n");
	    }
	    
	    sb.append(textos.getString("reserva.pago.total") + String.format("%.2f", (importeAlojamiento + importeParque) - descuento) + textos.getString("moneda") + "\n");
	    
	    return sb.toString();
	}

	private double calculaDescuento() {
		if (parque == null) {
			return alojamiento.getDescuento(adultos, ninios, diasEstancia);
		} else if (alojamiento == null) {
			return parque.getDescuento(adultos, ninios);
		}
		return parque.getDescuento(adultos, ninios) + alojamiento.getDescuento(adultos, ninios, diasEstancia);
	}

	public double getPrecioParqueNinio() {
		return parque.getPrecioNinio();
	}

	public String getCodigoParque() {
		return parque.getCodigo();
	}

	public String getNombreParque() {
		return parque.getNombre();
	}

	public String getCiudad() {
		return parque.getCiudad();
	}

	public String getPais() {
		return parque.getPais();
	}

	public double getPrecioParqueAdulto() {
		return parque.getPrecioAdulto();
	}

	public void setFecha(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void addFechaParque(Date fecha) {
		parque.addFecha(fecha);
	}

	public int getDiasEstanciaParque() {
		return parque.getDias();
	}

	public void iniciar() {
		this.parque = null;
		this.alojamiento = null;
		this.importe = 0.0;
		this.nombreyAp = null;
		this.dni = null;
		this.adultos = 1;
		this.ninios = 0;
		this.fechaEntrada = new Date();
		this.diasEstancia = 1;
		this.entradas = false;
	}

	public void setDatos(String nYAp, String dni, String correo, String obs) {
		this.nombreyAp = nYAp;
		this.dni = dni;
		this.correo= correo;
		this.observaciones = obs;
	}

	public void setEntradas(boolean seleccionEntradas) {
		this.entradas = seleccionEntradas;
	}

	public void resultadoJuego(boolean resultado) {
		this.juegoGanado = resultado;
	}

	public String getCorreo() {
		return correo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void eliminaFechaParque(Date fecha) {
		parque.eliminaFecha(fecha);
	}

	public void setDiasEstancia(int value) {
		this.diasEstancia = value;
	}

	public void setEntrada(Date date) {
		this.fechaEntrada = date;
	}

	public void eliminaFechasParque() {
		parque.eliminaFechas();
	}

}
