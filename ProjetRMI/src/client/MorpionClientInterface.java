package client;

import java.rmi.*;

public interface MorpionClientInterface extends Remote {
	//méthode pour recevoir la grille
	public void getGrille(String source, String target, char[][] grille) throws RemoteException;
}
