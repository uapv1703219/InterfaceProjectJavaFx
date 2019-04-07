package Controler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import JavaIA.MultiLayerPerceptron;
import JavaIA.SigmoidalTransferFunction;
import Model.AI;
import Model.Grille;
import Model.WinRectangles;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application{
	
	@FXML
	private Pane grille;
	@FXML
	private Pane properties;
	@FXML
	private Pane timerpane;
	@FXML
	private Ellipse roundp1;
	@FXML
	private Ellipse roundp2;
	@FXML 
	private Text errortext;
	@FXML
	private Text messagetext;
	@FXML
	private Text p1score;
	@FXML
	private Text p2score;
	@FXML
	private ComboBox<String> difficulty;
	@FXML
	private TextField learninginput;
	@FXML
	private TextField layersinput;
	@FXML
	private static ProgressBar bar;
	
	private static boolean ai = true;
	private boolean tour = false;
	private boolean win = false;
	private int compteurtour = 0;
	private Rectangle[] rectangles = new Rectangle[9];
	private int scorep1 = 0;
	private int scorep2 = 0;
	private WinRectangles winrectangles = new WinRectangles();
	private boolean showproperties = false;
	private boolean showtimer = false;
	
	private ArrayList<int[]> coupJ1 = new ArrayList<int[]>();
	private ArrayList<int[]> coupJ2 = new ArrayList<int[]>();
	
	
	// -------------------------- SETUP SECTION --------------------------- //
	@Override
	public void start(Stage stage) throws Exception {
		try
    	{
    	Parent root = FXMLLoader.load(getClass().getResource("../View/playWindow.fxml"));
        stage.setTitle("Morpion : Game");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        }
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
		
	}
	
	@FXML
	void initialize() {
		 for (int i = 0; i < rectangles.length; i++) {		//Recup�re les rectangles
				rectangles[i] = (Rectangle) grille.getChildren().get(i);
			}
		 roundp1.setFill(Color.BLUE);
		 roundp2.setFill(Color.RED);
		 roundp2.setOpacity(0.5);
		 
		 difficulty.getItems().removeAll(difficulty.getItems());
		 difficulty.getItems().addAll("Facile", "Moyen", "Difficile");
		 difficulty.getSelectionModel().select("Moyen");
		 
		 learninginput.setText(Double.toString(AI.getLearning()));
		 layersinput.setText(Integer.toString(AI.getNblayers()));
		 
		 if(ai) { AI.init(); }
		 
	}
	
	//-------------------- INTERFACE & GAME SECTION ------------------//
	
	
	public void finDeTour(int pos)
	{
		errorTextInput("");
		if(Grille.verificationWin(pos, winrectangles))
		{
			win = true;
			
			saveCoupsWin(tour);
			
			fade();
			if (!tour)
			{
				scorep1++;
				p1score.setText(String.valueOf(scorep1));
				messageTextInput("Player 1 a gagné !");
			}
			else
			{
				scorep2++;
				p2score.setText(String.valueOf(scorep2));
				messageTextInput("Player 2 a gagné !");
			}
			winrectangles.reset();
		}
		else
		{
			compteurtour++;
			if(compteurtour == 9) 
			{
				win = true;
				messageTextInput("Egalitée !");
				fillTransition();
				return;
			}
			tour = !tour; //change de tour
			if(ai) {
				int[] tmp = new int[9];
				int[] coup = new int[9];
				compteurtour++;
				int position = AI.play();
				//System.out.println(position);
				tmp = Grille.getGrille().clone();
				tmp[position] = 0;
				coupJ1.add(tmp);
				coup[position] = 1;
				coupJ1.add(coup);
				rectangles[position].setFill(Color.RED);
				//System.out.println(Grille.verificationWin(pos, winrectangles));
				if(Grille.verificationWin(position, winrectangles))
				{
					win = true;
					saveCoupsWin(tour);
					scorep2++;
					p2score.setText(String.valueOf(scorep2));
					fade();
					messageTextInput("IA a gagné !");
				}
				//System.out.println(Arrays.toString(Grille.getGrille()));
				tour = !tour;
				
			}
			else
			{
				if(tour) 
				{
					roundp1.setOpacity(0.5);
					roundp2.setOpacity(1.0);
				}
				else
				{
					roundp1.setOpacity(1);
					roundp2.setOpacity(0.5);
				}
			}
		}
	}
	
	public void playRectangle(MouseEvent event)
	{
		if (win) return;
		int[] tmp = new int[9];
		int[] coup = new int[9];
		for (int i = 0; i < rectangles.length; i++) {
			if(event.getSource().equals(rectangles[i]))
			{
				if (Grille.putInPosition(tour, i))
				{
					//System.out.println(tour);
					if(tour)		//Tour du Player 1
					{
						tmp = Grille.getGrille().clone();
						tmp[i] = 0;
						coupJ1.add(tmp);
						coup[i] = 1;
						coupJ1.add(coup);
						rectangles[i].setFill(Color.RED);
					}
					else			//Tour du player 2
					{
						tmp = Grille.getGrille().clone();
						tmp[i] = 0;
						coupJ2.add(tmp);
						coup[i] = 2;
						coupJ2.add(coup);
						rectangles[i].setFill(Color.BLUE);
					}
					finDeTour(i);
				}
				else errorTextInput("Vous ne pouvez pas jouer sur une case d�ja jou�e !");
				break;
			}
		}
	}
	
	private void fillTransition()
	{
		for (int i = 0; i < rectangles.length; i++) {
			FillTransition ft = new FillTransition(Duration.millis(3000), rectangles[i], (Color) rectangles[i].getFill(), Color.WHITE);
			ft.play();
			
			FadeTransition fades = new FadeTransition(Duration.millis(1499), rectangles[i]);
			fades.setFromValue(10);  
	        fades.setToValue(0.1);
	        fades.play();
		}
	}
	
	private void fade()
	{
		boolean test = false;
		int[] perdants = new int[6];
		int cpt = 0;
		for (int i = 0; i < rectangles.length; i++) {
			for (int j = 0; j < winrectangles.getWinrectangles().length; j++) {
				if (i == winrectangles.getWinrectangles()[j]) {
					test = true;
					break;
				}
			}
			if(test) { test = false; }
			else { perdants[cpt] = i; cpt++; }
		}
		
		//System.out.println(Arrays.toString(perdants));
		
		for (int i = 0; i < perdants.length; i++) {
			FadeTransition fades = new FadeTransition(Duration.millis(1499), rectangles[perdants[i]]);
			fades.setFromValue(10);  
	        fades.setToValue(0.1);
	        fades.play();
		}
	}
	
	private void errorTextInput(String text)
	{
		errortext.setText(text);
	}
	
	private void messageTextInput(String text)
	{
		messagetext.setText(text);
	}
	
	public void homeReturn(MouseEvent event) throws IOException {	//Clic sur maison et retour home
		ImageView imageView = (ImageView) ( ((Node) event.getSource()).getScene().lookup("#home"));
		try 
		{
			if(ai) { AI.saveModel(); }
			Grille.resetGrille();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/mainMenu.fxml"));
	        Stage stage = (Stage) imageView.getScene().getWindow();
	        Scene scene = new Scene(loader.load());
	        stage.setScene(scene);
	        stage.setTitle("Morpion : Main Menu");
	    }
		catch (IOException io)
		{
	        io.printStackTrace();
	    }
	}
	
	public void setAi()
	{
		if (compteurtour != 0) { errorTextInput(""); messageTextInput(""); errorTextInput("Vous ne pouvez modifier l'IA qu'en d�but de partie !"); }
		else
		{
			AI.setLearning(Double.parseDouble(learninginput.getText()));
			AI.setNblayers(Integer.parseInt(layersinput.getText()));
			AI.learn();
		}
	}
	
	public static void hideProgressBar()
	{
		bar.setVisible(false);
	}
	
	public static void showProgressBar()
	{
		bar.setVisible(true);
	}
	
	public static void progressBar(double pourcentage)
	{
		bar.setProgress(50.0);
	}

	private void saveCoupsWin(boolean tour)
	{
		try {
			int[] tmp = null;
			FileWriter fw = new FileWriter("src/ressources/AiDataLearn/dataCoup.txt", true);
			if (tour) {
				for(int i = 0; i < coupJ1.size(); i += 2)
				{
					tmp = coupJ1.get(i);
					for (int j = 0; j < 9; j++) {
						fw.write(tmp[j] + " ");
					}
					fw.write("/ ");
					tmp = coupJ1.get(i+1);
					for (int j = 0; j < 9; j++) {
						fw.write(tmp[j] + " ");
					}
					fw.write("\r\n");
					//System.out.println(Arrays.toString(coupJ1.get(i)));
				}
			}
			else
			{
				for(int i = 0; i < coupJ2.size(); i += 2)
				{
					tmp = coupJ2.get(i);
					for (int j = 0; j < 9; j++) {
						fw.write(tmp[j] + " ");
					}
					fw.write("/ ");
					tmp = coupJ2.get(i+1);
					for (int j = 0; j < 9; j++) {
						fw.write(tmp[j] + " ");
					}
					fw.write("\r\n");
					//System.out.println(Arrays.toString(coupJ1.get(i)));
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//-------------------- SHOW/HIDE SECTION-------------------//
	
	public void showHideProperties(MouseEvent event)
	{
		showproperties = !showproperties;
		properties.setVisible(showproperties);
	}
	
	public void showTimer()
	{
		showtimer = !showtimer;
		timerpane.setVisible(showtimer);
	}
	
	public void chooseDificulty(ActionEvent event)
	{
		//System.out.println(difficulty.getSelectionModel().getSelectedItem());
		if(difficulty.getSelectionModel().getSelectedItem() == "Facile")
		{
			AI.setLearning(0.5);
			AI.setNblayers(2);
		}
		else if(difficulty.getSelectionModel().getSelectedItem() == "Moyen")
		{
			AI.setLearning(0.3);
			AI.setNblayers(5);
		}
		else if(difficulty.getSelectionModel().getSelectedItem() == "Difficile")
		{
			AI.setLearning(0.1);
			AI.setNblayers(10);
		}
		learninginput.setText(Double.toString(AI.getLearning()));
		layersinput.setText(Integer.toString(AI.getNblayers()));
	}
	
	//-------------------- RESET SECTION-------------------//
	
	public void resetGrille(ActionEvent event) {
		Grille.resetGrille();
		for (int i = 0; i < rectangles.length; i++) {
			rectangles[i].setOpacity(1.0);
			rectangles[i].setFill(Color.WHITE);
		}
		errorTextInput("");
		messageTextInput("");
		
		coupJ1 = new ArrayList<int[]>();
		coupJ2 = new ArrayList<int[]>();
		
		compteurtour = 0;
		tour = debutTour();
		//System.out.println(tour);
		if(win)
		{
			for (int i = 0; i < rectangles.length; i++) {
				FadeTransition tmp = new FadeTransition(Duration.millis(1500), rectangles[i]);
				tmp.setFromValue(0.1);  
				tmp.setToValue(10);
				tmp.play();
			}
			win = false;
		}
	}
	
	private boolean debutTour()
	{
		if(ai ) { return false; }
		else
		{
			if(Math.random() <= 0.5) 
			{
				roundp1.setOpacity(0.5);
				roundp2.setOpacity(1.0);
				return true; 
			}
			else 
			{
				roundp1.setOpacity(1);
				roundp2.setOpacity(0.5);
				return false; 
			}
		}
	}

	public static void setAi(boolean ai) {
		Game.ai = ai;
	}
	
	
}
