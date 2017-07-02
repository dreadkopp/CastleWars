package data;

import java.util.Random;

public class Landscape {

	/**
	 * 
	 */
	private int start; // Gibt die Starthöhe der Landschaft vor
	private int mapheight = 0;
	private int mapsize = 0;
	// Variable zur Erzeugung einer Zufallszahl
	Random rnd = new Random();

	private int a = 0;
	private int b = 0;
	private int c = 0;
	private int d = 0;
	private int e = 0;
	private int f = 0;

	private double x = 0;
	private double y = 0;
	private double z = 0;

	// Arrays zum Speichern der Koordinaten
	private int[] ymap;
	private int[] xmap;

	/**
	 * Konstruktor der Landschaft, erzeugt sich ein paar Zufallszahlen...-
	 */

	public Landscape() {
		mapsize = 1400;
		mapheight = 700;
		a = (rnd.nextInt() % (mapheight / 2));
		b = (rnd.nextInt() % (mapheight / 2));
		c = (rnd.nextInt() % (mapheight / 2));

		d = Math.abs(rnd.nextInt() % 10);
		e = Math.abs(rnd.nextInt() % 10);
		f = Math.abs(rnd.nextInt() % 10);

		x = d * (mapsize * 0.000001);
		y = e * (mapsize * 0.000001);
		z = f * (mapsize * 0.000001);
		// Initialisierung der Arraygröße von map
		ymap = new int[mapsize + 10];
		xmap = new int[mapsize + 10];

		// Aufruf der Methode generateLandscape
		generateLandscape();

	}

	/**
	 * Konstruktor für Netzwerkspiel
	 * 
	 * @param xmap
	 * @param ymap
	 */
	public Landscape(int[] xmap, int[] ymap) {
		this.xmap = xmap;
		this.ymap = ymap;

	}

	/**
	 * ...mithilfe welcher die Landschaft generiert wird
	 */

	public void generateLandscape() {

		// Initialisierung des Startwertes, zufällig...
		start = Math.abs((mapheight - (mapsize / 10)) + (rnd.nextInt() % 50));

		// Speichern des Startwertes an der ersten Stelle des Arrays
		ymap[0] = start;

		// Loop zur Initialisierung aller anderen Arrayfelder
		for (int i = 1; i <= mapsize; i++) {

			// werte vor sinus= je größer, desto höhere amplituden, werte in der
			// klammer = je kleiner, desto gestreckter
			ymap[i] = (int) (a * Math.sin(x * i) + b * Math.cos(y * i) + c
					* Math.sin(z * i) + ymap[0] * 5) / 5;
			xmap[i] = i;

		}

	}

	public int[] getXmap() {
		return this.xmap;
	}

	public int[] getYmap() {
		return this.ymap;
	}

}
