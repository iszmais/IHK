package Model;

public class Iteration {
	
	private int zähler;
	private Staat[] staaten;
	private Nachbarschaft[] nachbarschaften;
	
	public Iteration(Staat[] staaten, Nachbarschaft[] nachbarschaften) {
		this.zähler = 1;
		this.staaten = staaten;
		this.nachbarschaften = nachbarschaften;
	}


	public Iteration(int zähler, Staat[] staaten, Nachbarschaft[] nachbarschaften) {
		this.zähler = zähler;
		this.staaten = staaten;
		this.nachbarschaften = nachbarschaften;
	}
	
	public int getZähler(){
		return this.zähler;
	}
	
	public void setZähler(int zähler) {
		this.zähler = zähler;
	}
	
	public Staat[] getStaaten() {
		return this.staaten;
	}
	
	public Nachbarschaft[] getNachbarschaften() {
		return this.nachbarschaften;
	}
}
