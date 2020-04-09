package controleur;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import client.MorpionClientImpl;
import client.MorpionClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.MorpionServerInterface;

public class CtrlMorpion implements Initializable {
	
	@FXML
	public GridPane GridPane; //grille contenant les boutons
	@FXML
	private Label lblInfo;
	@FXML
	private Button btn_1; //les 9 boutons de la grille
	@FXML
	private Button btn_2;
	@FXML
	private Button btn_3;
	@FXML
	private Button btn_4;
	@FXML
	private Button btn_5;
	@FXML
	private Button btn_6;
	@FXML
	private Button btn_7;
	@FXML
	private Button btn_8;
	@FXML
	private Button btn_9;
	@FXML
	private Button btnJoin, btnRetour;
	
	private static Stage primaryStage;
	private Node defaultPane;
	
	private char[][] grille; //grille de caractère pour stocker les pions
	private char pion1, pion2; //pion1 pour le pion du joueur, pion2 pour son adversaire
	private ArrayList<Button> listeBtn; //liste pour tout les boutons
	
	
	public MorpionClientInterface morpionClient; //on utilise l'interface de morpionClient
	public MorpionServerInterface jeuxInterface; //interface du serveur morpion

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		CtrlMorpion.primaryStage = primaryStage;
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//on désactive la grille
		GridPane.setDisable(true);
		lblInfo.setText("Veuillez rejoindre une partie");
		
		//on ajoute tt les boutons ds la liste
		listeBtn = new ArrayList<>();
		listeBtn.add(btn_1);
		listeBtn.add(btn_2);
		listeBtn.add(btn_3);
		listeBtn.add(btn_4);
		listeBtn.add(btn_5);
		listeBtn.add(btn_6);
		listeBtn.add(btn_7);
		listeBtn.add(btn_8);
		listeBtn.add(btn_9);
		
		//on se connecte sur l'interface serveur morpion
		try {
			jeuxInterface = (MorpionServerInterface) Naming.lookup("rmi://localhost:8000/pendu");
		} catch (Exception e) {
			System.out.println(e);
		}	
		
	}
	
	//si on appuie sur un des boutons de la grille
	public void onClick(ActionEvent event) throws RemoteException {
		//on récupère le bouton actionné
		Button btn = (Button) event.getSource();
		System.out.println(btn.getId() + " " + btn.getParent());
		
		//on récupère ses coordonnées
		int x = GridPane.getRowIndex(btn);
		int y = GridPane.getColumnIndex(btn);
		
		//on affiche son pion dans la grille
		grille[x][y] = pion1;
		
		//on affiche le pion sur le bouton et on désactive la grille
		btn.setText(String.valueOf(pion1));
		btn.setDisable(true);
		GridPane.setDisable(true);
		
		//on envoie la grille à l'adversaire
		jeuxInterface.sendGrille(String.valueOf(pion1), String.valueOf(pion2), grille);
		
		//on vérifie ensuite la grille pour savoir si on a fini
		char verif = jeuxInterface.verifGrille(grille);
		
		
		//Si le jeu est terminé, on quitte le serveur morpion
		if(verif==pion1) {
			lblInfo.setText("Vous avez gagné !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else if(verif==pion2) {
			lblInfo.setText("Vous avez perdu !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else if(verif=='n') {
			lblInfo.setText("Match nul !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else {	
			lblInfo.setText("Au tour de joueur suivant");
		
		}
		
		
		
		
	}
	
	public void joinPartie() throws RemoteException, InterruptedException {
		
		//on réinitialise la grille et les boutons de la grille
		grille = new char[3][3];
		
		ListIterator<Button> itr = listeBtn.listIterator();
		while(itr.hasNext()) {
			Button b = itr.next();
			b.setText("");
			b.setDisable(false);
		}
		
		
		//on regarde le nb de joueurs présent sur le serveur morpion
		if(jeuxInterface.getWhoIsOnline().size() >= 2) {
			lblInfo.setText("Une partie est déjà en cours, merci de revenir plus tard");
		}
		else if(jeuxInterface.getWhoIsOnline().size() == 0) {
			lblInfo.setText("En attente d'un autre joueur");
			morpionClient = new MorpionClientImpl("X",jeuxInterface, this);
			pion1 = 'X';
			pion2 = 'O';
			btnJoin.setDisable(true);
			
			jeuxInterface.joinMorpion("X", morpionClient);
		}
		else if(jeuxInterface.getWhoIsOnline().size() == 1) {
			lblInfo.setText("La partie commence à vous de jouer");
			morpionClient = new MorpionClientImpl("O",jeuxInterface, this);
			jeuxInterface.joinMorpion("O", morpionClient);
			pion1 = 'O';
			pion2 = 'X';
			GridPane.setDisable(false);
			btnJoin.setDisable(true);
		}
	}
	
	//pour mettre à jour la grille
	public void setGrille(char[][] grille) throws RemoteException {
		this.grille = grille;
		
		char verif = jeuxInterface.verifGrille(grille);
		
		//on met à jour les boutons sur la grille
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.println(i + " " + j);
				if(grille[i][j] == 'X') {
					((Button) GridPane.getChildren().get(grille.length * i + j)).setText("X");
					((Button) GridPane.getChildren().get(grille.length * i + j)).setDisable(true);
				}
				else if(grille[i][j] == 'O') {
					((Button) GridPane.getChildren().get(grille.length * i + j)).setText("O");
					((Button) GridPane.getChildren().get(grille.length * i + j)).setDisable(true);
				}
			}
		}
		
		//puis on vérifie si on a fini le jeu
		if(verif==pion1) {
			lblInfo.setText("Vous avez gagné !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else if(verif==pion2) {
			lblInfo.setText("Vous avez perdu !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else if(verif=='n') {
			lblInfo.setText("Match nul !!");
			jeuxInterface.leaveMorpion(String.valueOf(pion1));
			btnJoin.setDisable(false);
		}
		else {	
			lblInfo.setText("A vous de jouer");
			GridPane.setDisable(false);
		
		}
	}
	
	//pour revenir au menu
	public void retour() throws RemoteException {
		//on quitte le serveur morpion si on est dans une partie 
		jeuxInterface.leaveMorpion(String.valueOf(pion1));
		
		//on ferme le stage actuel
		Stage morpion = (Stage) btnRetour.getScene().getWindow();
	    morpion.close();
		
		
		//lancement de la seconde fenetre
		
	      try{
	    	  	Stage stage = new Stage();
	            
				VBox menu = (VBox)FXMLLoader.load(getClass().getResource("../Vues/menu.fxml"));
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

