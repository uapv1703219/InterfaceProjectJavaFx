package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Scanner;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import Controler.Game;
import JavaIA.MultiLayerPerceptron;
import JavaIA.SigmoidalTransferFunction;

public class AI {

	private static int nblayers = 5;
	private static double learning = 0.3;

	private static MultiLayerPerceptron net;
	
	public static void init()
	{
		File monFichier = new File("src/ressources/AiModel/AiModel_l="+learning+"_h="+nblayers+".ser"); 
		if (monFichier.exists())
		{
			FileInputStream fileIn;
			try {
				fileIn = new FileInputStream("src/ressources/AiModel/AiModel_l="+learning+"_h="+nblayers+".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
		        net = (MultiLayerPerceptron) in.readObject();
		        in.close();
		        fileIn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			int[] layers = new int[]{ 9, nblayers, 9 };
			net = new MultiLayerPerceptron(layers, learning, new SigmoidalTransferFunction());
		}
	}
	
	public static void learn()
	{
		double[] output = new double[9];
		double[] input = new double[9];
		String line = null;
		
		Game.showProgressBar();
		
		double error = 0.0;
		boolean change = false;
		
		try
		{
			File f = new File("src/ressources/AiDataLearn/dataCoup.txt");
	        Scanner fileScanner = new Scanner(f);
	        for (int j = 0; j < 100000000; j++) {
	        	change = false;
	        	
	        while(fileScanner.hasNextLine()){
	           line = fileScanner.nextLine();
	           
	        	   for (int i = 0; i < 9; i++) {
		        	   input[i] = Character.getNumericValue(line.charAt(i*2));
		        	   output[i] = Character.getNumericValue(line.charAt(i*2 + 20));
		        	   if (output[i] == 2 ) { change = true; }
	        	   }
	        	   for (int i = 0; i < input.length; i++) {
	        		   if(change)
	        		   {
	        			   if (input[i] == 1) { input[i] = 2; }
	        			   else if(input[i] == 2) { input[i] = 1; }
	        			   if (output[i] == 1) { output[i] = 2; }
	        			   else if(output[i] == 2) { output[i] = 1; }
	        		   }
			        	   if(input[i] == 1) { input[i] = 0.5; }
			        	   else if(input[i] == 2) { input[i] = 1.0; }
			        	   
			        	   if(output[i] == 1) { output[i] = 0.5; }
			        	   else if(output[i] == 2) { output[i] = 1.0; }
			        	   
			        	   error += net.backPropagate(input, output);
	        	   }
			}
	           
	           //System.out.println(Arrays.toString(input) +" " + Arrays.toString(output));
	        if ( ((j / 100000000.0)* 100.0) % 1.0 == 0.0 ) 
	        	{
	        		//System.out.println("Error at step "+j+" is "+ (error/(double)j));
	        		System.out.println(j / 100000000.0);
	        		Game.progressBar(j / 100000000.0);
	        	}
	        }
	        //Game.hideProgressBar();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int play()
	{
		double error = 0.0 ;
		
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
		//System.out.println("hello");
		
		for (int j = 0; j < coup.length; j++) {
			for (int i = 1; i < outputs.length; i++) {
				if(outputs[i] > max && outputs[i] < mindemax) { max = outputs[i]; pos = i;}
			}
			
			if(Grille.getGrille()[pos] == 0)
			{
				coup[pos] = 1;
				Grille.setGrille(coup);
//				System.out.println("Result is : [ ");
//				for (int k = 0; k < coup.length; k++) {
//					System.out.print(coup[k] +" ");
//				}
//				System.out.println("]");
				return pos;
			}
			else
			{
				mindemax = max;
				max = 0;
			}
		}
		
		return 0;
	}
	
	public static void saveModel()
	{
		 try {
			FileOutputStream fileOut = new FileOutputStream("src/ressources/AiModel/AiModel_l="+nblayers+"_h="+learning+".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(net);
	        out.close();
	        fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//File monFichier = new File(); 
	}
//	public static int countNbrOfLine()
//	{
//		try {
//			File file = new File("src/ressources/AiDataLearn/dataCoup.txt");
//			if(file.exists())
//			{
//				FileReader fr = new FileReader(file);
//			    LineNumberReader lnr = new LineNumberReader(fr);
//			    int linenumber = 0;
//			    
//	            while (lnr.readLine() != null){
//	        	linenumber++;
//	            }
//	 
//	            return linenumber;
//			}
//			else{
//				 System.out.println("File does not exists!");
//			}
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//		}
//		return 0;
//	}
	
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
