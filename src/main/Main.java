package main;

import java.io.IOException;

import modele.Arbre;
import view.Fenetre;

public class Main {
	public static Arbre arbre = new Arbre();

	public static void main(String[] args) throws IOException {
		System.out.println("Lancement");

		Fenetre f = new Fenetre();
		f.setVisible(true);

	}

}
