package Model;

/**
 * Stellt alle benötigten Werte einer Nachbarschaft bereit.
 * 
 * @author iszmais
 *
 */
public class Nachbarschaft {
	
	/**
	 * Refernz auf den ersten Staat der Nachbarschaft
	 */
	private Staat staat1;
	
	/**
	 * Refernz auf den zweiten Staat der Nachbarschaft
	 */
	private Staat staat2;
	
	/**
	 * Erstellt eine Nachbarschaft für 2 Staaten
	 * 
	 * @param staat1 erster Staat
	 * @param staat2 zweiter Staat
	 */
	public Nachbarschaft(Staat staat1, Staat staat2) {
		this.staat1 = staat1;
		this.staat2 = staat2;
	}
	
	/**
	 * Gibt die Referenz auf den ersten Staat der Nachbarschaft zurück
	 * 
	 * @return Die Referenz des ersten Staats der Nachbarschaft
	 */
	public Staat getStaat1() {
		return this.staat1;
	}
	
	/**
	 * Gibt die Referenz auf den zweiten Staat der Nachbarschaft zurück
	 * 
	 * @return Die Referenz des zweiten Staats der Nachbarschaft
	 */
	public Staat getStaat2() {
		return this.staat2;
	}
}
