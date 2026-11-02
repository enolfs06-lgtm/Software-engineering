package uo.cpm.modulo.model;

import java.util.Random;

public class Juego {
	private int casillaPremiada;
	public static int CANTIDAD_DE_CASILLAS = 5;
	
	public Juego() {
		Random r = new Random();
		casillaPremiada = r.nextInt(CANTIDAD_DE_CASILLAS);
	}
	
	public void relanzar() {
		Random r = new Random();
		casillaPremiada = r.nextInt(CANTIDAD_DE_CASILLAS);
	}
	
	public int getCasillaPremiada() {
		return casillaPremiada;
	}

}
