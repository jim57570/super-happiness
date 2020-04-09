package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import client.MorpionClientInterface;

public interface MorpionServerInterface extends Remote {
	//interface pour le jeu de morpion
	
	public String echo() throws RemoteException;
	
	public void sendGrille(String source, String target, char[][] grille) throws RemoteException;
	
	public void joinMorpion(String id, MorpionClientInterface morpionClient) throws RemoteException;
	
	public void leaveMorpion(String id) throws RemoteException;
	
	public char verifGrille(char[][] grille) throws RemoteException;
	
	List<String> getWhoIsOnline() throws RemoteException;

}
