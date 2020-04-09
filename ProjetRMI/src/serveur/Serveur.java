package serveur;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import modele.MorpionServerImpl;
import modele.PenduServerImpl;


public class Serveur {
	public static void main (String[] argv) {
		//lancement du serveur morpion
		try {
			int port = 8000;
			
			LocateRegistry.createRegistry(port);
			Naming.rebind("rmi://localhost:" + port + "/morpion", new MorpionServerImpl());
			System.out.println("Serveur morpion prêt !");
		} catch (Exception e) {
			System.out.println("Echec serveur morpion " + e);
		}
		
		//lancement du serveur pendu
		try {
			int port = 8001;
			
			LocateRegistry.createRegistry(port);
			Naming.rebind("rmi://localhost:" + port + "/pendu", new PenduServerImpl());
			System.out.println("Serveur pendu prêt !");
		} catch (Exception e) {
			System.out.println("Echec serveur pendu " + e);
		}
	}
}
