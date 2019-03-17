package test;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

	public class test extends Application {
	    
		static boolean hide;
		static boolean color;
		
//		public static void main(String[] args) {
//	        launch(args);
//	    }
	    
	    @Override
	    public void start(Stage stage) throws IOException {
	    	try
	    	{
	    	Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
	        stage.setTitle("FXML Welcome");
	        stage.setScene(new Scene(root));
	        stage.show();
	        }
	    	catch(IOException e)
	    	{
	    		e.printStackTrace();
	    	}
	 
	    }
	    
	    public HBox createHBox () {       
        	HBox hbox = new HBox();      
        	hbox.setStyle("−fx−background−color :#336699;");
        	Button b1 = new Button("Bouton1");      
        	Button b2 = new Button("Bouton2");  
        	Button b3 = new Button("Bouton3");
        	hbox.getChildren().addAll(b1, b2, b3); 
        	return hbox; 
        	}
	    
	    public void handle(ActionEvent event) {
            System.out.println("Hello World!");
        }
	    
	    public void hideCircle(ActionEvent event) {
	    	Circle circle = (Circle) ( ((Node) event.getSource()).getScene().lookup("#bluecircle"));
	    	circle.setVisible(hide);
	    	hide = !hide;
	    }
	    
	    public void changeColorCircle(ActionEvent event) {
	    	Circle circle = (Circle) ( ((Node) event.getSource()).getScene().lookup("#bluecircle"));
	    	if(color)
	    	{
	    		circle.setFill(Color.RED);
	    	}
	    	else
	    	{
	    		circle.setFill(Color.BLUE);
	    	}
	    	color = !color;
	    }
}
