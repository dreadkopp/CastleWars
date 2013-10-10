import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class FirstGUI extends JFrame implements ActionListener
{
	private JButton startbutton;
    @SuppressWarnings("rawtypes")
	private static JComboBox boxAufloesung;
    @SuppressWarnings("rawtypes")
	private static JComboBox boxSpielwelt;
    @SuppressWarnings("rawtypes")
	private static JComboBox boxSpieler;
    private JPanel panel;
    private JLabel Aufloesung;
    private JLabel Spielwelt;
    private JLabel Spieler;
    private JLabel emptylabel;
    private String resolution[] = {"640x480", "800x600", "1024x768", "1280x1024", "1600x900"};
    private String gravity[] = {"Erde", "Mars", "Mond"};
    private String player[] = {"1 Spieler", "2 Spieler"};
    public static int mapsize = 0;
    public static int mapheight = 0;
    public static double anziehung = 0;
    public static int teilnehmer = 0;
    
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public FirstGUI()
    {   
    	super ("CastleWars");
    	this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(1,1));  

        //Elemente erzeugen
        startbutton = new JButton("START");
        boxAufloesung = new JComboBox(resolution);
        boxSpielwelt = new JComboBox(gravity);
        boxSpieler = new JComboBox(player);
        Aufloesung = new JLabel("Auflösung");
        Spielwelt = new JLabel("Spielwelt");
        Spieler = new JLabel("Spieler");
        emptylabel = new JLabel("");
        
        //Listener
        startbutton.addActionListener(this);
        
        //Panel erzeugen auf einem GridLayout
        panel = new JPanel(new GridLayout(3,3));

        //Auf Panel Buttons packen..emptylabel als platzhalter, um nicht ein neues layout erstellen zu müssen.
        panel.add(Aufloesung);
        panel.add(Spielwelt);
        panel.add(Spieler);
        panel.add(boxAufloesung);
        panel.add(boxSpielwelt);
        panel.add(boxSpieler);
        panel.add(emptylabel);
        panel.add(startbutton);

        
        //Panel auf Frame packen 
        this.getContentPane().add(panel);

        this.pack();
        this.setVisible(true);

    }

    public static void main(String[] args)
    {
       new FirstGUI();
    }

    //Das Spiel starten, wenn der startbutton gedrück wird
    @Override
	public void actionPerformed(ActionEvent ae) {
<<<<<<< HEAD
		if (ae.getSource() == this.startbutton) { 
			setValues (); 
			new Game (); 
			System.out.println("starte Spiel"); 
		}
=======
		if (ae.getSource() == this.startbutton) { setValues (); new Game (); System.out.println("starte Spiel"); }
>>>>>>> 002735bfd793fda65f5fda80ae739bedbbeed706
	}
		
    //die Comcoboxen auswerten und dementsprechend werte setzen
	public void setValues() {
		
		
		int indexres = boxAufloesung.getSelectedIndex();
	    int indexgra = boxSpielwelt.getSelectedIndex();
	    int indexpla = boxSpieler.getSelectedIndex();
		if (indexres == 0) {mapsize = 640; mapheight = 480; System.out.println("test");};
		if (indexres == 1) {mapsize = 800; mapheight = 600;};
		if (indexres == 2) {mapsize = 1024; mapheight = 786;};
		if (indexres == 3) {mapsize = 1280; mapheight = 1024;};
		if (indexres == 4) {mapsize = 1600; mapheight = 900;};
		if (indexgra == 0) {anziehung = 1;};
		if (indexgra == 1) {anziehung = 0.16;};
		if (indexgra == 2) {anziehung = 0.33;};
		if (indexpla == 0) {teilnehmer = 1;};
		if (indexpla== 1) {teilnehmer = 2;};
	}
	
}
