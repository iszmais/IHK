package Controller;

import java.util.ArrayList;

import Controller.Interfaces.CalculatorInterface;
import Model.Iteration;
import Model.Karte;
import Model.Kraft;
import Model.Nachbarschaft;
import Model.Staat;

public class Calculator implements CalculatorInterface{
	
	public Iteration nextIteration(Iteration iteration) {

		ArrayList<Kraft> kräfte = new ArrayList<Kraft>();
		Staat[] staaten = iteration.getStaaten().clone();

		Nachbarschaft[] nachbarschaften = new Nachbarschaft[iteration.getNachbarschaften().length];
		for(int i = 0; i < iteration.getNachbarschaften().length; i++) {
			Nachbarschaft old = iteration.getNachbarschaften()[i];
			Nachbarschaft clone = new Nachbarschaft(staaten[old.getStaat1().getId()],staaten[old.getStaat2().getId()]);
			nachbarschaften[i] = clone;
			//kräfte.addAll(this.getKraft(clone));
		}
		for(int i = 0; i < staaten.length; i++) {
			for(int j = i; j < staaten.length; j++) {
				boolean isNachbar = false;
				for(int k = 0; k < nachbarschaften.length; k++) {
					if(
							(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()) &&
							staaten[j].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel())) ||
							(staaten[i].getKürzel().equals(nachbarschaften[k].getStaat2().getKürzel()) &&
							staaten[j].getKürzel().equals(nachbarschaften[k].getStaat1().getKürzel()))
					) {
						isNachbar = true;
					}
				}

				kräfte.addAll(this.getKraft(staaten[i],staaten[j],isNachbar));
			}
		}

		this.useKräfte(kräfte);

		return new Iteration(iteration.getZähler() + 1, staaten, nachbarschaften);
	}
	
	public ArrayList<Kraft> getKraft(Nachbarschaft nachbarschaft) {
		
		double radsum = nachbarschaft.getStaat1().getRadius() + nachbarschaft.getStaat2().getRadius();
		double x = nachbarschaft.getStaat1().getKoordinaten()[0] - nachbarschaft.getStaat2().getKoordinaten()[0];
		double y = nachbarschaft.getStaat1().getKoordinaten()[1] - nachbarschaft.getStaat2().getKoordinaten()[1];
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double move = distance - radsum;

		ArrayList<Kraft> result = new ArrayList<Kraft>();
		if(distance != 0) {
			Kraft kraft1 = new Kraft(nachbarschaft.getStaat1(),-(x * move)/(2 * distance),-(y * move)/(2 * distance));
			result.add(kraft1);
			Kraft kraft2 = new Kraft(nachbarschaft.getStaat2(),(x * move)/(2 * distance),(y * move)/(2 * distance));
			result.add(kraft2);
		}
		
		return result;
	}
	
	public ArrayList<Kraft> getKraft(Staat staat1, Staat staat2, boolean isNachbar) {
		
		double radsum = staat1.getRadius() + staat2.getRadius();
		double x = staat1.getKoordinaten()[0] - staat2.getKoordinaten()[0];
		double y = staat1.getKoordinaten()[1] - staat2.getKoordinaten()[1];
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double move = distance - radsum;

		ArrayList<Kraft> result = new ArrayList<Kraft>();
		if(isNachbar || (move < 0 && distance != 0)) {
			Kraft kraft1 = new Kraft(staat1,-(x * move)/(2 * distance),-(y * move)/(2 * distance));
			result.add(kraft1);
			Kraft kraft2 = new Kraft(staat2,(x * move)/(2 * distance),(y * move)/(2 * distance));
			result.add(kraft2);
		}
		
		return result;
	}
	
	public void useKräfte(ArrayList<Kraft> kräfte) {

		for(int i = 0; i < kräfte.size(); i++) {
			int count = 0;
			for(int j = 0; j < kräfte.size(); j++) {
				if(kräfte.get(j).getStaat().getKürzel().equals(kräfte.get(i).getStaat().getKürzel())) {
					count++;
				}
			}
			double[] coord = kräfte.get(i).getStaat().getKoordinaten();
			double[] move = kräfte.get(i).getVektor();
			coord[0] = coord[0] + (move[0]/count);
			coord[1] = coord[1] + (move[1]/count);

			kräfte.get(i).getStaat().setKoordinaten(coord);
		}

	}
	

	@Override
	public double rateIteration(Karte karte, int n) {
		// TODO Auto-generated method stub
		return 0;
	}

}
