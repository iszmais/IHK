import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Controller.Calculator;
import Controller.Formatter;
import Controller.InputReader;
import Model.Karte;

public class main {

	public static void main(String[] args) throws IOException {
		
		String file = "";

		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		file = reader.readLine();
		
		InputReader input = new InputReader();
		Karte map = input.readFile("src/assets/"+file);
		Calculator calc = new Calculator();
		for(int i = 0; i < 1000; i++) {
			map.addIteration(calc.nextIteration(map.getIterationen().get(map.getIterationen().size()-1)));
		}

		Formatter output = new Formatter();
		output.export(map, file);
		
		Runtime gnuplot = Runtime.getRuntime();
		
		gnuplot.exec("gnuplot src/assets/"+file+"Render.txt -p");
	}
}
