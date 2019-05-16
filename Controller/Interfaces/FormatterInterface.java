package Controller.Interfaces;

import java.io.IOException;

import Model.Karte;

public interface FormatterInterface {

	/**
	 * Exportiert die Karte in eine Textdatei.
	 * 
	 * @param karte Zu Interpretierende Karte
	 * @param filename absoluter Pfad der Ausgabedatei ohne Suffixe
	 * 
	 * @throws IOException
	 */
	public void export(Karte karte, String filename) throws IOException;
}
