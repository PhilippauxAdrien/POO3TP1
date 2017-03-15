package modele;

import java.io.Serializable;

public class Personne implements Comparable, Serializable{

	private String nom;
	private String prenom;
	private String region;

	public Personne(String nom, String prenom, String region) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.region = region;
	}
	
	public String getNom() {
		return nom;
	}

	public String toString() {
		return "Nom = " + nom + "\nPrenom = " + prenom + "\nRegion = "
				+ region ;
	}
	
	public int compareTo(Object o ){
		return this.nom.compareTo(((Personne) o).getNom());
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
}
