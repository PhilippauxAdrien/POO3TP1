package modele;
import java.util.TreeSet;

public class Arbre {

	private TreeSet<Personne> personnes;
	
	public Arbre() {
		personnes =  new TreeSet<Personne>();
	}

	public TreeSet<Personne> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(TreeSet<Personne> personnes) {
		this.personnes = personnes;
	}

	@Override
	public String toString() {
		return "Arbre [personnes=" + personnes + "]";
	}
	
}
