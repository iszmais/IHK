package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Controller.Interfaces.FormatterInterface;
import Model.Iteration;
import Model.Karte;
import Model.Staat;

/**
 * Stellt alle benötigten Funktionen zum Formatieren eine Objektes in eine gnuplot-konforme Textdatei bereit.
 * 
 * @author iszmais
 *
 */
public class Formatter implements FormatterInterface{

	/**
	 * Berechnet für die letzte Iteration der Karte den minimalen, quadratischen Bereich in einem 2-dimensionalen Koordinatensystem.
	 * der benötigt wird um alle Staatenkreise vollständig darszustellen.
	 * 
	 * @param karte Berechenete Karte
	 * 
	 * @return Rechteck in Form von 2 zusammengefügten Koordinaten min und max ([xmin,ymin,xmax,ymax])
	 */
	public double[] getMinSpace(Karte karte) {
		
		Iteration iteration = karte.getIterationen().get(karte.getIterationen().size() - 1);
		// Initialisiere das Rechteck mit den schlechtesten Daten
		double[] window = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
		
		// Iteriere über alle Flächen der Staaten und speicher die Extremwerte
		for(int i = 0; i < iteration.getStaaten().length; i++) {
			double x = iteration.getStaaten()[i].getKoordinaten()[0];
			double y = iteration.getStaaten()[i].getKoordinaten()[1];
			double rad = iteration.getStaaten()[i].getRadius();
			if(x - rad < window[0]) {
				window[0] = x - rad;
			}
			if(y - rad < window[1]) {
				window[1] = y - rad;
			}
			if(x + rad > window[2]) {
				window[2] = x + rad;
			}
			if(y + rad > window[3]) {
				window[3] = y + rad;
			}
		}
		
		// Erzeuge ein Quadrat indem die geringere von Höhe und Länge positiv erweitert wird
		if((window[2] - window[0]) > (window[3] - window[1])) {
			window[3] = window[1] + (window[2] - window[0]);
		}else {
			window[2] = window[0] + (window[3] - window[1]);
		}
		return window;
	}


	/**
	 * Exportiert die Karte in eine gnuplot-konforme Textdatei.
	 * 
	 * @param karte Berechenete Karte
	 * @param filename absoluter Pfad der Eingabedatei
	 * 
	 * @throws IOException
	 */
	public void export(Karte karte, String filename) throws IOException {
		
		String filepath = filename+"Render.txt";
		double[] window = this.getMinSpace(karte);
		Iteration iteration = karte.getIterationen().get(karte.getIterationen().size() - 1);
		
		// Befülle einen String mit allen für gnuplot notwendigen Daten
		String fileContent = "reset\n";
		fileContent += "set xrange ["+window[0]+":"+window[2]+"]\n";
		fileContent += "set yrange ["+window[1]+":"+window[3]+"]\n";
		fileContent += "set size ratio 1.0\n";
		fileContent += "set title \""+karte.getName()+", "+iteration.getZähler()+"\"\n";
		fileContent += "unset xtics\n";
		fileContent += "unset ytics\n";
		fileContent += "$data << EOD\n";
		for(int i = 0; i < iteration.getStaaten().length; i++) {
			Staat staat = iteration.getStaaten()[i];
			fileContent += staat.getKoordinaten()[0]+" "+staat.getKoordinaten()[1]+" "+staat.getRadius()+" "+staat.getKürzel()+" "+staat.getId()+"\n";
		}
		fileContent += "EOD\n";
		fileContent += "plot \\\n";
		fileContent += "'$data' using 1:2:3:5 with circles lc var notitle, \\\n";
		fileContent += "'$data' using 1:2:4:5 with labels font \"arial,9\" tc var notitle\\\n";
		
		// Erstelle eine neue Datei und befülle sie mit der String
		File file = new File(filepath);
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write(fileContent);
		writer.close();
	}
}
