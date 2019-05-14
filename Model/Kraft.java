package Model;

public class Kraft {
	
	private Staat staat;
	private double[] vektor;
	
	public Kraft(Staat staat) {
		this.staat = staat;
		double[] vektor = {0,0};
		this.vektor = vektor;
	}
	

	
	public Kraft(Staat staat, double x, double y) {
		this.staat = staat;
		double[] vektor = {x,y};
		this.vektor = vektor;
	}
	
	public Staat getStaat() {
		return this.staat;
	}
	
	public double[] getVektor() {
		return this.vektor;
	}
	
	public void setVektor(double[] vektor) {
		this.vektor = vektor;
	}
}
