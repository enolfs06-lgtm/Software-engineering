package uo.cpm.modulo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Parque implements Comparable<Parque>{

	public final static double DESCUENTO_OFERTA = 0.15;

	private String codigo;
	private boolean oferta;
	private String nombre;
	private String pais;
	private String ciudad;
	private String descripcion;
	private double precioAdulto;
	private double precioNinio;
	private List<Date> dias = new ArrayList<>();

	public Parque(String codigo, String nombre, String pais, String ciudad, String descripcion, double precioAdulto,
			double precioNinio) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.precioAdulto = precioAdulto;
		this.precioNinio = precioNinio;
	}

	public String getCodigo() {
		return codigo;
	}

	public boolean isOferta() {
		return oferta;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPais() {
		return pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecioAdulto() {
		return precioAdulto;
	}

	public double getPrecioNinio() {
		return precioNinio;
	}

	@Override
	public String toString() {
		return  nombre + "," + ciudad ;
	}

	public void setOferta(boolean oferta) {
		this.oferta = oferta;
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
		Parque other = (Parque) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	public int getDias() {
		return dias.size();
	}
	
	public void addFecha(Date fecha) {
		dias.add(fecha);
	}

	public Date getPrimeraFecha() {
		dias.sort(null);
		return dias.get(0);
	}

	public double getImporte(int adultos, int ninios) {
		return ((adultos * precioAdulto) + (ninios * precioNinio)) * dias.size();
	}

	public double getDescuento(int adultos, int ninios) {
		return getImporte(adultos, ninios) * DESCUENTO_OFERTA;
	}

	@Override
	public int compareTo(Parque o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}

	public void eliminaFecha(Date fecha) {
		dias.remove(fecha);
	}

	public void eliminaFechas() {
		dias.clear();
	}
}
