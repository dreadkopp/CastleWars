import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class flyingball {
	
	private int speed0 = 0;
	private int angle = 0;
	private int damage = 0;
	private Image cannonball = null;
	private int xpos = 0;
	private int ypos = 0;
	private double C = 0.23;
	private int mapsize = FirstGUI.mapsize;
	private double g = FirstGUI.anziehung * mapsize * 0.005;
	private int m = 20;
	private int [] ymap = new int [mapsize + 10];
	private int [] xmap = new int [mapsize + 10];
	private int castlewidth = FirstGUI.mapsize / 30;
	private int castleheight = FirstGUI.mapheight / 10;
	
	
	public flyingball (int speed0c, int anglec, int damagec, Image cannonballc, int xposc, int yposc) {
		
		speed0 =  speed0c;
		angle = anglec;
		damage = damagec;
		cannonball = cannonballc;
		xpos = xposc;
		ypos = yposc;
		generatemaps ();
		//TODO: geht das so?
		letitfly(Game.g2d);
		
	}

	private void generatemaps() {
		
		
		//baut die flugbahn auf
		
		double speedx=speed0*Math.cos(angle);
		double speedy=speed0*Math.sin(angle);
		double accy = 0;
		double accx = 0;
		double forcex = 0;
		double forcey = 0;
		double forcewindx = 0;
		double forcewindy = 0;
		double forcewind = 0;
		double speed = 0;
		
		
		
		//delta t= 1 =i
		
		for (int i = 0; i < FirstGUI.mapsize && i >= 0; i++) {
			speed = Math.sqrt(speedx*speedx + speedy*speedy);
		
		
			forcewind = C * (speed*speed);
				
		
			forcewindx = -speedx*(forcewind/speed);
			forcewindy = -speedy*(forcewind/speed);
		
		
			forcex = forcewindx;
			forcey = -m * g + forcewindy;
	
		
			accy = forcex / m;
			accx = forcey / m;
	
		
			speedy = speedy+accy*i;
			speedx = speedx+accx*i;
	
		
			xmap[i]= xmap[i]+(int)(speedx+ Game.wind )*i;
			ymap[i]= ymap[i]+(int)speedy*i;
		}
	}
		
	//lässt die kugel die bahn entlang fliegen, sofern erlaubt
	private void letitfly(Graphics g) {
		for (int i = 0; xpos > 0 && xpos < FirstGUI.mapsize && ypos > Game.playerposarray[xpos]; i++) {
			//TODO: bildgröße resizen
			//Rechteck um grobe eckpunkte der burgen legen und prüfen ob x,y in diesem rechteck ist
			if (xpos > Game.xposplayer1 && xpos < (Game.xposplayer1 + castlewidth) && ypos > Game.playerposarray[xpos] && ypos < (Game.playerposarray[xpos]+ castleheight)) Game.dealdamageplayer1(damage); xpos = -20;
			if (xpos > Game.xposplayer2 && xpos < (Game.xposplayer2 + castlewidth) && ypos > Game.playerposarray[xpos] && ypos < (Game.playerposarray[xpos]+ castleheight)) Game.dealdamageplayer2(damage); xpos = -20;
			g.setColor(Color.BLACK);
			g.drawImage(cannonball, xpos, ypos, null);
			//g.drawOval(xpos, ypos, mapsize/100, mapsize/100);
			xpos += xmap[i];
			ypos += ymap[i];
			
			
			
		}
	};
}
