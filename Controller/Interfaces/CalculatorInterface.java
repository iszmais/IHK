package Controller.Interfaces;

import Model.Karte;

public interface CalculatorInterface {
	
	/**
	 * Bewertet eine Iteration.
	 * Gegebenenfalls wird zusätzlich die Abweichung der Mittelpunkte der Staatenkreis zum Ursprung
	 * (erste Iteration der Karte) mitgewichtet.
	 * 
	 * @param karte Karte
	 * @param n Index der Iteration die bewertet werden soll
	 * @param ignoreOriginCenter Flag die besagt ob bei der Bewertung einer Iteration die Veränderung der Lage des 
	 * 		  Staatenmittelpunktes zum Ursprung gewichtet werden soll
	 * @return Bewertungszahl
	 */
	public double rateIteration(Karte karte,int n, boolean ignoreOriginCenter);

}
