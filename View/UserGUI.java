package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Stellt alle benötigten Funktionen zur Interaktion mit dem Nutzer bereit.
 * 
 * @author iszmais
 *
 */
public class UserGUI {
	
	/**
	 * Fordert den Nutzer zur Eingabe des absoluten Dateipfades der Eingabedatei auf und gibt die folgende Eingabe zurück.
	 * 
	 * @return Texteingabe: der absolute Pfad zur Eingabedatei
	 * @throws IOException
	 */
	public String getFileName() throws IOException{
		System.out.println("Enter file name: ");
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		String file = reader.readLine();
		return file;
	}
	
	/**
	 * Fordert den Nutzer zur Eingabe der Anzahl der zu berechnend Iterationen auf und gibt die folgende Eingabe formattiert zurück.
	 * 
	 * @return Ganzzahleingabe: die Zahl der zu berechnenden Iterationen (oder < 0)
	 * @throws IOException
	 */
	public int getIterationCount() throws NumberFormatException, IOException {
		System.out.println("Enter iteration count (0 for intelligent iteration): ");
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		int count = Integer.parseInt(reader.readLine());
		return count;
	}
	
	/**
	 * Fragt den Nutzer ob die Abweichung der Mittelpunkte zum Urprung bei der Bewertung ignoriert werden woll und gibt die folgende
	 * Eingabe formattiert zurück.
	 * 
	 * @return Check: falsch bei Eingabe "no", ansonsten wahr
	 * @throws IOException
	 */
	public boolean getIgnoreOriginCenter() throws IOException {
		System.out.println("Should the distance to the origin Center be ignored for the intelligent rating? (type 'no' if not)");
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		boolean ignore = true;
		if(reader.readLine().equals("no")) {
			ignore = false;
		}		
		return ignore;
	}
	
	/**
	 * Öffnet einen übergebenen Dateipfad über die Kommandozeile in gnuplot und gibt den Kommandozeilenbefehl aus
	 * 
	 * @param file der absolute Pfad der Ausgabedatei
	 * @throws IOException
	 */
	public void interpretFileInGnuplot(String file) throws IOException {
		Runtime gnuplot = Runtime.getRuntime();
		System.out.print("gnuplot "+file+"Render.txt -p");
		gnuplot.exec("gnuplot "+file+"Render.txt -p");
	}
}
