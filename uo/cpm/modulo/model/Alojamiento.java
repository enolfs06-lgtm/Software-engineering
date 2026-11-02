package uo.cpm.modulo.model;

import java.util.Objects;

public class Alojamiento {
	
	public static final int NUMERO_PERSONAS_HABITACION_HOTEL = 2;
	public static final int NUMERO_PERSONAS_HABITACION_APART = 4;

	private String denominacion;
	private int categoria;
	private Alojamientos tipo;
	private String codigo;
	private String parqueAsociado;
	private double price;
	private boolean ofertado;
	int cantidadHabitaciones = 1;
	
	public Alojamiento(String codigo, String tipo, int categoria, String denominacion, String parqueAsociado, double price ) {
		this.codigo = codigo;
		this.tipo = getTipoAlojamiento(tipo);
		this.categoria = categoria;
		this.denominacion = denominacion;
		this.parqueAsociado = parqueAsociado;
		this.price = price;
	}
	
	private Alojamientos getTipoAlojamiento(String tipo) {
		switch(tipo) {
		case "HO":
			return Alojamientos.HO;
		case "AH":
			return Alojamientos.AH;
		case "AP":
			return Alojamientos.AP;
		default:
			return null;
		}
	}
	
	public void setOfertado(boolean oferta) {
		this.ofertado = oferta;
	}

	public String getDenominacion() {
		return denominacion;
	}
	public int getCategoria() {
		return categoria;
	}
	public Alojamientos getTipo() {
		return tipo;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getParqueAsociado() {
		return parqueAsociado;
	}
	public double getPrice() {
		return price;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alojamiento other = (Alojamiento) obj;
		return Objects.equals(codigo, other.codigo);
	}

	public boolean isHotel() {
		return tipo.equals(Alojamientos.HO);
	}

	public boolean isApartaHotel() {
		return tipo.equals(Alojamientos.AH);
	}

	public boolean isApartamento() {
		return tipo.equals(Alojamientos.AP);
	}

	public double getImporte(int adultos, int ninios, int dias) {
		if(tipo == Alojamientos.HO)
			return ((adultos + ninios) * price) * dias;
		else
			return price * dias * cantidadHabitaciones;
	}

	public boolean isOfertado() {
		return ofertado;
	}

	public double getDescuento(int adultos, int ninios, int dias) {
		return getImporte(adultos, ninios, dias) * Parque.DESCUENTO_OFERTA;
	}

	public void setHabitaciones(int habitaciones) {
		cantidadHabitaciones = habitaciones;
	}
	
}
