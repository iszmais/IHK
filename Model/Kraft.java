package Model;

/**
 * Stellt alle benötigten Werte einer Kraft bereit.
 * 
 * @author iszmais
 *
 */
public class Kraft {
	
	/**
	 * Referenz auf den Staat auf den die Kraft sich bezieht.
	 */
	private Staat staat;
	
	/**
	 * Kraft in Form eines 2-dimensionalen Richtungsvektors.
	 */
	private double[] vektor;
	
	/**
	 * Erstellt eine neutrale Kraft für einen Staat
	 * 
	 * @param staat
	 */
	public Kraft(Staat staat) {
		this.staat = staat;
		double[] vektor = {0,0};
		this.vektor = vektor;
	}
	
	/**
	 * Erstellt anhand einer x- und einer y-Richtung eine Kraft für einen Staat
	 * 
	 * @param staat
	 * @param x Kraft in X-Richtung
	 * @param y Kraft in y-Richtung
	 */
	public Kraft(Staat staat, double x, double y) {
		this.staat = staat;
		double[] vektor = {x,y};
		this.vektor = vektor;
	}
	
	/**
	 * Gibt die Referenz des Staats der Kraft zurück
	 * 
	 * @return Die Referenz des Staats der Kraft
	 */
	public Staat getStaat() {
		return this.staat;
	}
	
	/**
	 * Gibt die Kraft in Form eines 2-dimensionalen Vektors zurück
	 * 
	 * @return Vektor der Kraft
	 */
	public double[] getVektor() {
		return this.vektor;
	}
	
	/**
	 * Setzt Vektor einer Kraft
	 * 
	 * @param vektor Neuer 2-dimensionaler Vektor der Kraft
	 */
	public void setVektor(double[] vektor) {
		this.vektor = vektor;
	}
}
