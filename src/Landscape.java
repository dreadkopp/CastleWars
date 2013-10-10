/** Diese Klasse implementiert alle für die Landschaft wichtigen Funktionen */

import java.awt.*;
import java.util.*;

public class Landscape {
	// Deklaration der Variablen
	private int start;			// Gibt die Starthöhe der Landschaft vor
	private int mapheight = FirstGUI.mapheight;
	private int mapsize = FirstGUI.mapsize;
	
	// Variable zur Erzeugung einer Zufallszahl
	Random rnd = new Random ();
	//erzeugt randomwerte zur levelgeneration
	int a =(rnd.nextInt() % (mapheight/2));
	int b =(rnd.nextInt() % (mapheight/2));
	int c =(rnd.nextInt() % (mapheight/2));
	
	int d = Math.abs(rnd.nextInt() % 10);
	int e = Math.abs(rnd.nextInt() % 10);
	int f = Math.abs(rnd.nextInt() % 10);

	double x =  d * (mapsize * 0.000001);
	double y =  e * (mapsize * 0.000001);
	double z =  f * (mapsize * 0.000001);

	
	
	// Arrays zum Speichern der Koordinaten
	public int [] ymap;
    private int [] xmap;
	
	//Construktormethode

	public Landscape ()
	{
		// Initialisierung der Arraygröße von map
		ymap = new int [mapsize+10];
		xmap = new int [mapsize+10];
		
		// Aufruf der Methode generateLandscape
		generateLandscape ();
	
	}
		

	public void generateLandscape ()
	{	//die landschaft wird zufällig gezeichnet.
		

		// Initialisierung des Startwertes, züfällig...
		start = Math.abs((mapheight-(mapsize/10)) + (rnd.nextInt() % 50));

		// Speichern des Startwertes an der ersten Stelle des Arrays
		ymap [0] = start;

	
		
				
			// Loop zur Initialisierung aller anderen Arrayfelder
			for (int i = 1; i <= mapsize; i ++)
			{
				
				
				//werte vor sinus= je größer, desto höhere amplituden, werte in der klammer = je kleiner, desto gestreckter 
				ymap[i] = (int)(a*Math.sin(x*i)+b*Math.cos(y*i)+c*Math.sin(z*i)+ymap[0]*5)/5; 
				xmap[i] = i;
			}
		
	}

	//Landscape zeichnen
	public void paintMap (Graphics g)
	{
		
		
		// Definition der Farbe...eigentlich wollen wir hier texturen...
		g.setColor ( new Color(0x32, 0x45, 0x62));
		
		
		//Eckpunkte des Polygons festlegen
		xmap[mapsize]= mapsize;
		xmap[mapsize+1]= 0;
		ymap[mapsize]=mapheight;
		ymap[mapsize+1]=mapheight;
		
		//Levelebene zeichnen als Polygon
		g.fillPolygon(xmap, ymap, mapsize+2);
	
	}
	
	
		

}