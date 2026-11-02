package uo.cpm.modulo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uo.cpm.modulo.model.Alojamiento;
import uo.cpm.modulo.model.Parque;
import uo.cpm.modulo.service.Agencia;

public class FileUtil {

	public static void loadFile(String string, List<Parque> parques) {
		String linea;
		String[] datosParque = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(string));
			while (fichero.ready()) {
				linea = fichero.readLine();
				datosParque = linea.split("@");
				parques.add(new Parque(datosParque[0], datosParque[1], datosParque[2], datosParque[3], datosParque[4],
						Double.parseDouble(datosParque[5]), Double.parseDouble(datosParque[6])));
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	}

	public static void saveToFile(Agencia agencia) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			BufferedWriter fichero = new BufferedWriter(
					new FileWriter("files/" + agencia.getDni() + formato.format(new Date()) + ".dat"));
			fichero.write(agencia.getString());
			fichero.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}

	public static void loadFileAlojamientos(String string, List<Alojamiento> alojamientos) {
		String linea;
		String[] datosAlojamiento = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(string));
			while (fichero.ready()) {
				linea = fichero.readLine();
				datosAlojamiento = linea.split("@");
				alojamientos.add(
						new Alojamiento(datosAlojamiento[0], datosAlojamiento[1], Integer.parseInt(datosAlojamiento[2]),
								datosAlojamiento[3], datosAlojamiento[4], Double.parseDouble(datosAlojamiento[5])));
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	}

}
