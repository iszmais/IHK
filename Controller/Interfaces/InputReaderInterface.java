package Controller.Interfaces;

import java.io.IOException;

import Model.Karte;

public interface InputReaderInterface {
	
	public Karte readFile(String filepath) throws IOException;

}
