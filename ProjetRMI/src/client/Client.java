package client;

import java.net.URL;

import controleur.CtrlMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {
	
	private Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Chargement de la fenetre Menu.fxml
		try {
			URL fxmlURL= getClass().getResource("/vues/Menu.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			

			scene = new Scene((VBox) root,640,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			CtrlMenu ctrl = (CtrlMenu) fxmlLoader.getController();
			ctrl.setPrimaryStage(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("STEAM 2000");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
