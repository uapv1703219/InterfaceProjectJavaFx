package Controler;

import java.io.IOException;

import Model.Grille;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
	
	@FXML
	private Pane grille;
	
	@FXML 
	private Text errortext;
	
	@FXML
	private Ellipse roundp1;
	
	@FXML
	private Ellipse roundp2;
	
	@FXML
	private Text p1score;
	
	@FXML
	private Text p2score;
	
	private boolean tour = false;
	private boolean win = false;
	private Rectangle[] rectangles = new Rectangle[9];
	private int scorep1 = 0;
	private int scorep2 = 0;
	
	
	
	/* -------------------------- Interface --------------------------- */
	@Override
	public void start(Stage stage) throws Exception {
		try
    	{
    	Parent root = FXMLLoader.load(getClass().getResource("../View/playWindow.fxml"));
        stage.setTitle("Morpion : Game");
        stage.setScene(new Scene(root));
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
		 
	}
	
	public void finDeTour(int pos)
	{
		if(Grille.verificationWin(pos))
		{
			win = true;
			if (!tour)
			{
				scorep1++;
				p1score.setText(String.valueOf(scorep1));
			}
			else
			{
				scorep2++;
				p2score.setText(String.valueOf(scorep2));
			}
			errorTextInput("Gagn�");
		}
		else
		{
			tour = !tour; //change de tour
			errorTextInput("");
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
	
	public void playRectangle(MouseEvent event)
	{
		if (win) return;
		for (int i = 0; i < rectangles.length; i++) {
			if(event.getSource().equals(rectangles[i]))
			{
				if (Grille.putInPosition(tour, i))
				{
					if(tour)		//Tour du Player 1
					{
						rectangles[i].setFill(Color.RED);
					}
					else			//Tour du player 2
					{
						rectangles[i].setFill(Color.BLUE);
					}
					finDeTour(i);
				}
				else errorTextInput("Vous ne pouvez pas jouer sur une case d�ja jou�e !");
				break;
			}
		}
	}
	
	private void errorTextInput(String text)
	{
		errortext.setText(text);
	}
	
	public void homeReturn(MouseEvent event) throws IOException {	//Clic sur maison et retour home
		ImageView imageView = (ImageView) ( ((Node) event.getSource()).getScene().lookup("#home"));
		try 
		{
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
	
	public void resetGrille(ActionEvent event) {
		Grille.resetGrille();
		for (int i = 0; i < rectangles.length; i++) {
			rectangles[i].setFill(Color.WHITE);
		}
		win = false;
		tour = true;
	}

}