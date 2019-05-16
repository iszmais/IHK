import java.io.IOException;

import Controller.Calculator;
import Controller.Formatter;
import Controller.InputReader;
import Model.Karte;
import View.UserGUI;

/**
 * Stellt die Basis des Programms bereit.
 * 
 * @author iszmais
 *
 */
public class main {

	/**
	 * Startet das Programm und Initialisiert alle Bassisklassen.
	 * 
	 * @param args
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// Sammle Nutzereingaben
		UserGUI gui = new UserGUI();
		
		String file = gui.getFileName();
		InputReader input = new InputReader();
		Karte map = input.readFile(file);
		
		int count = gui.getIterationCount();
		boolean ignoreOriginCenter = true;
		if(count <= 0) {
			ignoreOriginCenter = gui.getIgnoreOriginCenter();
		}
		
		// Berechne eine geeignete oder festgelegte Iteration
		Calculator calc = new Calculator();
		calc.calculateMap(map, count, ignoreOriginCenter);
		
		// Formatiere das Ergebniss
		Formatter output = new Formatter();
		output.export(map, file);
		
		// Öffne die Ergebnissdatei in gnuplot
		gui.interpretFileInGnuplot(file);
	}
}
