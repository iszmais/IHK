package Controller.Interfaces;

import java.io.IOException;

import Model.Karte;

public interface InputReaderInterface {
	
	/**
	 * Liest einen Eingabepfad ein und erstellt anhand der Eingabedatei ein Karte.
	 * 
	 * @param filepath absoluter Pfad der Eingabedatei
	 * 
	 * @return Karte
	 * @throws IOException
	 */
	public Karte readFile(String filepath) throws IOException;

}
