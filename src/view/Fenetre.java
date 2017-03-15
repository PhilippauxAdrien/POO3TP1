package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import main.Main;
import modele.MyFileController;
import modele.Personne;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements WindowListener, MouseListener{
	
	public static boolean listFocused;
	private BarreHaute bh;
	private BarreBasse bb;
	private JList liste;
	private Personne personne;
	private Fenetre me;
	
	public Fenetre() throws IOException {
		me = this;
		this.addWindowListener(this);
		this.setTitle("TP1 gestion de personnes");
		this.setSize(900,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.bh = new BarreHaute(this);
		this.liste = new JList();
		this.bb = new BarreBasse(this);
		getBb().getSaisie().requestFocus();
		
		chargement();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu m1 = new JMenu("Actions");
		JMenuItem mi1 = new JMenuItem("Ajouter une personne");
		JMenuItem mi2 = new JMenuItem("Enregistrer dans un fichier");
		JMenuItem mi3 = new JMenuItem("Importer depuis fichier");
		JMenuItem mi4 = new JMenuItem("Quitter");
		
		m1.add(mi1);
		m1.add(mi2);
		m1.add(mi3);
		m1.add(mi4);
		menuBar.add(m1);
		
		/**
		 * Ajout d'une perssonne
		 */
		mi1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				AjoutPersonne m = new AjoutPersonne(me);
				m.setVisible(true);
			}
		});
		
		/**
		 * Enregistrement dans un fichier
		 */
		mi2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String filename;
				try {
				      JFileChooser c = new JFileChooser();
				      int choix = c.showSaveDialog(me);
				      if (choix == JFileChooser.APPROVE_OPTION) {
				        filename = c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName();
				      }
				      else{
				    	  	filename = null;
				      }
				      
					MyFileController mfc = new MyFileController(me);
					if (filename != null && !filename.equals(""))
						mfc.ecrire(filename, Main.arbre.getPersonnes());
					else
						JOptionPane.showMessageDialog(me, "La chaine rentrée est vide ou incorrecte");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		/**
		 * Importer depuis un fichier 
		 */
		mi3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String filename;
				try {
				     JFileChooser c = new JFileChooser();
				     int choix = c.showOpenDialog(me);
				      if (choix == JFileChooser.APPROVE_OPTION) {
					      filename = c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName();
				      }
				      else{
				    	  filename = null;
				      }
				      
					MyFileController mfc = new MyFileController(me);
					if (filename != null && !filename.equals("")) {
						mfc.importer(filename, Main.arbre.getPersonnes());
						me.getBb().updateContenuListe();
					} else
						JOptionPane.showMessageDialog(me, "La chaine rentrée est vide ou incorrecte");
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		
		/**
		 * Quitter le programme
		 */
		mi4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		this.liste.addMouseListener(this);
		this.liste.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE)
					System.out.println("suppr");
					bb.getBsuppr().doClick();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	
		getListe().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.setLayout(new BorderLayout());
		this.add(this.bh, BorderLayout.NORTH);
		this.add(this.liste, BorderLayout.CENTER);
		this.add(this.bb, BorderLayout.SOUTH);
		this.setJMenuBar(menuBar);
	}

	public void chargement() throws IOException{
		MyFileController mfc = new MyFileController(this);
		mfc.importer("C:/Users/adrie/Documents/IG2I/POO/TP1/test.txt", Main.arbre.getPersonnes());
		getBb().updateContenuListe();
	}
	
	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public JList getListe() {
		return liste;
	}

	public void setListe(JList liste) {
		this.liste = liste;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);		
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
	public void mouseClicked(MouseEvent e) {
		getBb().getBsuppr().setEnabled(true);
		getBh().getBmodifier().setEnabled(true);
		
		for(Personne p : Main.arbre.getPersonnes()){
			if(p.getNom().equals(liste.getSelectedValue())){
				personne = p;
				bh.getZoneAffichage().setText(personne.toString());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public BarreHaute getBh() {
		return bh;
	}

	public void setBh(BarreHaute bh) {
		this.bh = bh;
	}

	public BarreBasse getBb() {
		return bb;
	}

	public void setBb(BarreBasse bb) {
		this.bb = bb;
	}
	
}
