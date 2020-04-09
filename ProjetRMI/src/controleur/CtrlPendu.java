package controleur;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import modele.PenduServerInterface;

public class CtrlPendu implements Initializable {
	
	@FXML
	private TextField txtMot;
	@FXML
	private Line part1; //Elements graphiques pour le pendu
	@FXML
	private Line part2;
	@FXML
	private Line part3;
	@FXML
	private Line part4;
	@FXML
	private Line part5;
	@FXML
	private Circle part6;
	@FXML
	private Line part7;
	@FXML
	private Line part8;
	@FXML
	private Line part9;
	@FXML
	private Line part10;
	@FXML
	private Line part11;
	@FXML
	private Button btnRetour, btnRecommencer;
	@FXML
	private Label lblInfo;
	@FXML
	private GridPane grilleLettres; //grille contenant tout les boutons des lettres
	
	private ArrayList<Shape> liste; //liste pr les élements graphique
	
	private int nbErreurs = 0; 
	private String mot = ""; //mot a deviner

	private static Stage primaryStage;
	private Node defaultPane;
	
	private PenduServerInterface penduInterface; //interface du serveur pendu

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		CtrlPendu.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//on ajoute tt les elements dans la liste
		liste = new ArrayList<>();
		liste.add(part1);
		liste.add(part2);
		liste.add(part3);
		liste.add(part4);
		liste.add(part5);
		liste.add(part6);
		liste.add(part7);
		liste.add(part8);
		liste.add(part9);
		liste.add(part10);
		liste.add(part11);
		//puis on cache tt les élements
		ListIterator<Shape> itr = liste.listIterator();
		while(itr.hasNext()) {
			itr.next().setVisible(false);
		}
		
		btnRecommencer.setVisible(false);
		
		//on se connecte au serveur et on recupère un mot
		try {
			penduInterface = (PenduServerInterface) Naming.lookup("rmi://localhost:8001/pendu");
			mot = penduInterface.getMot();
			System.out.println(mot);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//on affiche des * selon la longueur du mot à deviner
		for(int i=0; i<mot.length(); i++) {
			txtMot.setText(txtMot.getText() + "*");
		}
	}
	
	
	//on teste une lettre
	public void verifLettre(ActionEvent event) {
		//on recupère le bouton actionné
		Button btn = (Button) event.getTarget();
		
		char car = btn.getText().toLowerCase().charAt(0);
		
		String affiMot = txtMot.getText();
		
		StringBuffer buffer = new StringBuffer(affiMot);
		
		//si le mot contient la lettre testé, on remplace son étoile par la lettre
		if(mot.contains(btn.getText().toLowerCase())) {
			for(int i=0; i<mot.length(); i++) {
				if(mot.charAt(i) == car) {
					buffer.setCharAt(i, car);
				}
			}
			//puis on réaffiche le mot
			txtMot.setText(buffer.toString());
		}
		else {
			//Sinon on affiche une partie du pendu et on incrémente le nb d'erreurs
			liste.get(nbErreurs).setVisible(true);
			nbErreurs++;
		}
		btn.setDisable(true);
		
		//si le mot ne contient plus d'*, on a gagné
		if(!txtMot.getText().contains("*")) {
			System.out.println("Victoire");
			lblInfo.setText("Vous avez gagné !");
			grilleLettres.setDisable(true);
			btnRecommencer.setVisible(true);
		}
		//sinon si 11 erreurs, on a perdu
		else if(nbErreurs == 11) {
			System.out.println("Défaite");
			lblInfo.setText("Vous avez perdu ! La réponse était " + mot);
			grilleLettres.setDisable(true);
			btnRecommencer.setVisible(true);
		}	
	}
	
	
	//si on recommence
	public void recommencer() throws RemoteException {
		nbErreurs = 0;
		
		//on cache tt les elements graphique
		ListIterator<Shape> itr = liste.listIterator();
		while(itr.hasNext()) {
			itr.next().setVisible(false);
		}
		
		grilleLettres.setDisable(false);
		
		//on réactive tt les boutons de lettr
		ListIterator<Node> itrNode = grilleLettres.getChildren().listIterator();
		while(itrNode.hasNext()) {
			itrNode.next().setDisable(false);
		}
		
		btnRecommencer.setVisible(false);
		lblInfo.setText("");
		txtMot.setText("");
		
		//on recupère un nouveau mot sur le serveur
		mot = penduInterface.getMot();
		System.out.println(mot);
		
		for(int i=0; i<mot.length(); i++) {
			txtMot.setText(txtMot.getText() + "*");
		}
		
	}
	
	//Si on reviens au menu
	public void retour() {
		//on ferme le stage actuem
		Stage pendu = (Stage) btnRetour.getScene().getWindow();
	    pendu.close();
		
		
		//lancement de la seconde fenetre
		
	      try{
	    	  	Stage stage = new Stage();
	            
				VBox menu = (VBox)FXMLLoader.load(getClass().getResource("../Vues/Menu.fxml"));
				Scene scene = new Scene(menu); //redimmension auto 
	            
	            stage.setTitle("STEAM 2000");       
	            stage.setScene(scene);		
				stage.setResizable(false);
	            stage.show();
	           
	    		
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	}

}
