package Controler;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainMenuControler extends Application {
	
	private Stage stage;
	
	public static void launchApp(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws IOException {
    	try
    	{
    	Parent root = FXMLLoader.load(getClass().getResource("../View/mainMenu.fxml"));
        stage.setTitle("Morpion : Main Menu");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        }
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public void launchPlay(ActionEvent event) throws IOException {
		Button btn = (Button) ( ((Node) event.getSource()).getScene().lookup("#playButton"));
		try 
		{
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/playWindow.fxml"));
	        Stage stage = (Stage) btn.getScene().getWindow();
	        Scene scene = new Scene(loader.load());
	        stage.setScene(scene);
	        stage.setTitle("Morpion : Game");
	    }
		catch (IOException io)
		{
	        io.printStackTrace();
	    }
	}
	
}
