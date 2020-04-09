package modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class PenduServerImpl extends UnicastRemoteObject implements PenduServerInterface {
	
	public PenduServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String echo() throws RemoteException {
		// TODO Auto-generated method stub
		return "Hola";
	}
	
	//permet de récuperer un mot au hasard dans le fichier mots.txt
	public String getMot() throws RemoteException {
		String mot = "";
		Random randomGenerator = new Random();
		int lim = randomGenerator.nextInt(10); //nb de mots ds mots.txt
		int i = 0;
		try {
			InputStream inStream = new FileInputStream("mots.txt");
			InputStreamReader inReader = new InputStreamReader(inStream);
			BufferedReader bufReader = new BufferedReader(inReader);
			String ligne;
			
			while((ligne=bufReader.readLine()) != null && i<lim) {
				mot = ligne;
				i++;
			}
			bufReader.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return mot;
	}

}
