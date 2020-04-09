package client;

import java.rmi.server.UnicastRemoteObject;

import controleur.CtrlMorpion;
import javafx.application.Platform;
import modele.MorpionServerInterface;

import java.rmi.RemoteException;

public class MorpionClientImpl extends UnicastRemoteObject implements MorpionClientInterface {
	
	//interface du serveur morpion
	private MorpionServerInterface jeuxInterface;
	//id pour stocker le pion du joueur
	private String id = null;
	//controleur dans lequel ce situe le joueur
	private CtrlMorpion m;
	
	//constructeur
	public MorpionClientImpl(String id, MorpionServerInterface jeuxInterface, CtrlMorpion m) throws RemoteException {
		this.id = id;
		this.jeuxInterface = jeuxInterface;
		this.m = m;
	}
	
	//permet de récupérer la grille et mettre à jour le jeu
	public void getGrille(String source, String target, char[][] grille) throws RemoteException {
		
		
		//fonction qui permet de lancer la méthode setGrille du controleur morpion (en tâche de fond)
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					m.setGrille(grille);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
