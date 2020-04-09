package modele;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import client.MorpionClientInterface;

public class MorpionServerImpl extends UnicastRemoteObject implements MorpionServerInterface {
	
	private HashMap<String, MorpionClientInterface> morpionClients; //liste pour contenir les client
	
	public MorpionServerImpl() throws RemoteException {
		morpionClients = new HashMap<String, MorpionClientInterface>();
	}


	@Override
	public String echo() throws RemoteException {
		// TODO Auto-generated method stub
		return "Hola";
	}
	
	//on ajoute le client dans la liste morpionClients (synchronized= 1 à la fois)
	@Override
	public synchronized void joinMorpion(String id, MorpionClientInterface morpionClient) throws RemoteException {
		System.out.println("Un joueur à rejoint : " + id);
		this.morpionClients.put(id, morpionClient);	
	}
	
	
	//on supprime un client de la liste morpionClients
	@Override
	public synchronized void leaveMorpion(String id) throws RemoteException {
		this.morpionClients.remove(id);
	}
	
	
	//on renvoie la liste des clients connecté
	@Override
	public synchronized List<String> getWhoIsOnline() throws RemoteException {
		return new ArrayList<String>(morpionClients.keySet());
	}
	
	
	//on envoie la grille à un client grâce à sa méthode getGrille
	public synchronized void sendGrille(String source, String target, char[][] grille) throws RemoteException {
		morpionClients.get(target).getGrille(source, target, grille);	
	}

	//fonction pour vérifier la grille et renvoyer le gagnant ou si match nul
	@Override
	public char verifGrille(char[][] grille) throws RemoteException {
		//test ligne
		for(int i=0; i<3; i++) {
			if(grille[i][0]==grille[i][1] && grille[i][1]==grille[i][2]) { //si tt une ligne = même caractère, on renvoie le gagnant
				return grille [i][0];
			}
		}
		
		//test colonne
		for(int i=0; i<3; i++) {
			if(grille[0][i]==grille[1][i] && grille[1][i]==grille[2][i]) { //si tt une colonne = même caractère, on renvoie le gagnant
				return grille [0][i];
			}
		}
		
		//test diagonale
		if(grille[0][0]==grille[1][1] && grille[1][1]==grille[2][2]) {
			return grille [0][0];
		}
		if(grille[0][2]==grille[1][1] && grille[1][1]==grille[2][0]) {
			return grille [0][2];
		}
		
		
		//test si tout les cases sont rempli ou non
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(grille[i][j]!='X' && grille[i][j]!='O') {
					return 0;
				}
			}
		}
		//si aucun il y a eu aucun return précedemment, match nul
		return 'n';
		
	}
	
	

}
