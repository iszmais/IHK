package Model;

public class Staat {
	
	private int id;
	private String kürzel;
	private double radius;
	private double[] koordinaten;
	
	public Staat(int id, String kürzel, double radius, double[] koordinaten) {
		this.id = id;
		this.kürzel = kürzel;
		this.radius = radius;
		this.koordinaten = koordinaten;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getKürzel() {
		return this.kürzel;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double[]getKoordinaten(){
		return this.koordinaten;
	}
	
	public void setKoordinaten(double[] koordinaten) {
		this.koordinaten = koordinaten;
	}
}
