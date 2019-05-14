package Controller.Interfaces;

import java.io.IOException;

import Model.Karte;

public interface FormatterInterface {

	public void export(Karte karte, String filename) throws IOException;
}
