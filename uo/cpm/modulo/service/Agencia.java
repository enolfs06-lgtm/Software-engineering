package uo.cpm.modulo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import uo.cpm.modulo.model.Alojamiento;
import uo.cpm.modulo.model.Juego;
import uo.cpm.modulo.model.Parque;
import uo.cpm.modulo.model.Reserva;
import uo.cpm.modulo.util.FileUtil;

public class Agencia {
	
	private List<Parque> parques = new ArrayList<>();
	private List<Alojamiento> alojamientos = new ArrayList<>();

	private Reserva reserva;
	
	private Juego juego;
	
	private ResourceBundle textos;
	
	private Parque parqueOfertado;
	
	public Agencia(ResourceBundle textos) {
		reserva = new Reserva();
		this.textos = textos;
		cargaParques();
		cargaAlojamientos();
		inicializaOferta();
		juego = new Juego();
	}

	private void cargaAlojamientos() {
		FileUtil.loadFileAlojamientos("files/" + textos.getString("fichero.alojamientos") + ".dat", alojamientos);
	}

	private void inicializaOferta() {
		Random r = new Random();
		parqueOfertado = parques.get(r.nextInt(parques.size()));
		parqueOfertado.setOferta(true);
		
		inicializaOfertaAlojamientos();
	}

	private void inicializaOfertaAlojamientos() {
		List<Alojamiento> alojs = getAlojamientos(parqueOfertado);
		
		for(Alojamiento aloj : alojs) {
			aloj.setOfertado(true);
		}
	}

	private void cargaParques() {
		FileUtil.loadFile("files/" + textos.getString("fichero.parques") + ".dat", parques);
		parques.sort(null);
	}

	public Parque[] getParques() {
		return  parques.toArray(new Parque[parques.size()]);
	}

	public Parque getParqueOfertado() {
		for(Parque parque: parques) {
			if(parque.isOferta()) {
				return parque;
			}
		}
		return null;
	}

	public void inicializarReserva(Date dateEntrada, int dias, int adultos, int ninios, Parque parque, int habitaciones) {
		reserva.inicializar(dateEntrada, dias, adultos, ninios, parque, habitaciones);
	}

	public int getDiasEstancia() {
		return reserva.getDiasEstancia();
	}

	public Date getDateEntrada() {
		return reserva.getFechaEntrada();
	}

	public int getAdultos() {
		return reserva.getAdultos();
	}

	public int getNinios() {
		return reserva.getNinios();
	}

	public Parque getParqueSeleccionado() {
		return reserva.getParque();
	}

	public List<Alojamiento> getAlojamientos(Parque parqueSeleccionado) {
		List<Alojamiento> alojamientosParque = new ArrayList<>();
		
		for(Alojamiento aloj : alojamientos) {
			if(aloj.getParqueAsociado().equals(parqueSeleccionado.getCodigo())) {
				alojamientosParque.add(aloj);
			}
		}
		return alojamientosParque;
	}

	public List<Alojamiento> getApartaHoteles(Parque parque) {
		List<Alojamiento> alojamientosParque = getAlojamientos(parque);
		List<Alojamiento> apartaHoteles = new ArrayList<>();
		for(Alojamiento aloj : alojamientosParque) {
			if(aloj.isApartaHotel()) {
				apartaHoteles.add(aloj);
			}
		}
		return apartaHoteles;
	}

	public List<Alojamiento> getHoteles(Parque parque) {
		List<Alojamiento> alojamientosParque = getAlojamientos(parque);
		List<Alojamiento> hoteles = new ArrayList<>();
		for(Alojamiento aloj : alojamientosParque) {
			if(aloj.isHotel()) {
				hoteles.add(aloj);
			}
		}
		return hoteles;
	}

	public List<Alojamiento> getApartamentos(Parque parque) {
		List<Alojamiento> alojamientosParque = getAlojamientos(parque);
		List<Alojamiento> apartamento = new ArrayList<>();
		for(Alojamiento aloj : alojamientosParque) {
			if(aloj.isApartamento()) {
				apartamento.add(aloj);
			}
		}
		return apartamento;
	}

	public int getMinimoHabitaciones(Alojamiento aloj, int adultos, int ninios) {
		int personas = (adultos + ninios);
		
		switch(aloj.getTipo()) {
		case HO:
			return (int)Math.ceil((double)personas / (double)Alojamiento.NUMERO_PERSONAS_HABITACION_HOTEL);
		default:
			return (int)Math.ceil((double)personas / (double) Alojamiento.NUMERO_PERSONAS_HABITACION_APART);
		}
	}

	public void guardarFechasYPers(int adultos, int ninios, Date date, int dias) {
		reserva.guardarFechasYPers(adultos,ninios,date, dias);
	}

	public void seleccionaAlojamiento(Alojamiento aloj) {
		reserva.seleccionaAlojamiento(aloj);
	}

	public Alojamiento getAlojamiento() {
		return reserva.getAlojamiento();
	}

	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		SimpleDateFormat formato = new SimpleDateFormat(textos.getString("formato.fechas"));
		
			sb.append(textos.getString("agencia.recibo.cabecera") + " \n \n");
		
			sb.append(textos.getString("agencia.recibo.separador")+"\n");
		
			sb.append(textos.getString("agencia.recibo.titulo"));
			sb.append(formato.format(new Date()) + " - ");
		
			sb.append(reserva.toString(textos));
		
		return sb.toString();
	}

	public double getPrecioParqueNinio() {
		return reserva.getPrecioParqueNinio();
	}

	public String getCodigoParque() {
		return reserva.getCodigoParque();
	}

	public String getNombreParque() {
		return reserva.getNombreParque();
	}

	public String getCiudad() {
		return reserva.getCiudad();
	}

	public String getPais() {
		return reserva.getPais();
	}

	public double getPrecioParqueAdulto() {
		return reserva.getPrecioParqueAdulto();
	}

	public void addFechaParque(Date fecha) {
		reserva.addFechaParque(fecha);
	}

	public int getDiasEstanciaParque() {
		return reserva.getDiasEstanciaParque();
	}

	public void iniciar() {
		reserva.iniciar();
		juego.relanzar();
	}

	public void setDatos(String nYAp, String dni, String correo, String obs) {
		reserva.setDatos(nYAp, dni, correo, obs);
	}

	public void setEntradas(boolean seleccionEntradas) {
		reserva.setEntradas(seleccionEntradas);
	}
	
	public boolean isCasillaPremiada(int i) {
		return i == juego.getCasillaPremiada();
	}

	public void resultadoJuego(boolean resultado) {
		reserva.resultadoJuego(resultado);
	}

	public void finalizarReserva() {
		FileUtil.saveToFile(this);
		iniciar();
	}

	public String getDni() {
		return reserva.getDni();
	}

	public void actualizarTexto(ResourceBundle textos) {
		this.textos = textos;
		
		parques.clear();
		alojamientos.clear();
		
		cargaParques();
		
		int posicionOfertado = parques.indexOf(parqueOfertado);
		
		parques.get(posicionOfertado).setOferta(true);
		
		cargaAlojamientos();
		inicializaOfertaAlojamientos();
	}

	public int getMaximoHabitaciones(Alojamiento aloj, int adultos, int ninios) {
		if(aloj.isHotel()) {
			return getMinimoHabitaciones(aloj, adultos, ninios);
		}
		return adultos + ninios;
	}

	public int getCasillaPremiada() {
		return juego.getCasillaPremiada();
	}

	public void eliminaFecha(Date fecha) {
		reserva.eliminaFechaParque(fecha);
	}

	public void setDiasEstancia(int value) {
		reserva.setDiasEstancia(value);
	}

	public void setFechaEntrada(Date date) {
		reserva.setEntrada(date);
	}

	public void eliminaFechasParque() {
		reserva.eliminaFechasParque();
	}
	
	
}
