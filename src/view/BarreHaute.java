package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Main;
import modele.Personne;

@SuppressWarnings("serial")
public class BarreHaute extends JPanel implements ActionListener {
	private JTextArea zoneAffichage;
	private JButton bmodifier;
	private Fenetre myFenetre;
	private JTextField recherche;
	private JLabel label;

	public BarreHaute(Fenetre f) {
		myFenetre = f;
		this.label = new JLabel("Rechercher");
		this.zoneAffichage = new JTextArea();
		this.bmodifier = new JButton("Modifier");
		bmodifier.addActionListener(this);
		bmodifier.setEnabled(false);
		
		recherche = new JTextField(10);
		recherche.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			/**
			 * A l'appui sur une touche on recherche si un nom, prenom, ou
			 * region coincide avec la saisie
			 */
			public void keyReleased(KeyEvent arg0) {
				rechercheResults();
			}

			public void keyPressed(KeyEvent arg0) {
			}
		});

		this.add(label);
		this.add(recherche);
		this.add(this.zoneAffichage);
		this.add(this.bmodifier);
	
	}

	private void rechercheResults() {
		if (recherche.getText().length() > 0) {
			String pattern = getRecherche().getText().toUpperCase();
			DefaultListModel dlm = new DefaultListModel();
			
			/**
			 * Si le nom, le prenom ou la région d'une personne contient la chaine recherchée on l'ajoute 
			 */
			for (Personne pcurr : Main.arbre.getPersonnes()) {
				if (pcurr.getNom().contains(pattern) || pcurr.getPrenom().toUpperCase().contains(pattern)
						|| pcurr.getRegion().toUpperCase().contains(pattern))
					dlm.addElement(pcurr.getNom());
			}
			myFenetre.getListe().setModel(dlm);

			// Reset de l'affichage des infos de la personne
			myFenetre.getBh().getZoneAffichage().setText(null);
		} else {
			myFenetre.getBb().updateContenuListe();
		}
	}

	public JTextArea getZoneAffichage() {
		return zoneAffichage;
	}

	public void setZoneAffichage(JTextArea zoneAffichage) {
		this.zoneAffichage = zoneAffichage;
	}

	public JButton getBmodifier() {
		return bmodifier;
	}

	public void setBmodifier(JButton bmodifier) {
		this.bmodifier = bmodifier;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modification m = new Modification(myFenetre);
		m.setVisible(true);
	}

	public JTextField getRecherche() {
		return recherche;
	}

	public void setRecherche(JTextField recherche) {
		this.recherche = recherche;
	}

}
