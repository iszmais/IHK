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
		System.out.println("Enter file name: ");
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		file = reader.readLine();
		InputReader input = new InputReader();
		Karte map = input.readFile("src/assets/"+file);
		
		int count = 0;
		System.out.println("Enter iteration count (0 for intelligent iteration): ");
		count = Integer.parseInt(reader.readLine());
		boolean ignoreOriginCenter = true;
		if(count == 0) {
			System.out.println("Should the distance to the origin Center be ignored for the intelligent rating? (type 'no' if not)");
			if(reader.readLine().equals("no")) {
				ignoreOriginCenter = false;
			}
		}
		
		Calculator calc = new Calculator();
		calc.preventIntersections(map);
		if(count == 0) {
			double last = Double.MAX_VALUE;
			double current = Double.MAX_VALUE / 2;
			while(last > current) {
				map.addIteration(calc.nextIteration(map.getIterationen().get(map.getIterationen().size()-1)));
				last = current;
				current = calc.rateIteration(map,map.getIterationen().size()-1, ignoreOriginCenter);
			}
		}else {
			for(int i = 0; i < count; i++) {
				map.addIteration(calc.nextIteration(map.getIterationen().get(map.getIterationen().size()-1)));
			}
		}
		
		Formatter output = new Formatter();
		output.export(map, file);
		
		Runtime gnuplot = Runtime.getRuntime();
		gnuplot.exec("gnuplot src/assets/"+file+"Render.txt -p");
	}
}
