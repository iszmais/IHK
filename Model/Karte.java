package Model;

import java.util.ArrayList;

public class Karte {
	
	private String name;
	private ArrayList<Iteration> iterationen;
	
	public Karte(String name) {
		this.name = name;
		this.iterationen = new ArrayList<Iteration>();
	}
	
	public Karte(String name, ArrayList<Iteration> iterationen) {
		this.name = name;
		this.iterationen = iterationen;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Iteration> getIterationen(){
		return this.iterationen;
	}
	
	public void setIterationen(ArrayList<Iteration> iterationen) {
		this.iterationen = iterationen;
	}
	
	public void addIteration(Iteration iteration) {
		this.iterationen.add(iteration);
	}
}
