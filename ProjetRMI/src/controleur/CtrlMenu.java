package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CtrlMenu {
	
	@FXML
	private Button btnPendu, btnMorpion;
	
	private static Stage primaryStage;
	private Node defaultPane;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		CtrlMenu.primaryStage = primaryStage;
	}
	
	//lancement du jeu de pendu
	public void lancePendu() {
		//on ferme le stage actuel
		Stage menu = (Stage) btnPendu.getScene().getWindow();
	    menu.close();
		
		//lancement de la seconde fenetre
		
	      try{
	    	  	Stage stage = new Stage();
	            
				VBox pendu = (VBox)FXMLLoader.load(getClass().getResource("../Vues/pendu.fxml"));
				Scene scene = new Scene(pendu); //redimmension auto 
				
	            stage.setTitle("Pendu 2000");       
	            stage.setScene(scene);		
				stage.setResizable(false);
	            stage.show();
	           
	    		
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	}
	
	
	//lancement du jeu de morpion
	public void lanceMorpion() {
		//on ferme le stage actuel
		Stage menu = (Stage) btnPendu.getScene().getWindow();
	    menu.close();
		
		//lancement de la seconde fenetre
		
	      try{
	    	  	Stage stage = new Stage();
	            
				VBox morpion = (VBox)FXMLLoader.load(getClass().getResource("../Vues/Morpion.fxml"));
				Scene scene = new Scene(morpion); //redimmension auto 
	            
	            stage.setTitle("Morpion 2000");       
	            stage.setScene(scene);		
				stage.setResizable(false);
	            stage.show();
	           
	    		
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	}
	
	
	//Si on appuie sur le bouton quitter
	public void quitter() {
		System.exit(0);
	}
	
	
}
