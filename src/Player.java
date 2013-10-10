import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;


public class Player {
	
	public int health = 100;
	public int angle = 90;
	public int speed0 = 0;
	public int cannontype = 1;
	public int rate = 30;
	private int damage = 0;
	public int wins = 0;
	private int counterpos = 0;
	public int xpos = 0;
	public int ypos = Game.playerposarray[xpos];
	Image castle;
	Image cannonball;
	Image cannon;
	flyingball ball;
	private int cannonypos = ypos+FirstGUI.mapsize/5;
	public int castleheight = FirstGUI.mapheight/10;
	public int castlewidth = FirstGUI.mapsize/30;
	
	//Konstruktor
	
	public Player (int xposplayer) {
		xpos = xposplayer;
		if (xpos <= FirstGUI.mapsize/2) counterpos = 20;
		else counterpos = FirstGUI.mapsize/2 + 20;
		}
	
	//Setter
	
	public void sethealth (int schaden) { this.health -= schaden; }; 
	
	//zeichnen
	public void paint (Graphics2D g) {
		if (health<100 && health>=50) {castle = Toolkit.getDefaultToolkit().getImage("castle50-100.png");};
		if (health<50 && health>=20) {castle = Toolkit.getDefaultToolkit().getImage("castle20-50.png");};
		if (health<20 && health>0) {castle = Toolkit.getDefaultToolkit().getImage("castle0-20.png");};
		if (health<=0) {castle = Toolkit.getDefaultToolkit().getImage("destroyedcastle.png");};
		if (cannontype == 1) {cannon = Toolkit.getDefaultToolkit().getImage("cannon1.png");
							  cannonball = Toolkit.getDefaultToolkit().getImage("cannonball1.png");
							  damage = 10;
							  rate = 30;};
		if (cannontype == 2) {cannon = Toolkit.getDefaultToolkit().getImage("cannon2.png");
							  cannonball = Toolkit.getDefaultToolkit().getImage("cannonball2.png");
							  damage = 20;
							  rate = 35;};
		if (cannontype == 3) {cannon = Toolkit.getDefaultToolkit().getImage("cannon3.png");
							  cannonball = Toolkit.getDefaultToolkit().getImage("cannonball3.png");
							  damage = 30;
							  rate = 40;};
							  
		//Burg malen TODO: Burg und Kanone resizen 
		g.setColor (Color.gray);
		g.drawImage(castle, xpos, ypos, null);
		//werte anzeigen
		g.drawString(""+angle, counterpos, 10);
		g.drawString(""+speed0, counterpos, 30);
		g.drawString(""+health, counterpos, 50);
		g.drawString(""+wins, counterpos, 70);
		g.rotate(Math.toRadians(angle));
		g.drawImage(cannon, xpos, cannonypos, null);
		//TODO: rotate???
		
	}
	
	//kugeln abfeuern
	public void fire () {
		
		ball = new flyingball (speed0, angle, damage, cannonball, xpos, cannonypos);
		
	}

	}
