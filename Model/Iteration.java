package Model;

/**
 * Stellt alle benötigten Werte einer Iteration bereit.
 * 
 * @author iszmais
 *
 */
public class Iteration {
	
	/**
	 * Zähler der Iteration
	 */
	private int zähler;
	
	/**
	 * Statische Liste der Staaten der Iteration
	 */
	private Staat[] staaten;
	
	/**
	 * Statische Liste der Nachbarschaften der Iteration
	 */
	private Nachbarschaft[] nachbarschaften;
	
	/**
	 * Erstellt eine Ursprungsiteration anhand einer Liste von Staaten und einer Liste von Nachbarschaften
	 * 
	 * @param staaten Statische Liste der Staaten der Iteration
	 * @param nachbarschaften Statische Liste der Nachbarschaften der Iteration
	 */
	public Iteration(Staat[] staaten, Nachbarschaft[] nachbarschaften) {
		this.zähler = 1;
		this.staaten = staaten;
		this.nachbarschaften = nachbarschaften;
	}

	/**
	 * Erstellt eine Iteration anhand eines Zählers, einer Liste von Staaten und einer Liste von Nachbarschaften
	 * 
	 * @param zähler Zähler der Iteration
	 * @param staaten Statische Liste der Staaten der Iteration
	 * @param nachbarschaften Statische Liste der Nachbarschaften der Iteration
	 */
	public Iteration(int zähler, Staat[] staaten, Nachbarschaft[] nachbarschaften) {
		this.zähler = zähler;
		this.staaten = staaten;
		this.nachbarschaften = nachbarschaften;
	}
	
	/**
	 * Klont eine Iteration indem sie eine Liste neuer Staaten erstellt und eine Liste neuer Nachabrschaften auf diese
	 * Staatenliste referenziert und den Zähler inkrementiert
	 * 
	 * @return Echter Klon der aufrufenden Iteration (Folgeiteration)
	 */
	public Iteration clone() {
		Staat[] staaten = new Staat[this.getStaaten().length];
		Nachbarschaft[] nachbarschaften = new Nachbarschaft[this.getNachbarschaften().length];
		
		//Erstelle neue Staatenliste
		for(int i = 0; i < this.getStaaten().length; i++) {
			Staat originS = this.getStaaten()[i];
			double[] koord = {originS.getKoordinaten()[0],originS.getKoordinaten()[1]};
			Staat copyS = new Staat(originS.getId(),originS.getKürzel(),originS.getRadius(),koord);
			staaten[i] = copyS;
		}
		
		// Referenziere neue Nachabrschaften auf die neue Staatenliste
		for(int i = 0; i < this.getNachbarschaften().length; i++) {
			Nachbarschaft originN = this.getNachbarschaften()[i];
			Nachbarschaft copyN = new Nachbarschaft(staaten[originN.getStaat1().getId()], staaten[originN.getStaat2().getId()]);
			nachbarschaften[i] = copyN;
		}
		
		return new Iteration(this.zähler + 1, staaten, nachbarschaften);
	}
	
	/**
	 * Gibt den Zähler der Iteration zurück
	 * 
	 * @return Zähler der Iteration
	 */
	public int getZähler(){
		return this.zähler;
	}
	
	/**
	 * Setzt den Zähler der Iteration
	 * 
	 * @param Neuer Zähler der Iteration
	 */
	public void setZähler(int zähler) {
		this.zähler = zähler;
	}
	
	/**
	 * Gibt die Liste der Staaten der Iteration zurück
	 * 
	 * @return Statische Liste der Staaten der Iteration
	 */
	public Staat[] getStaaten() {
		return this.staaten;
	}
	
	/**
	 * Gibt die Liste der Nachbarschaften der Iteration zurück
	 * 
	 * @return Statische Liste der Nachbarschaften der Iteration
	 */
	public Nachbarschaft[] getNachbarschaften() {
		return this.nachbarschaften;
	}
}
