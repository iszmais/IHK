package Model;

public class Nachbarschaft {
	
	private Staat staat1;
	private Staat staat2;
	
	public Nachbarschaft(Staat staat1, Staat staat2) {
		this.staat1 = staat1;
		this.staat2 = staat2;
	}
	
	public Staat getStaat1() {
		return this.staat1;
	}
	
	public Staat getStaat2() {
		return this.staat2;
	}
}
