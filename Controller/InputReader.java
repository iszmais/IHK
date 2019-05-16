package Controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

import Controller.Interfaces.InputReaderInterface;
import Model.Iteration;
import Model.Karte;
import Model.Nachbarschaft;
import Model.Staat;

/**
 * Stellt alle benötigten Funktionen zum Einlesen und Interpretieren innerhalb der Datenstruktur bereit.
 * 
 * @author iszmais
 *
 */
public class InputReader implements InputReaderInterface{
	
	/**
	 * Liest einen Eingabepfad ein und erstellt anhand der Eingabedatei ein Karte mit einer ersten Iteration.
	 * 
	 * @param filepath absoluter Pfad der Eingabedatei
	 * 
	 * @return Karte für die Berechnung
	 * @throws IOException
	 */
	public Karte readFile(String filepath) throws IOException {
		
		File file = new File(filepath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String name = "";
		ArrayList<Staat> staaten = new ArrayList<Staat>();
		ArrayList<Nachbarschaft> nachbarschaften = new ArrayList<Nachbarschaft>();
		String st;
		int i = 0;
		
		// Itereriere über alle Zeilen der Eingabedatei
		while ((st = br.readLine()) != null) {
			// Ingoriere Kommentarzeilen
			  if(st.charAt(0) != '#') {
				  // Die erste gefundene Nicht-Kommentarzeile enthält der Namen des Bezeicheners
				  if(name.equals("")) {
					  name = st;
				  }else {
					  // Zeilen die mit einer Zahl endet enthalten Informationen zu Staaten
					  if(Character.isDigit(st.charAt(st.length() - 1))) {
						  String[] parts = st.split("\\s+");
						  String kürzel = parts[0];
						  double rad = Math.sqrt(Double.parseDouble(parts[1])/Math.PI);
						  double[] coord = {Double.parseDouble(parts[2]),Double.parseDouble(parts[3])};
						  staaten.add(new Staat(i, kürzel, rad, coord));
						  i++;
					  // Alle anderen Zeilen enthalten Beziehungen
					  // (funktioniert das alle Staaten vor allen Beziehungen stehen)
					  }else {
						  String[] parts = st.split("\\s+");
						  Staat staat1 = null;
						  for(int j = 0; j < staaten.size(); j++) {
							  if(parts[0].equals(staaten.get(j).getKürzel()+":")) {
								  staat1 = staaten.get(j);
								  break;
							  }
						  }
						  if(staat1 != null) {
							  for(int j = 1; j < parts.length; j++) {
								  for(int k = 0; k < staaten.size(); k++) {
									  if(parts[j].equals(staaten.get(k).getKürzel())) {
										  Staat staat2 = staaten.get(k);
										  Nachbarschaft nachbarschaft = new Nachbarschaft(staat1, staat2);
										  nachbarschaften.add(nachbarschaft);
										  break;
									  }
								  }
							  }
						  }
					  }
				  }
				  
			  }
		}
		br.close();

		//Erstelle die erste Iteration und gibt die Karte zurück
		Iteration firstIteration = new Iteration(staaten.toArray(new Staat[staaten.size()]),nachbarschaften.toArray(new Nachbarschaft[nachbarschaften.size()]));
		Karte karte = new Karte(name);
		karte.addIteration(firstIteration);
		return karte;
	}
}
