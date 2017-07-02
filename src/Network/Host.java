package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import main.Main;
import data.Cannonball1;
import data.Cannonball2;
import data.Cannonball3;
import data.Landscape;
import data.Player;
import data.Ram;

/**
 * 
 * Host für die Netzwerkschnittstelle
 * 
 * @author arne
 *
 */
public class Host implements Runnable {

	private Socket socket;
	private ServerSocket listener;
	private boolean running = true;
	private boolean firstrun = true;

	public Host() throws IOException {

		listener = new ServerSocket(9090);

	}

	/**
	 * Konvertiert die Objekte in ein übertragbares Format
	 * 
	 */
	private ArrayList<ArrayList> convertToSend() {

		ArrayList<Cannonball1> cbs1 = Main.getCannonballs1();
		ArrayList<Cannonball2> cbs2 = Main.getCannonballs2();
		ArrayList<Cannonball3> cbs3 = Main.getCannonballs3();
		ArrayList<Ram> rams = Main.getRams();
		Player player1 = Main.getPlayer1();
		Player player2 = Main.getPlayer2();
		Landscape landscape = Main.getLandscape();
		double wind = Main.getWind();
		ArrayList<ArrayList> objects = new ArrayList<ArrayList>();
		ArrayList<double[]> cbs1s = new ArrayList<double[]>();
		ArrayList<double[]> cbs2s = new ArrayList<double[]>();
		ArrayList<double[]> cbs3s = new ArrayList<double[]>();
		ArrayList<int[]> ramss = new ArrayList<int[]>();
		ArrayList<int[]> players = new ArrayList<int[]>();
		ArrayList<int[]> landscapemaps = new ArrayList<int[]>();
		ArrayList<Double> winds = new ArrayList<Double>();

		for (Cannonball1 cb : cbs1) {
			double[] cannonballdata = { cb.getXpos(), cb.getYpos(),
					cb.getSpeedX(), cb.getSpeedY() };
			cbs1s.add(cannonballdata);
		}
		for (Cannonball2 cb : cbs2) {
			double[] cannonballdata = { cb.getXpos(), cb.getYpos(),
					cb.getSpeedX(), cb.getSpeedY() };
			cbs2s.add(cannonballdata);
		}
		for (Cannonball3 cb : cbs3) {
			double[] cannonballdata = { cb.getXpos(), cb.getYpos(),
					cb.getSpeedX(), cb.getSpeedY() };
			cbs3s.add(cannonballdata);
		}
		for (Ram ram : rams) {
			int direction;
			if (ram.getDirection() == "left")
				direction = 0;
			else
				direction = 1;

			int[] ramdata = { ram.getXpos(), ram.getYpos(), direction };
			ramss.add(ramdata);
		}
		int[] player1data = new int[13];
		player1data[0] = player1.getXpos();
		player1data[1] = player1.getYpos();
		player1data[2] = player1.getPower();
		player1data[3] = player1.getAngle();
		player1data[4] = player1.getDefaulthealth();
		player1data[5] = player1.getHealth();
		player1data[6] = player1.getFirerate();
		player1data[7] = player1.getCannonballlevel();
		player1data[8] = player1.getWins();
		if (player1.isCannonlock())
			player1data[9] = 1;
		else
			player1data[9] = 0;
		if (player1.isRamlock())
			player1data[10] = 1;
		else
			player1data[10] = 0;

		player1data[11] = player1.getFiredcannon();
		player1data[12] = player1.getFiredram();

		int[] player2data = new int[13];
		player2data[0] = player2.getXpos();
		player2data[1] = player2.getYpos();
		player2data[2] = player2.getPower();
		player2data[3] = player2.getAngle();
		player2data[4] = player2.getDefaulthealth();
		player2data[5] = player2.getHealth();
		player2data[6] = player2.getFirerate();
		player2data[7] = player2.getCannonballlevel();
		player2data[8] = player2.getWins();
		if (player2.isCannonlock())
			player2data[9] = 1;
		else
			player2data[9] = 0;
		if (player2.isRamlock())
			player2data[10] = 1;
		else
			player2data[10] = 0;
		player2data[11] = player2.getFiredcannon();
		player2data[12] = player2.getFiredram();

		players.add(player1data);
		players.add(player2data);
		landscapemaps.add(landscape.getXmap());
		landscapemaps.add(landscape.getYmap());
		winds.add(wind);

		objects.add(cbs1s);
		objects.add(cbs2s);
		objects.add(cbs3s);
		objects.add(ramss);
		objects.add(players);
		objects.add(landscapemaps);
		objects.add(winds);

		return objects;

	}

	/**
	 * Konviertiert Input zu Objekten
	 * 
	 * @param objects
	 */

	private void convertFromReceived(ArrayList<ArrayList> objects) {

		ArrayList<double[]> cbs1s = new ArrayList<double[]>();
		ArrayList<double[]> cbs2s = new ArrayList<double[]>();
		ArrayList<double[]> cbs3s = new ArrayList<double[]>();
		ArrayList<int[]> ramss = new ArrayList<int[]>();
		ArrayList<int[]> players = new ArrayList<int[]>();
		ArrayList<Boolean> newrounds = new ArrayList<Boolean>();
		ArrayList<int[]> clientstatss = new ArrayList<int[]>();

		cbs1s = objects.get(0);
		cbs2s = objects.get(1);
		cbs3s = objects.get(2);
		ramss = objects.get(3);
		players = objects.get(4);
		newrounds = objects.get(5);
		clientstatss = objects.get(6);

		// landscapemaps = objects.get(5);
		// winds = objects.get(6);

		ArrayList<Cannonball1> cbs1 = new ArrayList<Cannonball1>();
		ArrayList<Cannonball2> cbs2 = new ArrayList<Cannonball2>();
		ArrayList<Cannonball3> cbs3 = new ArrayList<Cannonball3>();
		ArrayList<Ram> rams = new ArrayList<Ram>();
		Player player1;
		Player player2;
		boolean newround;
		int[] clientstats;

		for (double[] x : cbs1s) {
			cbs1.add(new Cannonball1((int) x[0], (int) x[1], x[2], x[3]));
		}
		Main.setCannonballs1(cbs1);
		for (double[] x : cbs2s) {
			cbs2.add(new Cannonball2((int) x[0], (int) x[1], x[2], x[3]));
		}
		Main.setCannonballs2(cbs2);
		for (double[] x : cbs3s) {
			cbs3.add(new Cannonball3((int) x[0], (int) x[1], x[2], x[3]));
		}
		Main.setCannonballs3(cbs3);
		for (int[] x : ramss) {
			String direction;
			if (x[2] == 0)
				direction = "left";
			else
				direction = "right";
			rams.add(new Ram(x[0], x[1], direction));
		}
		Main.setRams(rams);
		int[] player1data = players.get(0);
		int[] player2data = players.get(1);
		boolean p1cannonlock;
		boolean p1ramlock;
		boolean p2cannonlock;
		boolean p2ramlock;
		if (player1data[9] == 1)
			p1cannonlock = true;
		else
			p1cannonlock = false;
		if (player1data[10] == 1)
			p1ramlock = true;
		else
			p1ramlock = false;
		if (player2data[9] == 1)
			p2cannonlock = true;
		else
			p2cannonlock = false;
		if (player2data[10] == 1)
			p2ramlock = true;
		else
			p2ramlock = false;
		player1 = new Player(player1data[0], player1data[1], player1data[2],
				player1data[3], player1data[4], player1data[5], player1data[6],
				player1data[7], player1data[8], 1, p1cannonlock, p1ramlock,
				player1data[11], player1data[12]);
		player2 = new Player(player2data[0], player2data[1], player2data[2],
				player2data[3], player2data[4], player2data[5], player2data[6],
				player2data[7], player2data[8], 2, p2cannonlock, p2ramlock,
				player2data[11], player2data[12]);
		Main.setPlayer1(player1);
		Main.setPlayer2(player2);
		newround = newrounds.get(0);
		clientstats = clientstatss.get(0);
		if (newround) {
			player2.setDefaulthealth(clientstats[0]);
			player2.setCannonballlevel(clientstats[1]);
			player2.setFirerate(clientstats[2]);

			System.out.println("Player2 stats: " + player2.getDefaulthealth()
					+ " " + player2.getFirerate() + " "
					+ player2.getCannonballlevel());

			Main.refresh();
		}

	}

	/**
	 * server starten
	 */
	@Override
	public void run() {

		try {

			// Sofern sich ein Mitspieler verbindet, startet das Spiel
			socket = listener.accept();

			if (!Main.isConnected() && Main.isHost()) {

				Main.setConnected(true);
				Main.getGUI().getWait().dispose();
				Main.getGUI().getMultiplayer().dispose();
				Main.getGUI().startNetworkGame();
				// kurz warten, bis die Engine läuft
				Thread.sleep(500);
			}

			InputStream socketStreamIn = socket.getInputStream();
			OutputStream socketStreamOut = socket.getOutputStream();

			ObjectOutputStream objectOutput = new ObjectOutputStream(
					socketStreamOut);
			ObjectInputStream objectInput = new ObjectInputStream(
					socketStreamIn);

			Semaphore wait = Main.getWait();
			Semaphore lock = Main.getLock();

			while (running) {
				// wir wollen sowohl sichergehen, dass die Engine mindestens
				// einen Durchlauf vollbracht hat (wait), sowie dass unsere
				// durch Keyevents erstellten Objekte nicht überschireben werden

				if (!firstrun) {
					lock.acquire();
					receive(objectInput);
					lock.release();
				}

				
				synchronized (wait) {
					wait.wait();
				}
				lock.acquire();
				send(objectOutput);
				synchronized (wait) {
					wait.notify();
				}
				lock.release();

				if (firstrun) {
					firstrun = false;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Main.getGUI().error(e.getMessage());
			running = false;
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Main.getGUI().error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				listener.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Main.getGUI().error(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * schickt daten an Client
	 * 
	 * @param stream
	 */
	private void send(ObjectOutputStream stream) {

		ArrayList<ArrayList> objectssend = convertToSend();

		try {
			stream.writeObject(objectssend);
			stream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Main.getGUI().error(e.getMessage());
			running = false;
			e.printStackTrace();
		}

	}

	/**
	 * liest daten vom client
	 * 
	 * @param stream
	 */
	private void receive(ObjectInputStream stream) {

		ArrayList<ArrayList> objectsreceive;
		try {
			objectsreceive = (ArrayList<ArrayList>) stream.readObject();
			convertFromReceived(objectsreceive);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			running = false;
			Main.getGUI().error(e.getMessage());
			e.printStackTrace();
		}

	}
}
