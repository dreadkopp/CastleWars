package main;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

import Network.Client;
import Network.Host;
import data.Cannonball1;
import data.Cannonball2;
import data.Cannonball3;
import data.Landscape;
import data.Player;
import data.Ram;
import engine.Demo;
import engine.Engine;
import gui.Gui;

/**
 * 
 * @author arne Hier liegen alle relevanten Objekte und das Programm wird
 *         initiiert
 */
public class Main {

	private static ArrayList<Cannonball1> cannonballs1 = new ArrayList<Cannonball1>();
	private static ArrayList<Cannonball2> cannonballs2 = new ArrayList<Cannonball2>();
	private static ArrayList<Cannonball3> cannonballs3 = new ArrayList<Cannonball3>();
	private static ArrayList<Ram> rams = new ArrayList<Ram>();
	private static Player player1 = new Player(100, 5000, 1);
	private static Player player2 = new Player(100, 5000, 2);
	private static double wind;
	private static Landscape landscape = new Landscape();
	private static Image cannonball1Image = getImage("cannonballlevel1.png");
	private static Image cannonball2Image = getImage("cannonballlevel2.png");
	private static Image cannonball3Image = getImage("cannonballlevel3.png");
	private static Image backgroundImage = getImage("background.jpg");
	private static Image texture = getImage("earthtexture.jpg");
	private static Image castlefullhealthImage = getImage("castlenodamage.png");
	private static Image castlemediumhealthImage = getImage("castlelightdamage.png");
	private static Image castlelowhealthImage = getImage("castleheavydamage.png");
	private static Image ramImageLeft = getImage("ramleft.png");
	private static Image ramImageRight = getImage("ramright.png");
	private static Engine Engine;
	private static Demo Demo;
	private static Gui GUI;
	private static Host HOST;
	private static Client CLIENT;

	private static Thread engineThread;
	private static Thread demoThread;
	private static Semaphore lock = new Semaphore(1);
	private static Semaphore wait = new Semaphore(1);

	public static Semaphore getWait() {
		return wait;
	}

	private static boolean host = true;
	private static boolean connected = false;

	public static boolean isConnected() {
		return connected;
	}

	public static void setConnected(boolean connected) {
		Main.connected = connected;
	}

	public static boolean isHost() {
		return host;
	}

	public static void setHost(boolean host) {
		Main.host = host;
	}

	/**
	 * Erstellt die beiden Threads und startet die GUI
	 * 
	 */
	public static void main(String[] args) {

		GUI = new Gui();
		new Thread(GUI).start();

	}

	/**
	 * startet die Engine
	 */
	public static void startEngine(boolean demo) {
		Engine = new Engine();
		Engine.setDemo(demo);
		engineThread = new Thread(Engine);
		engineThread.start();

	}

	/**
	 * startet die Demo
	 */
	public static void startDemo() {
		Demo = new Demo();
		demoThread = new Thread(Demo);
		demoThread.start();
	}

	/**
	 * startet die Netzwerkschittstelle des Multiplayer
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void startSender() throws IOException, ClassNotFoundException {
		HOST = new Host();
		Thread senderThread = new Thread(HOST);
		senderThread.start();

	}

	public static void startReceiver() throws IOException,
			ClassNotFoundException {
		CLIENT = new Client();
		Thread receiverThread = new Thread(CLIENT);
		receiverThread.start();
	}

	@SuppressWarnings("deprecation")
	public static void stopEngine() {
		engineThread.stop();
		demoThread.stop();
	}

	/**
	 * wird zum erstellen einer neuen Runde aufgerufen
	 */
	public static void refresh() {
		if (connected && !isHost()) {
			// hier stimmen die Stati noch, newround verhindert das Einlesen neuer Werte, sofern nicht gesendet wurde, trotzdem wird der player überschrieben 
			
			getCLIENT().setNewround(true);
			

		} else {
			landscape = new Landscape();
			cannonballs1.clear();
			cannonballs2.clear();
			cannonballs3.clear();
			rams.clear();
			// TODO: x-pos player random
			Random rnd = new Random();
			int xposp1 = 100 + rnd.nextInt(300);
			rnd = new Random();
			int xposp2 = 1100 - rnd.nextInt(300);
			player1 = new Player(xposp1,
					landscape.getYmap()[xposp1 + 100] - 120,
					player1.getDefaulthealth(), player1.getDefaulthealth(),
					player1.getFirerate(), player1.getCannonballlevel(),
					player1.getWins(), player1.getWhichplayer());
			player2 = new Player(xposp2,
					landscape.getYmap()[xposp2 + 100] - 120,
					player2.getDefaulthealth(), player2.getDefaulthealth(),
					player2.getFirerate(), player2.getCannonballlevel(),
					player2.getWins(), player2.getWhichplayer());

		}
	}

	public static Client getCLIENT() {
		return CLIENT;
	}

	public static void setPlayer1(Player player1) {
		Main.player1 = player1;
	}

	public static void setPlayer2(Player player2) {
		Main.player2 = player2;
	}

	public static Gui getGUI() {
		return GUI;
	}

	public static Semaphore getLock() {
		return lock;
	}

	public static Engine getEngine() {
		return Engine;
	}

	public static double getWind() {
		return wind;
	}

	public static Image getRamImageLeft() {
		return ramImageLeft;
	}

	public static Image getRamImageRight() {
		return ramImageRight;
	}

	public static Image getBackgroundImage() {
		return backgroundImage;
	}

	public static Image getCannonball2Image() {
		return cannonball2Image;
	}

	public static Image getCannonball3Image() {
		return cannonball3Image;
	}

	public static Image getTexture() {
		return texture;
	}

	public static Image getCastlefullhealthImage() {
		return castlefullhealthImage;
	}

	public static Image getCastlemediumhealthImage() {
		return castlemediumhealthImage;
	}

	public static Image getCastlelowhealthImage() {
		return castlelowhealthImage;
	}

	public static Image getCannonball1Image() {
		return cannonball1Image;
	}

	public static void setLandscape(Landscape landscape) {
		Main.landscape = landscape;
	}

	public static ArrayList<Cannonball1> getCannonballs1() {
		return cannonballs1;
	}

	public static ArrayList<Cannonball2> getCannonballs2() {
		return cannonballs2;
	}

	public static ArrayList<Cannonball3> getCannonballs3() {
		return cannonballs3;
	}

	public static ArrayList<Ram> getRams() {
		return rams;
	}

	public static Player getPlayer1() {
		return player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public static Landscape getLandscape() {
		return landscape;
	}

	public static void setCannonballs1(ArrayList<Cannonball1> cannonballs1) {
		Main.cannonballs1 = cannonballs1;
	}

	public static void setCannonballs2(ArrayList<Cannonball2> cannonballs2) {
		Main.cannonballs2 = cannonballs2;
	}

	public static void setCannonballs3(ArrayList<Cannonball3> cannonballs3) {
		Main.cannonballs3 = cannonballs3;
	}

	public static void setRams(ArrayList<Ram> rams) {
		Main.rams = rams;
	}

	public static void setWind(double wind) {
		Main.wind = wind;
	}

	/**
	 * eine Methode um die Bilder zu laden. Zugunsten der Performance werden
	 * einmalig am Anfang alle benötigten Bilder geladen
	 * 
	 */
	private static Image getImage(String path) {

		Image tempImage = null;
		try {
			String location = ""
					+ Main.class.getProtectionDomain().getCodeSource()
							.getLocation();
			location = location.substring(0, location.length() - 5);
			location = location.substring(5);
			System.out.println("Loading: " + location + "/" + path);
			// File sourceimage = new File( location + "/res/" + path );
			tempImage = ImageIO.read(Main.class.getClass().getResource(
					"/" + path));
			// tempImage = ImageIO.read(sourceimage);

		} catch (Exception e) {
			System.out.println(path + " nicht geladen");
		}
		return tempImage;
	}
}
