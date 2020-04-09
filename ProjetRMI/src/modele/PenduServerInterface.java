package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PenduServerInterface extends Remote {
	//interface pour le pendu
	
	public String echo() throws RemoteException;
	
	public String getMot() throws RemoteException;
}
