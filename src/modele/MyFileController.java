package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import view.Fenetre;

public class MyFileController {

	private File file;
	private FileWriter fw;
	private Fenetre fen;

	public MyFileController(Fenetre f) throws IOException {
		fen = f;
	}

	public void ecrire(String fileName, TreeSet<Personne> tree) {
		ObjectOutputStream oos = null;
		try {

			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			Iterator<Personne> iterator = tree.iterator();

			while (iterator.hasNext()) {
				Personne next = iterator.next();
				oos.writeObject(next);
			}
			oos.close();
			JOptionPane.showMessageDialog(fen, "Bien enregistré dans le fichier.");

		} catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

	public TreeSet<Personne> importer(String filename, TreeSet<Personne> tree) {
		ObjectInputStream ois = null;
		tree.clear();
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			Iterator<Personne> iterator = tree.iterator();
			Personne p;
			
			while (( p = (Personne) ois.readObject()) != null) {
				tree.add(p);
			}

			JOptionPane.showMessageDialog(fen, "Fichier " + filename + " bien ouvert");
		} catch (Exception e) {
			try {
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return tree;
	}

}
