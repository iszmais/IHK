package Model;

/**
 * Stellt alle benötigten Werte eines Staats bereit.
 * 
 * @author iszmais
 *
 */
public class Staat {
	
	/**
	 * ID des Staats
	 */
	private int id;
	
	/**
	 * Länderkürzel des Staats 
	 */
	private String kürzel;
	
	/**
	 * Radius des Kannwertkreises des Staats
	 */
	private double radius;
	
	/**
	 * Koordinaten des Mittelpunktes des Kennwertkreises des Staats in Form eines 2-dimensionalen Arrays
	 */
	private double[] koordinaten;
	
	/**
	 * Erstellt einen Staat anhand von einer ID, einem Länderkürzel, eines Radius und Koordinaten
	 * 
	 * @param id ID des Staats
	 * @param kürzel Länderkürzel des Staats
	 * @param radius Radius des Kannwertkreises des Staats
	 * @param koordinaten Koordinaten des Mittelpunktes des Kennwertkreises des Staats in Form eines 2-dimensionalen Arrays
	 */
	public Staat(int id, String kürzel, double radius, double[] koordinaten) {
		this.id = id;
		this.kürzel = kürzel;
		this.radius = radius;
		this.koordinaten = koordinaten;
	}
	
	/**
	 * Gibt die ID des Staats zurück
	 * 
	 * @return ID des Staats
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Gibt das Länderkürzel des Staats zurück
	 * 
	 * @return Länderkürzel des Staats
	 */
	public String getKürzel() {
		return this.kürzel;
	}
	
	/**
	 * Gibt den Radius des Staats zurück
	 * 
	 * @return Radius des Staats
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Setz den Radius des Staats
	 * 
	 * @param Neuer Radius des Staats
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/**
	 * Gibt die Koordinaten des Staats zurück
	 * 
	 * @return Koordinaten des Staats in Form von einem 2-dimensionalen Array
	 */
	public double[]getKoordinaten(){
		return this.koordinaten;
	}
	
	/**
	 * Setzt die Koordinaten des Staats
	 * 
	 * @param Neue Koordinaten des Staats in Form von einem 2-dimensionalen Array
	 */
	public void setKoordinaten(double[] koordinaten) {
		this.koordinaten = koordinaten;
	}
}
