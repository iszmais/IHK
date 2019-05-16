package Model;

import java.util.ArrayList;

/**
 * Stellt alle benötigten Werte einer Karte bereit.
 * 
 * @author iszmais
 *
 */
public class Karte {
	
	/**
	 * Bezeichner des Kenntwertes der Karte
	 */
	private String name;
	/**
	 * Liste alles berechneten Iterationen und der Ursprungsiteration der Karte 
	 */
	private ArrayList<Iteration> iterationen;
	
	/**
	 * Erstellt eine Karte ohne Iterationen anhand eines Kennwert-Namens
	 * 
	 * @param name Bezeichner des Kennwerts der Karte
	 */
	public Karte(String name) {
		this.name = name;
		this.iterationen = new ArrayList<Iteration>();
	}
	
	/**
	 * Erstellt eine Karte anhand von Iterationen und eines Kennwert-Namens
	 * 
	 * @param name Bezeichner des Kennwerts der Karte
	 * @param iteationen Dynamische Liste von Iterationen der Karte
	 */
	public Karte(String name, ArrayList<Iteration> iterationen) {
		this.name = name;
		this.iterationen = iterationen;
	}
	
	/**
	 * Gibt den Namen der Karte zurück
	 * 
	 * @return Bezeichner des Kenntwertes der Karte
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gibt die Liste der Iterationen der Karte zurück
	 * 
	 * @return Dynamische Liste der Iterationen der Karte
	 */
	public ArrayList<Iteration> getIterationen(){
		return this.iterationen;
	}
	
	/**
	 * Setzt die Liste der Iterationen der Karte
	 * 
	 * @param iterationen Neue Dynamische Liste der Iterationen der Karte
	 */
	public void setIterationen(ArrayList<Iteration> iterationen) {
		this.iterationen = iterationen;
	}
	
	/**
	 * Fügt der Liste der Iterationen der Karte ein Element hinzu (am Ende)
	 * 
	 * @param iteration Neue letzte Iteration der Karte
	 */
	public void addIteration(Iteration iteration) {
		this.iterationen.add(iteration);
	}
	
	/**
	 * Entfernt die letzte Iteration der Liste der Iterationen der Karte
	 */
	public void removeLastIteration() {
		this.iterationen.remove(this.iterationen.get(this.iterationen.size() - 1));
	}
}
