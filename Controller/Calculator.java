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
		Iteration next = iteration.clone();
		Staat[] staaten = next.getStaaten();
		Nachbarschaft[] nachbarschaften = next.getNachbarschaften();

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
		return next;
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
	public double rateIteration(Karte karte, int n, boolean ignoreOriginCenter) {
		
		double sum = 0;
		Iteration first = karte.getIterationen().get(0);
		Iteration target = karte.getIterationen().get(n);
		ArrayList<Kraft> kräfte = new ArrayList<Kraft>();

		for(int i = 0; i < target.getStaaten().length; i++) {
			for(int j = i; j < target.getStaaten().length; j++) {
				boolean isNachbar = false;
				for(int k = 0; k < target.getNachbarschaften().length; k++) {
					if(
							(target.getStaaten()[i].getKürzel().equals(target.getNachbarschaften()[k].getStaat1().getKürzel()) &&
									target.getStaaten()[j].getKürzel().equals(target.getNachbarschaften()[k].getStaat2().getKürzel())) ||
							(target.getStaaten()[i].getKürzel().equals(target.getNachbarschaften()[k].getStaat2().getKürzel()) &&
									target.getStaaten()[j].getKürzel().equals(target.getNachbarschaften()[k].getStaat1().getKürzel()))
					) {
						isNachbar = true;
					}
				}

				kräfte.addAll(this.getKraft(target.getStaaten()[i],target.getStaaten()[j],isNachbar));
			}
		}
		
		for(int i = 0; i < kräfte.size(); i++) {
			sum = sum + Math.sqrt(Math.pow(kräfte.get(i).getVektor()[0], 2) + Math.pow(kräfte.get(i).getVektor()[1], 2));
		}
		
		if(!ignoreOriginCenter) {
			for(int i = 0; i < first.getStaaten().length; i++) {
				double[] root = first.getStaaten()[i].getKoordinaten();
				double[] now = target.getStaaten()[i].getKoordinaten();
				sum = sum + Math.sqrt(Math.pow(root[0] - now[0], 2) + Math.pow(root[1] - now[1], 2));
			}
		}
		return sum;
	}
	
	public void preventIntersections(Karte karte) {
		Staat[] staaten = karte.getIterationen().get(0).getStaaten();
		Nachbarschaft[] nachbarschaften = karte.getIterationen().get(0).getNachbarschaften();
		double intersect = -1;
		
		while(intersect < 0) {
			intersect = 0;
			for(int i = 0; i < staaten.length; i++) {
				for(int j = i; j < staaten.length; j++) {
					double radsum = staaten[i].getRadius() + staaten[j].getRadius();
					double x = staaten[i].getKoordinaten()[0] - staaten[j].getKoordinaten()[0];
					double y = staaten[i].getKoordinaten()[1] - staaten[j].getKoordinaten()[1];
					double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
					double move = distance - radsum;
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
					if((move < 0) || isNachbar) {
						intersect = intersect + move;
					}
				}
			}
			if(intersect < 0) {
				for(int i = 0; i < staaten.length; i++) {
					staaten[i].setRadius(staaten[i].getRadius() * 0.8);
				}
			}
		}
	}

}
