package Controler;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
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
		Button btnAi = (Button) ( ((Node) event.getSource()).getScene().lookup("#aibutton"));
		try 
		{
			if(btnAi == event.getSource()) { Game.setAi(true); }
			else { Game.setAi(false); }
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/playWindow.fxml"));
	        Stage stage = (Stage) btnAi.getScene().getWindow();
	        Scene scene = new Scene(loader.load());
	        stage.setScene(scene);
	        stage.setTitle("Morpion : Game");
	    }
		catch (IOException io)
		{
	        io.printStackTrace();
	    }
	}
	
	public void exitApp(ActionEvent event) {
		Platform.exit();
        System.exit(0);
	}
	
}
