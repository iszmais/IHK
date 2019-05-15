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
	
	public Iteration clone() {
		Staat[] staaten = new Staat[this.getStaaten().length];
		Nachbarschaft[] nachbarschaften = new Nachbarschaft[this.getNachbarschaften().length];
		
		for(int i = 0; i < this.getStaaten().length; i++) {
			Staat originS = this.getStaaten()[i];
			double[] koord = {originS.getKoordinaten()[0],originS.getKoordinaten()[1]};
			Staat copyS = new Staat(originS.getId(),originS.getKürzel(),originS.getRadius(),koord);
			staaten[i] = copyS;
		}
		
		for(int i = 0; i < this.getNachbarschaften().length; i++) {
			Nachbarschaft originN = this.getNachbarschaften()[i];
			Nachbarschaft copyN = new Nachbarschaft(staaten[originN.getStaat1().getId()], staaten[originN.getStaat2().getId()]);
			nachbarschaften[i] = copyN;
		}
		
		return new Iteration(this.zähler + 1, staaten, nachbarschaften);
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
