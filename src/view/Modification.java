package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.Main;
import modele.Personne;

@SuppressWarnings("serial")
public class Modification extends JDialog implements ActionListener, WindowListener, KeyListener{

	private JLabel lNom, lPrenom, lRegion;
	private JTextField tfNom, tfPrenom, tfRegion;
	private JButton bsave, bannuler;
	private Fenetre myFenetre;
	
	public Modification(Fenetre f) {

		myFenetre = f;
		this.setTitle("Modification d'une personne");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0, 2));
		bsave = new JButton("Enregistrer");

		bannuler = new JButton("Annuler");
		bannuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Annulation de la modif");
				Modification.this.dispose();
			}
		});

		if (myFenetre.getPersonne() != null) {
			lNom = new JLabel("Nom");
			tfNom = new JTextField(myFenetre.getPersonne().getNom());

			lPrenom = new JLabel("Prenom");
			tfPrenom = new JTextField(myFenetre.getPersonne().getPrenom());

			lRegion = new JLabel("Region");
			tfRegion = new JTextField(myFenetre.getPersonne().getRegion());
			
			tfNom.addKeyListener(this);
			tfPrenom.addKeyListener(this);
			tfRegion.addKeyListener(this);
		}
			bsave.addActionListener(this);

			this.add(lNom);
			this.add(tfNom);
			this.add(lPrenom);
			this.add(tfPrenom);
			this.add(lRegion);
			this.add(tfRegion);
		
		this.add(bsave);
		this.add(bannuler);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Enregistrement des modifs");
		/*
		 * Suppression de la personne dans l'arbre
		 */
		Iterator<Personne> iter = Main.arbre.getPersonnes().iterator();

		while (iter.hasNext()) {
			Personne next = iter.next();
			if (next.equals(myFenetre.getPersonne())) {
				iter.remove();
				break;
			}
		}
		/*
		 * Modif de la personne
		 */
		myFenetre.getPersonne().setNom(tfNom.getText().toUpperCase());
		myFenetre.getPersonne().setPrenom(tfPrenom.getText().toUpperCase());
		myFenetre.getPersonne().setRegion(tfRegion.getText().toUpperCase());
		/*
		 * ajout de la personne dans l'arbre
		 */
		Main.arbre.getPersonnes().add(myFenetre.getPersonne());
		myFenetre.getBb().updateContenuListe();
		myFenetre.getListe().setSelectedValue(myFenetre.getPersonne().getNom(), true);

		this.dispose();
		myFenetre.getBh().getBmodifier().setEnabled(false);

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			bsave.doClick();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			bannuler.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
