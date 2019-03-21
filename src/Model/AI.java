package Model;

import Controler.Game;
import JavaIA.MultiLayerPerceptron;
import JavaIA.SigmoidalTransferFunction;

public class AI {

	private static int nblayers = 5;
	private static double learning = 0.3;

	public static int play()
	{
		int[] layers = new int[]{ 9, nblayers, 9 };
		double error = 0.0 ;
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, learning, new SigmoidalTransferFunction());
		
		double[] inputs = new double[9]; 
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = Grille.getGrille()[i];
		}
		
		double[] outputs = new double[9];
		
		outputs = net.forwardPropagation(inputs);
		
		/*System.out.println("Result is : [ ");
		for (int i = 0; i < outputs.length; i++) {
			System.out.print(outputs[i] +" ");
		}
		System.out.println("]");*/
		int pos = coup(outputs);
		return pos;
		
	}

	public static int coup(double[] outputs)
	{
		double max = outputs[0];
		double mindemax = 1.0;
		int pos = 0;
		int[] coup = Grille.getGrille();
		
		for (int j = 0; j < coup.length; j++) {
			for (int i = 1; i < outputs.length; i++) {
				if(outputs[i] > max && max < mindemax) { max = outputs[i]; pos = i;}
			}
			
			if(Grille.getGrille()[pos] == 0)
			{
				coup[pos] = 2;
				Grille.setGrille(coup);
				System.out.println("Result is : [ ");
				for (int k = 0; k < coup.length; k++) {
					System.out.print(coup[k] +" ");
				}
				System.out.println("]");
				return pos;
			}
			else
			{
				mindemax = max;
			}
		}
		
		return 0;
	}
	
	public static int getNblayers() {
		return nblayers;
	}

	public static void setNblayers(int nblayers) {
		AI.nblayers = nblayers;
	}

	public static double getLearning() {
		return learning;
	}

	public static void setLearning(double learning) {
		AI.learning = learning;
	}
	
}
