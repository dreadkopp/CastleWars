import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

@SuppressWarnings("serial")
public class Game extends JFrame  {
	
	
	//elemente
	
    static Landscape land; 
   	Image hintergrund;
   	static Player player1;
   	static Player player2;
   	public static int playerposarray[] = null;
   	static boolean running = true;
   	public static int xposplayer1 = 0;
   	public static int xposplayer2 = 0;
   	private int player1counter = 0;
   	private int player2counter = 0;
   	private long currenttime =System.currentTimeMillis();
   	private long lasttime = currenttime;
   	private long totaltime = 0;
   	public static int wind = (int)Math.random()*100-50;
	public static Graphics2D g2d;
	
		
		//hier krachts ;)
		public static void dealdamageplayer1 (int damage) { player1.sethealth (damage);};
		public static void dealdamageplayer2 (int damage) { player2.sethealth (damage);};
	
		//startet eine neue runde
		public static void newround () {
			Random rnd = new Random ();
			xposplayer1 = (FirstGUI.mapsize/2) - rnd.nextInt()*(FirstGUI.mapsize/2);
	        xposplayer2 = (FirstGUI.mapsize/2) + rnd.nextInt()*(FirstGUI.mapsize/2);
	        land = new Landscape ();
	        playerposarray = land.ymap;
	        player1.health = 100;
	        player2.health = 100;
	        running = true;
	        
		}
		
<<<<<<< HEAD
=======
		
>>>>>>> 002735bfd793fda65f5fda80ae739bedbbeed706
	
		public Game () {
			super ("CastleWars");
			this.setSize(FirstGUI.mapsize, FirstGUI.mapheight);
			this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setIgnoreRepaint( true );
	        this.addKeyListener(new KeyAdapter () {
	        	//Keylistener zur Steuerung TODO:keylistener zum laufen bringen
	    		public void keyPressed (KeyEvent event)
	    			
	    			{if ( event.getKeyCode() == KeyEvent.VK_ESCAPE) running = false; //ESC;
	    			if ( event.getKeyCode() == KeyEvent.VK_SPACE && (player1.rate < player1counter))	player1.fire(); player1counter = 0;//leer
	    			if ( event.getKeyCode() == KeyEvent.VK_UP && player1.speed0 < 100)  player1.speed0 = player1.speed0 +1; //oben
	    			if ( event.getKeyCode() == KeyEvent.VK_LEFT && player1.angle < 180) player1.angle = player1.angle +1; //links
	    			if ( event.getKeyCode() == KeyEvent.VK_RIGHT && player1.angle > 0) player1.angle = player1.angle -1; //rechts
	    			if ( event.getKeyCode() == KeyEvent.VK_DOWN && player1.speed0 > 0)  player1.speed0 = player1.speed0 -1; //unten
	    			if ( event.getKeyCode() == KeyEvent.VK_W && player2.speed0 < 100)  player2.speed0 = player2.speed0 +1; //W
	    			if ( event.getKeyCode() == KeyEvent.VK_A && player2.angle < 180) player2.angle = player2.angle +1; //A
	    			if ( event.getKeyCode() == KeyEvent.VK_S && player2.speed0 > 0)  player2.speed0 = player2.speed0 -1; //S
	    			if ( event.getKeyCode() == KeyEvent.VK_D && player2.angle > 0) player2.angle = player2.angle -1; //D
	    			if ( event.getKeyCode() == KeyEvent.VK_ENTER && (player2.rate < player2counter))  player2.fire(); player2counter = 0; //Enter
	    			}
	        });
	        
	        // Create canvas for painting...

	        Canvas canvas = new Canvas();
	        canvas.setIgnoreRepaint( true );
	        canvas.setSize( FirstGUI.mapsize, FirstGUI.mapheight);

	        JPanel gamepanel = new JPanel(new GridLayout (1,1));
	        gamepanel.add(canvas);
       
	        this.getContentPane().add(gamepanel);
	        this.pack();
	        this.setVisible(true);
	        
	        
	        //Bufferung realisieren

	        canvas.createBufferStrategy(2);
	        BufferStrategy buffer = canvas.getBufferStrategy();



	        // Grafikkonfiguration (???)

	        GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice gd = ge.getDefaultScreenDevice();
	        GraphicsConfiguration gc = gd.getDefaultConfiguration();

	        //hintergrund (buffer)

	        BufferedImage bi = gc.createCompatibleImage( FirstGUI.mapsize, FirstGUI.mapheight );

	        // Renderobjekte
	             
	        Random rnd = new Random ();
	        xposplayer1 = (FirstGUI.mapsize/2) - rnd.nextInt()*(FirstGUI.mapsize/2);
	        xposplayer2 = (FirstGUI.mapsize/2) + rnd.nextInt()*(FirstGUI.mapsize/2);
	        land = new Landscape ();
	        playerposarray = land.ymap;
	        player1 = new Player (xposplayer1);
	        player2 = new Player (xposplayer2);
	        //if (FirstGUI.teilnehmer == 1) player2 = new AI;
	        Graphics graphics = null;
	        Graphics2D g2d = null;
	        Color background = Color.BLACK;
	                         

	        while( running ) {
	          try {
	        	  
	        	//counter zählen lassen
	        	lasttime = currenttime;
	        	currenttime = System.currentTimeMillis();
	        	totaltime += (currenttime - lasttime);
<<<<<<< HEAD
	        	if (totaltime > 100) totaltime -= 100; player1counter += 1; player2counter += 1;
	        	
	        	//schauen, ob noch alle leben, wenn jemand tot, neue runde
	        	
	        	if (player1.health <= 0 ) running = false; player2.wins += 1; new roundpopup ();
	        	if (player2.health <= 0 ) running = false; player1.wins += 1; new roundpopup ();
	        	
	        	//schauen, ob jemand 5 wins hat, und somit aufleveln kann
	        	
	        	if (player1.wins == 5) running = false; new Levelup (1); player1.wins -= 5;
	        	if (player2.wins == 5) running = false; new Levelup (2); player2.wins -= 5;
=======
	        	if (totaltime > 100) {totaltime -= 100; player1counter += 1; player2counter += 1;};
	        	
	        	//schauen, ob noch alle leben, wenn jemand tot, neue runde
	        	
	        	if (player1.health <= 0 ) {running = false; player2.wins += 1; new roundpopup ();};
	        	if (player2.health <= 0 ) {running = false; player1.wins += 1; new roundpopup ();};
	        	
	        	//schauen, ob jemand 5 wins hat, und somit aufleveln kann
	        	
	        	if (player1.wins == 5) {running = false; new Levelup (1); player1.wins -= 5;};
	        	if (player2.wins == 5) {running = false; new Levelup (2); player2.wins -= 5;};
>>>>>>> 002735bfd793fda65f5fda80ae739bedbbeed706
	        	
	            //hintergrund löschen
	            
	            g2d = bi.createGraphics();
	            g2d.setColor( background );
	            g2d.fillRect( 0, 0, FirstGUI.mapsize, FirstGUI.mapheight );
                                  
	            // zeichne das spiel
<<<<<<< HEAD
	            
	            //Hintergrund laden und zeichnen  &&  fallunterscheidung nach spielewelt
	    		if (FirstGUI.anziehung == 1) hintergrund = Toolkit.getDefaultToolkit().getImage("background1.jpg");
	    		if (FirstGUI.anziehung== 0.16) hintergrund = Toolkit.getDefaultToolkit().getImage("background2.jpg");
	    		if (FirstGUI.anziehung == 0.33) hintergrund = Toolkit.getDefaultToolkit().getImage("background3.jpg");
=======

	            Class<? extends JFrame> j = this.getClass();
	            
	            //Hintergrund laden und zeichnen  &&  fallunterscheidung nach spielewelt
	    		if (FirstGUI.anziehung == 1) {hintergrund = Toolkit.getDefaultToolkit().createImage(j.getResource("/background1.jpg"));};//Toolkit.getDefaultToolkit().getImage("background1.jpg");
	            //Hintergrund laden und zeichnen  &&  fallunterscheidung nach spielewelt
	    		if (FirstGUI.anziehung == 1) {hintergrund = Toolkit.getDefaultToolkit().getImage("background1.jpg");};
	    		if (FirstGUI.anziehung== 0.16) {hintergrund = Toolkit.getDefaultToolkit().getImage("background2.jpg");};
	    		if (FirstGUI.anziehung == 0.33) {hintergrund = Toolkit.getDefaultToolkit().getImage("background3.jpg");};
>>>>>>> 002735bfd793fda65f5fda80ae739bedbbeed706
	    		g2d.drawImage(hintergrund, 0, 0, FirstGUI.mapsize, FirstGUI.mapheight, this);
	            player1.paint(g2d);
	            player2.paint(g2d);
	    		land.paintMap(g2d);
	            
	            
                                   
	            // Doppelpufferung

	            graphics = buffer.getDrawGraphics();
	            graphics.drawImage( bi, 0, 0, null );
	            if( !buffer.contentsLost() )
	              buffer.show();
                                    
	            //hier dem system etwas zeit geben... 

	            Thread.yield();
	          } finally {

	            // Ressourcen freigeben

	            if( graphics != null ) 
	              graphics.dispose();
	            if( g2d != null ) 
	              g2d.dispose();
	        }
	          
	          
	      }
		}

}

