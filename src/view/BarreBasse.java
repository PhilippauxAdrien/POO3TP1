package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.Main;
import modele.Personne;

@SuppressWarnings("serial")
public class BarreBasse extends JPanel implements ActionListener, KeyListener {

	private JLabel mess;
	private JTextField saisie;
	private JButton bajout;
	private JButton bsuppr;
	private Fenetre myFenetre;
	private ButtonGroup btngrp;
	private JRadioButton delOne, delMutiple;

	public BarreBasse(Fenetre f) {
		myFenetre = f;

		mess = new JLabel("Nom : ");
		saisie = new JTextField(20);
		bajout = new JButton("Ajouter");
		bsuppr = new JButton("Supprimer");
		bajout.setEnabled(false);
		bsuppr.setEnabled(false);
		
		btngrp = new ButtonGroup();
		delMutiple = new JRadioButton("Suppression multiple");
		delOne = new JRadioButton("Suppression simple", true);
		
		delOne.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				myFenetre.getListe().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
		});
		delMutiple.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				myFenetre.getListe().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}
		});
		
		btngrp.add(delMutiple);
		btngrp.add(delOne);
		bajout.addActionListener(this);
		saisie.addKeyListener(this);
		saisie.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				changed();
			}

			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			public void changedUpdate(DocumentEvent e) {
				changed();
			}
		});

		bsuppr.addActionListener(this);

		this.add(mess);
		this.add(saisie);
		this.add(bajout);
		this.add(bsuppr);
		this.add(delOne);
		this.add(delMutiple);
	}

	private void ajouterUnePersonne() {
		System.out.println("Ajout d'une personne.");

		Personne p = new Personne(this.saisie.getText().toUpperCase(), "", "");
		Main.arbre.getPersonnes().add(p);
		updateContenuListe();
		System.out.println(Main.arbre);

		this.saisie.setText("");
	}

	private void supprimer() {
		Iterator<Personne> iterator = Main.arbre.getPersonnes().iterator();

		if (delOne.isSelected()) {
			/**
			 * Suppression d'un item seulement
			 */
				System.out.println("Suppression de l'item de nom : " + myFenetre.getListe().getSelectedValue());

				while (iterator.hasNext()) {
					Personne next = iterator.next();
					if (next.getNom().equals(myFenetre.getListe().getSelectedValue())) {
						iterator.remove();
						break;
					}
				}
				updateContenuListe();
			
		} else {
			/**
			 * Suppression de plusieurs items si suppresion multiple sélectionnée (et CTRL + clic sur
			 * items de la liste)
			 */

			// Récupération des items sélectionnés
				Object[] names = myFenetre.getListe().getSelectedValues();
				
				/**
				 * Affichage des items a supprimer
				 */
				System.out.print("Suppression des items de noms : ");
				for (Object o : names) {
					System.out.print(o + " , ");
				}
				System.out.println();
				
				// Pour chaque item on cherche dans l'arbre la personne
				// a supprimer
				for (Object name : names) {
					for (Iterator<Personne> iter = Main.arbre.getPersonnes().iterator(); iter.hasNext();) {
						Personne next = iter.next();
						if (next.getNom().equals(name)) {
							iter.remove();
							break;
						}
					}
				}
				// Actualisation de la liste
				updateContenuListe();
			
		}
		getBsuppr().setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bajout)) 
			ajouterUnePersonne();
		else if (e.getSource().equals(bsuppr)) 
			supprimer();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateContenuListe() {
		DefaultListModel dlm = new DefaultListModel();

		for (Personne pcurr : Main.arbre.getPersonnes()) {
			dlm.addElement(pcurr.getNom());
		}
		myFenetre.getListe().setModel(dlm);

		// Reset de l'affichage des infos de la personne
		myFenetre.getBh().getZoneAffichage().setText(null);
	}

	public JLabel getMess() {
		return mess;
	}

	public void setMess(JLabel mess) {
		this.mess = mess;
	}

	public JTextField getSaisie() {
		return saisie;
	}

	public void setSaisie(JTextField saisie) {
		this.saisie = saisie;
	}

	public JButton getBajout() {
		return bajout;
	}

	public void setBajout(JButton bajout) {
		this.bajout = bajout;
	}

	public JButton getBsuppr() {
		return bsuppr;
	}

	public void setBsuppr(JButton bsuppr) {
		this.bsuppr = bsuppr;
	}

	private void changed() {
		if (saisie.getText().length() > 0) {
			bajout.setEnabled(true);
		} else {
			bajout.setEnabled(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			getBajout().doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
