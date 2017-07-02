package painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

import main.Main;
import data.Landscape;
import data.Paintable;
import data.Player;


/**
 * 
 * Der Painter zeichnet das Bild, welches im JFrame dargestellt wird (das Game)
 *
 */
@SuppressWarnings("serial")
public class Painter extends JComponent {
	
	private long starttime = System.currentTimeMillis();
	private int pause = 1;
	private long fps = 0;
	private int desiredfps=60;
	private Image background = Main.getBackgroundImage();
	private Image texture = Main.getTexture();
	private int repaint = 0;
	private int mapsize = 1400;
	private int mapheight = 700;

	public Painter()
	{
		
	}
	
	
	
	
	public void paint(Graphics g)
	{
			//Das zeichnen auf einem Offscreen-Image sorgt dafür,dass die CPU unter Volllast läuft und die FPS einbricht, die GPU rechnet leider nix
			//schade, sonst hätte man auf vielfache Größe malen lassen ,macht es resizable und die Fluganimation smoother
		
			//BufferedImage offscreen = new BufferedImage(1399,699,BufferedImage.TYPE_INT_ARGB);
			//Graphics2D g2d = (Graphics2D) offscreen.createGraphics();
		
		
			Graphics2D g2d = (Graphics2D)g;
			
			//Kantenglättung macht's schicker
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			//zähler für den FPS-Limiter
			this.repaint += 1;
			long currenttime = System.currentTimeMillis();
		
			
			
			//Landscape
			Landscape landscape = Main.getLandscape();
			int[] xmap = landscape.getXmap();
			int[] ymap = landscape.getYmap();
			//Eckpunkte des Polygons festlegen
			xmap[mapsize]= mapsize;
			xmap[mapsize+1]= 0;
			ymap[mapsize]= mapheight;
			ymap[mapsize+1]=mapheight;
			
			
			
			//hintergrund zeichen
			g2d.drawImage(background, 0, 0, 1400, 700,  this);
		
			
	
			//Objekte zeichnen
			ArrayList<Paintable> paintables = gatherObjects();
			for (Paintable p : paintables) {
				g2d.drawImage(p.getImage(), p.getXpos(), p.getYpos(), this); 
			
			}
		
		
			//Landschaft zeichnen
			if (texture != null) {
				BufferedImage bi = new BufferedImage (	512,	512,	BufferedImage.TYPE_INT_ARGB);
				bi.createGraphics().drawImage(texture, 0,0, null);
				Rectangle2D rectangle = new Rectangle2D.Float(0, 0, 250, 250);
				TexturePaint tp = new TexturePaint(bi, rectangle);
				g2d.setPaint(tp);
				//Polygon zeichnen mit textur
				g2d.fillPolygon(xmap, ymap, 1402);
			}
			
	
			//ein wenig Infos
			
			Player player1 = Main.getPlayer1();
			Player player2 = Main.getPlayer2();
			
			g2d.setColor(new Color(255, 255, 255, 255));
			g2d.drawString("FPS: " + fps, 15, 620);
			g2d.drawString("Kanonenkugeln: " + (Main.getCannonballs1().size() + Main.getCannonballs2().size() + Main.getCannonballs3().size()) , 15, 640);
			g2d.drawString("Rammen: " + Main.getRams().size(), 15, 660);
			
			g2d.setColor(new Color(0,0,0,255));
			g2d.drawString("Winkel: "+player1.getAngle(),15,20);
			g2d.drawString("Energie: "+player1.getPower(),15,40);
			g2d.drawString("Gesundheit: "+player1.getHealth(),15,60);
			if (player1.isCannonlock()) g2d.drawString("Kanone wird geladen",15,80);
			else g2d.drawString("Kanone bereit", 15, 80);
			if (player1.isRamlock()) g2d.drawString("Ramme wird vorbereitet",15,100);
			else g2d.drawString("Ramme bereit", 15, 100);
			g2d.drawString("Siege: " + player1.getWins(),15,120);
			
			g2d.drawString("Winkel: "+player2.getAngle(),1200,20);
			g2d.drawString("Energie: "+player2.getPower(),1200,40);
			g2d.drawString("Gesundheit: "+player2.getHealth(),1200,60);
			if (player2.isCannonlock()) g2d.drawString("Kanone wird geladen",1200,80);
			else g2d.drawString("Kanone bereit", 1200, 80);
			if (player2.isRamlock()) g2d.drawString("Ramme wird vorbereitet",1200,100);
			else g2d.drawString("Ramme bereit", 1200, 100);
			g2d.drawString("Siege: "+ player2.getWins(),1200,120);
			
			
			//wind einzeichnen:
			double wind = Main.getWind();
			
				if (wind>1)	g2d.drawString("Wind :"+"◄◄◄",650,40);
					
				else if (wind>0.5) g2d.drawString("Wind :"+"◄◄",650,40);
				
				else if (wind>0.2) g2d.drawString("Wind :"+"◄",650,40);
					
				else if (wind<0.2 && wind > -0.2) g2d.drawString("Wind :"+"",650,40);
					
				else if (wind <-0.2 && wind > -0.5) g2d.drawString("Wind :"+"►",650,40);
					
				else if (wind <-0.5 && wind > -1) g2d.drawString("Wind :"+"►►",650,40);
				
				else g2d.drawString("Wind :"+"►►►",650,40);
				
				
			//leider halt nicht...	
			//Graphics2D screen = (Graphics2D)g;
			//screen.drawImage(offscreen, 0,0,this.getWidth() , this.getHeight(), this);
			
			
			
		
		
		
		//Framelimiter, da sonst Vollast auf der GPU und >2000fps
		if (currenttime-starttime > 1000) {
			fps = repaint/((currenttime-starttime)/1000);
		
			if ( fps > (desiredfps+3) ) {
				pause++;
			}
			else if ( fps < (desiredfps-3) && pause > 0) {
				pause--;
			}
	
		}
		
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			Main.getGUI().error(e.getMessage());
			e.printStackTrace();
		}
		repaint();
	}
	
	/**
	 * sammelt alle zu zeichnenden Objekte und steckt sie in eine Liste, welche vom Painter abgearbeitet wird
	 * 
	 */
	public ArrayList<Paintable> gatherObjects() {
		ArrayList<Paintable> paintables = new ArrayList<Paintable>();
		paintables.add(Main.getPlayer1());
		paintables.add(Main.getPlayer2());
		paintables.addAll(Main.getCannonballs1());
		paintables.addAll(Main.getCannonballs2());
		paintables.addAll(Main.getCannonballs3());
		paintables.addAll(Main.getRams());
		return paintables;
		
	}


}
