package Controller;

import java.util.ArrayList;

import Controller.Interfaces.CalculatorInterface;
import Model.Iteration;
import Model.Karte;
import Model.Kraft;
import Model.Nachbarschaft;
import Model.Staat;

/**
 * Stellt alle benötigten Funktionen Berchnen und bewerten neuer Iterationen bereit.
 * 
 * @author iszmais
 *
 */
public class Calculator implements CalculatorInterface{
	
	/**
	 * Führt eine Voriteration aus und berechnet dann entweder eine feste Anzahl an Iterationen der Karte oder so lange
	 * neue Iterationen bis diese sich nicht mehr verbessern.
	 * 
	 * @param map Zu berechnende Karte
	 * @param count Anzehl der auszuführenden Iterationen (wenn > 0)
	 * @param ignoreOriginCenter Flag die besagt ob bei der Bewertung einer Iteration die Veränderung der Lage des 
	 * 		  Staatenmittelpunktes zum Ursprung gewichtet werden soll
	 */
	public void calculateMap(Karte map, int count, boolean ignoreOriginCenter) {
		// Voriteration
		this.preventIntersections(map);
		
		// Höre auf zu Iterieren wenn die Bewertung der letzten Iteration schlechter ist als die der vorletzten
		if(count <= 0) {
			double last = Double.MAX_VALUE;
			double current = Double.MAX_VALUE / 2;
			// Verlgeiche die Bewertung der letzten Iteration mit der der ersten
			while(last > current) {
				map.addIteration(this.nextIteration(map.getIterationen().get(map.getIterationen().size()-1)));
				last = current;
				//Bewertung
				current = this.rateIteration(map,map.getIterationen().size()-1, ignoreOriginCenter);
			}
			// Lösche die letzte Iteration
			map.removeLastIteration();
		// Berechne count Iterationen
		}else {
			for(int i = 0; i < count; i++) {
				map.addIteration(this.nextIteration(map.getIterationen().get(map.getIterationen().size()-1)));
			}
		}
	}
	
	/**
	 * Berechnet aus einer Iteration die nächste Iteration
	 * 
	 * @param iteration Lokale Ursprungsiteration
	 * 
	 * @return nächste / neu berechnete Iteration
	 */
	public Iteration nextIteration(Iteration iteration) {

		ArrayList<Kraft> kräfte = new ArrayList<Kraft>();
		// Kopiere die Ursprungsiteration (Echte Kopie ohne Aliase)
		Iteration next = iteration.clone();
		Staat[] staaten = next.getStaaten();
		Nachbarschaft[] nachbarschaften = next.getNachbarschaften();

		//Itereriere über jede Zweierkombination von Elementen der Liste Staaten ohne Wiederholung (Staaten!)
		for(int i = 0; i < staaten.length; i++) {
			for(int j = i; j < staaten.length; j++) {
				// überprüfe ob beide Staaten eine Nachbarschaftsbeziehung haben
				boolean isNachbar = false;
				for(int k = 0; k < nachbarschaften.length; k++) {
					if(
							(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()) &&
							staaten[j].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel())) ||
							(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel()) &&
							staaten[j].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()))
					) {
						isNachbar = true;
					}
				}
				// Berechne die Kräfte
				kräfte.addAll(this.getKraft(staaten[i],staaten[j],isNachbar));
			}
		}
		
		// Wende anteilig alle Kräfte an
		this.useKräfte(kräfte);
		return next;
	}
	
	/**
	 * Berechnet die Vektoren, die nötig wären um die Überschneidung der beiden Staatenkreise zu elimieren.
	 * Berechnet gegebenenfalls die Vektoren, die nötig wären um den Abstand zwischen den beiden Staatenkreisen zu 
	 * elminieren, sofern die beiden Staaten Nachbarn sind.
	 * 
	 * @param staat1 erster Staat
	 * @param staat2 zweiter Staat
	 * @param isNachbar staat1 und staat2 haben eine Nachbarschaftsbeziehung
	 * 
	 * @return Liste mit beiden benötigten Kräften für die Staaten
	 */
	public ArrayList<Kraft> getKraft(Staat staat1, Staat staat2, boolean isNachbar) {
		
		// Berechnung des Abstandes (negativ = Überschneidung)
		double radsum = staat1.getRadius() + staat2.getRadius();
		double x = staat1.getKoordinaten()[0] - staat2.getKoordinaten()[0];
		double y = staat1.getKoordinaten()[1] - staat2.getKoordinaten()[1];
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double move = distance - radsum;
		
		ArrayList<Kraft> result = new ArrayList<Kraft>();
		
		// Wenn der Abstand negativ ist oder die Staaten Nachbarn sind erhalte beide Staaten 
		// die Hälfte der benötigten Ausgleichskraft
		if(isNachbar || (move < 0 && distance != 0)) {
			Kraft kraft1 = new Kraft(staat1,-(x * move)/(2 * distance),-(y * move)/(2 * distance));
			result.add(kraft1);
			Kraft kraft2 = new Kraft(staat2,(x * move)/(2 * distance),(y * move)/(2 * distance));
			result.add(kraft2);
		}

		return result;
	}
	
	/**
	 * Wendet alle Kräft zu einem Anteil auf den zugehörigen Staat an.
	 * Der Anteil entspricht hierbei der Kraft geteilt durch die Summa aller Kräfte die auf diesen Staat wirken.
	 * 
	 * @param kräfte Liste aller Berechneten Kräfte dieser Iteration
	 */
	public void useKräfte(ArrayList<Kraft> kräfte) {

		for(int i = 0; i < kräfte.size(); i++) {
			// Berechne die Summe aller Kräfte die auf den Staat wirken
			int count = 0;
			for(int j = 0; j < kräfte.size(); j++) {
				if(kräfte.get(j).getStaat().getKürzel().equals(kräfte.get(i).getStaat().getKürzel())) {
					count++;
				}
			}
			// Wende die Kraft anteilig an
			double[] coord = kräfte.get(i).getStaat().getKoordinaten();
			double[] move = kräfte.get(i).getVektor();
			coord[0] = coord[0] + (move[0]/count);
			coord[1] = coord[1] + (move[1]/count);
			kräfte.get(i).getStaat().setKoordinaten(coord);
		}
	}
	

	/**
	 * Bewertet eine Iteration anhand der Summe der Überschneidungen aller Kreise und der Summe der Abstände
	 * aller Nachbarn.
	 * Gegebenenfalls wird zusätzlich die Abweichung der Mittelpunkte der Staatenkreis zum Ursprung
	 * (erste Iteration der Karte) in Summe mitgewichtet.
	 * 
	 * @param karte Berechnete Karte
	 * @param n Index der Iteration die bewertet werden soll
	 * @param ignoreOriginCenter Flag die besagt ob bei der Bewertung einer Iteration die Veränderung der Lage des 
	 * 		  Staatenmittelpunktes zum Ursprung gewichtet werden soll
	 * @return Bewertungszahl
	 */
	public double rateIteration(Karte karte, int n, boolean ignoreOriginCenter) {
		
		double sum = 0;
		Iteration first = karte.getIterationen().get(0);
		Iteration target = karte.getIterationen().get(n);
		ArrayList<Kraft> kräfte = new ArrayList<Kraft>();

		//Itereriere über jede Zweierkombination von Elementen der Liste Staaten ohne Wiederholung (Staaten!)
		for(int i = 0; i < target.getStaaten().length; i++) {
			for(int j = i; j < target.getStaaten().length; j++) {
				// überprüfe ob beide Staaten eine Nachbarschaftsbeziehung haben
				boolean isNachbar = false;
				for(int k = 0; k < target.getNachbarschaften().length; k++) {
					if(
							(target.getStaaten()[i].getKürzel().equals(target.getNachbarschaften()[k].getStaat1().getKürzel()) &&
									target.getStaaten()[j].getKürzel().equals(target.getNachbarschaften()[k].getStaat2().getKürzel())) ||
							(target.getStaaten()[i].getKürzel().equals(target.getNachbarschaften()[k].getStaat2().getKürzel()) &&
									target.getStaaten()[j].getKürzel().equals(target.getNachbarschaften()[k].getStaat1().getKürzel()))
					) {
						isNachbar = true;
					}
				}
				// Berechne die Kräfte
				kräfte.addAll(this.getKraft(target.getStaaten()[i],target.getStaaten()[j],isNachbar));
			}
		}
		
		// Addiere die Summe aller Kräfte zur Bewertungszahl hinzu
		for(int i = 0; i < kräfte.size(); i++) {
			sum = sum + Math.sqrt(Math.pow(kräfte.get(i).getVektor()[0], 2) + Math.pow(kräfte.get(i).getVektor()[1], 2));
		}
		
		// Wenn das Flag gesetzt ist, addiere die Summe der Abweichungen der Mittelpunkt zum Ursprung zur Bewertungszahl hinzu
		if(!ignoreOriginCenter) {
			for(int i = 0; i < first.getStaaten().length; i++) {
				double[] root = first.getStaaten()[i].getKoordinaten();
				double[] now = target.getStaaten()[i].getKoordinaten();
				sum = sum + Math.sqrt(Math.pow(root[0] - now[0], 2) + Math.pow(root[1] - now[1], 2));
			}
		}
		return sum;
	}
	
	/**
	 * Manipuliert die Ursprungsiteration der Karte durch  gleichmäßige Reduzierung der Radien so lange bis die Überschneidungen
	 * der Kreise nicht mehr die Abstände der Nachbarn überwiegen
	 * 
	 * @param karte Zu Berechnende Karte
	 */
	public void preventIntersections(Karte karte) {
		Staat[] staaten = karte.getIterationen().get(0).getStaaten();
		Nachbarschaft[] nachbarschaften = karte.getIterationen().get(0).getNachbarschaften();
		double intersect = -1;
		
		// Iteriere so lange bis die Summe der Abstände der Nachbarn minus die Summe der Überschneidungen positiv ist
		while(intersect < 0) {
			intersect = 0;
			for(int i = 0; i < staaten.length; i++) {
				for(int j = i; j < staaten.length; j++) {
					// Berechnung des Abstandes (negativ = Überschneidung)
					double radsum = staaten[i].getRadius() + staaten[j].getRadius();
					double x = staaten[i].getKoordinaten()[0] - staaten[j].getKoordinaten()[0];
					double y = staaten[i].getKoordinaten()[1] - staaten[j].getKoordinaten()[1];
					double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
					double move = distance - radsum;
					// überprüfe ob beide Staaten eine Nachbarschaftsbeziehung haben
					boolean isNachbar = false;
					for(int k = 0; k < nachbarschaften.length; k++) {
						if(
								(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()) &&
										staaten[j].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel())) ||
								(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel()) &&
										staaten[j].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()))
						) {
							isNachbar = true;
						}
					}
					// Wenn Überschneidung oder Nachbarn addiere move
					if((move < 0) || isNachbar) {
						intersect = intersect + move;
					}
				}
			}
			// Wenn die Überschneidungen überwiegen reduziere die Kreise um 20%
			if(intersect < 0) {
				for(int i = 0; i < staaten.length; i++) {
					staaten[i].setRadius(staaten[i].getRadius() * 0.8);
				}
			}
		}
	}
}
