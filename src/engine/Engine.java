package engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import main.Main;
import data.Cannonball1;
import data.Cannonball2;
import data.Cannonball3;
import data.Player;
import data.Ram;

/**
 * Objekte bewegen, Collisioncheck, Objekte bei Bedarf löschen
 */

public class Engine implements Runnable {

	private double wind = 0;

	private boolean justlookup = false;
	private boolean idle = false;
	private boolean demo = false;
	private long lasttime;

	@Override
	public void run() {

		Semaphore sem = Main.getLock();
		Semaphore wait = Main.getWait();

		while (true) {

			while (!this.idle) {

				/**
				 * Semaphore sperrt die Nutzereingabe, damit nicht in den Listen
				 * rumgepfuscht werden kann
				 */

				try {
					sem.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Main.getGUI().error(e.getMessage());
					e.printStackTrace();
				}

				// Objekte sammeln

				ArrayList<Cannonball1> cannonballs1 = Main.getCannonballs1();
				ArrayList<Cannonball2> cannonballs2 = Main.getCannonballs2();
				ArrayList<Cannonball3> cannonballs3 = Main.getCannonballs3();
				ArrayList<Ram> rams = Main.getRams();
				Player player1 = Main.getPlayer1();
				Player player2 = Main.getPlayer2();

				// die locktimes für Kanone und Rammen updaten
				updateLocks(player1, player2);

				// den Wind drehen
				updateWind();

				// Objekte bewegen und ggf. löschen
				moveAndCollide(cannonballs1, cannonballs2, cannonballs3, rams,
						player1, player2);

				// noch alle da?
				checkDead(player1, player2);

				// Lock wieder freigeben und ggf. dem Host bescheidsagen
				sem.release();
				synchronized (wait) {
					wait.notify();
				}
				// Die engine drosseln,sonst läufts zu schnell
				try {
					Thread.sleep(70);
				} catch (InterruptedException e) {
					Main.getGUI().error(e.getMessage());
					e.printStackTrace();
				}
			}

			while (this.idle) {

				// Wenn wir im Leerlauf sind, die Engine stark drosseln und
				// nur
				// ab und zu schauen, ob es weitergehen kann (beiden Spieler
				// haben wieder Gesundheit)
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Main.getGUI().error(e.getMessage());

					e.printStackTrace();
				}
				this.idle = false;
				this.justlookup = true;

			}
		}
	}

	/**
	 * Ist jemand in diesem Durchlauf gestorben, wird die Engine in den Leerlauf
	 * versetzt, bis es weitergeht (Levelaufstieg). in der ENGINEDEMO stirbt
	 * niemand
	 */
	private void checkDead(Player player1, Player player2) {

		if (!demo) {
			if (justlookup) {

				if (player1.getHealth() > 0 && player2.getHealth() > 0) {
					justlookup = false;
					Main.getGUI().getLevelupGui().dispose();
				} else
					idle = true;
			} else {
				if (player1.getHealth() <= 0) {
					Main.getGUI().levelup(2);
					player2.addWin();
					this.idle = true;
				} else if (player2.getHealth() <= 0) {
					Main.getGUI().levelup(1);
					player1.addWin();
					this.idle = true;
				}
			}
		}

		// In der DEMO geben wir allerdings Werte über die Konsole
		// aus:
		if (demo) {
			System.out.println("cannonballs: "
					+ (Main.getCannonballs1().size()
							+ Main.getCannonballs2().size() + Main
							.getCannonballs3().size()));
			System.out.println("rams: " + Main.getRams().size());
		}
	}

	/**
	 * Bewegt alle zu bewegenden Objekte, prüft Kollisionen und das Verlassen
	 * des Spielfeldes. Löscht ggf. Objekte
	 * 
	 * @param cannonballs1
	 * @param cannonballs2
	 * @param cannonballs3
	 * @param rams
	 * @param player1
	 * @param player2
	 */
	private void moveAndCollide(ArrayList<Cannonball1> cannonballs1,
			ArrayList<Cannonball2> cannonballs2,
			ArrayList<Cannonball3> cannonballs3, ArrayList<Ram> rams,
			Player player1, Player player2) {

		// Die Kanonenkugeln bewegen und löschen, falls sie aus dem
		// Bild
		// unten 'herausfallen'

		// wie es sich herausstellt, ist es eine dumme idee,
		// elemente
		// aus einer Liste zu entfernen, während man über die Anzahl
		// ihrer Elemente iteriert...., daher eine Liste aller zu
		// entfernenden Objekte
		int[] ymap = Main.getLandscape().getYmap();
		ArrayList<Object> removelist = new ArrayList<Object>();

		for (int i = 0; i < cannonballs1.size(); i++) {

			Cannonball1 cb = cannonballs1.get(i);
			double speedX = cb.getSpeedX();
			double speedY = cb.getSpeedY();
			int mass = cb.getWeight();
			int xpos = cb.getXpos();
			int ypos = cb.getYpos();

			speedX = speedX - (this.wind / 2);
			speedY = speedY + (0.5 * mass);
			cb.setSpeedX(speedX);
			cb.setSpeedY(speedY);

			cb.setXpos((int) (xpos + speedX));
			cb.setYpos((int) (ypos + speedY));

			if (ypos > 700)
				removelist.add(cb);
		}
		for (Object o : removelist) {
			cannonballs1.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs2.size(); i++) {

			Cannonball2 cb = cannonballs2.get(i);
			double speedX = cb.getSpeedX();
			double speedY = cb.getSpeedY();
			int mass = cb.getWeight();
			int xpos = cb.getXpos();
			int ypos = cb.getYpos();

			speedX = speedX - (this.wind / 2);
			speedY = speedY + (0.5 * mass);
			cb.setSpeedX(speedX);
			cb.setSpeedY(speedY);

			cb.setXpos((int) (xpos + speedX));
			cb.setYpos((int) (ypos + speedY));

			if (ypos > 700)
				removelist.add(cb);
		}

		for (Object o : removelist) {
			cannonballs2.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs3.size(); i++) {

			Cannonball3 cb = cannonballs3.get(i);
			double speedX = cb.getSpeedX();
			double speedY = cb.getSpeedY();
			int mass = cb.getWeight();
			int xpos = cb.getXpos();
			int ypos = cb.getYpos();

			speedX = speedX - (this.wind / 2);
			speedY = speedY + (0.5 * mass);
			cb.setSpeedX(speedX);
			cb.setSpeedY(speedY);

			cb.setXpos((int) (xpos + speedX));
			cb.setYpos((int) (ypos + speedY));

			if (ypos > 700)
				removelist.add(cb);

		}

		for (Object o : removelist) {
			cannonballs3.remove(o);
		}
		removelist.clear();

		// Die Rammen bewegen
		for (int i = 0; i < rams.size(); i++) {
			Ram ram = rams.get(i);
			if (ram.getDirection() == "left")
				ram.setXpos(ram.getXpos() - 2);
			else
				ram.setXpos(ram.getXpos() + 2);
			ram.setYpos(ymap[ram.getXpos()] - 25);

		}

		// Kollisionen abfragen (objekte+boden/spielrand) und ggf
		// Objekte Löschen/Werte anpassen

		// Kanonenkugeln mit Rammen:
		for (int i = 0; i < cannonballs1.size(); i++) {

			Cannonball1 cb = cannonballs1.get(i);
			for (int ii = 0; ii < rams.size(); ii++) {
				Ram ram = rams.get(ii);
				if (ram.getCollisionbox().intersects(cb.getCollisionbox())) {

					removelist.add(cb);
					removelist.add(ram);

				}

			}
		}
		for (Object o : removelist) {
			cannonballs1.remove(o);
			rams.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs2.size(); i++) {

			Cannonball2 cb = cannonballs2.get(i);
			for (int ii = 0; ii < rams.size(); ii++) {
				Ram ram = rams.get(ii);
				if (ram.getCollisionbox().intersects(cb.getCollisionbox())) {
					removelist.add(cb);
					removelist.add(ram);
				}

			}
		}
		for (Object o : removelist) {
			cannonballs2.remove(o);
			rams.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs3.size(); i++) {

			Cannonball3 cb = cannonballs3.get(i);
			for (int ii = 0; ii < rams.size(); ii++) {
				Ram ram = rams.get(ii);
				if (ram.getCollisionbox().intersects(cb.getCollisionbox())) {
					removelist.add(cb);
					removelist.add(ram);
				}

			}
		}
		for (Object o : removelist) {
			cannonballs3.remove(o);
			rams.remove(o);
		}
		removelist.clear();

		// Rammen mit Burgen
		for (int i = 0; i < rams.size(); i++) {
			Ram ram = rams.get(i);
			ArrayList<Rectangle> CBs = player1.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (ram.getCollisionbox().intersects(CB)
						&& ram.getDirection() == "left") {
					player1.setHealth(ram.getDamage());
					removelist.add(ram);
				}

			}

		}
		for (Object o : removelist) {
			rams.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < rams.size(); i++) {
			Ram ram = rams.get(i);
			ArrayList<Rectangle> CBs = player2.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (ram.getCollisionbox().intersects(CB)
						&& ram.getDirection() == "right") {
					player2.setHealth(ram.getDamage());
					removelist.add(ram);
				}

			}

		}
		for (Object o : removelist) {
			rams.remove(o);
		}
		removelist.clear();

		// Kanonenkugeln mit Burgen
		for (int i = 0; i < cannonballs1.size(); i++) {
			Cannonball1 cb = cannonballs1.get(i);
			ArrayList<Rectangle> CBs = player1.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player1.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs1.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs2.size(); i++) {
			Cannonball2 cb = cannonballs2.get(i);
			ArrayList<Rectangle> CBs = player1.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player1.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs2.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs3.size(); i++) {
			Cannonball3 cb = cannonballs3.get(i);
			ArrayList<Rectangle> CBs = player1.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player1.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs3.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs1.size(); i++) {
			Cannonball1 cb = cannonballs1.get(i);
			ArrayList<Rectangle> CBs = player2.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player2.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs1.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs2.size(); i++) {
			Cannonball2 cb = cannonballs2.get(i);
			ArrayList<Rectangle> CBs = player2.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player2.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs2.remove(o);
		}
		removelist.clear();

		for (int i = 0; i < cannonballs3.size(); i++) {
			Cannonball3 cb = cannonballs3.get(i);
			ArrayList<Rectangle> CBs = player2.getCollisionboxes();
			for (int ii = 0; ii < CBs.size(); ii++) {
				Rectangle CB = CBs.get(ii);
				if (cb.getCollisionbox().intersects(CB)) {
					player2.setHealth(cb.getDamage());
					removelist.add(cb);
				}

			}

		}
		for (Object o : removelist) {
			cannonballs3.remove(o);
		}
		removelist.clear();
	}

	/**
	 * Die Sperren der Feuerrate werde aktualisiert
	 * @param player1
	 * @param player2
	 */
	private void updateLocks(Player player1, Player player2) {
		long currenttime = System.currentTimeMillis();
		long timeelapsed = currenttime - lasttime;
		lasttime = currenttime;
		player1.setFiredcannon(timeelapsed);
		player1.setFiredram(timeelapsed);
		player2.setFiredcannon(timeelapsed);
		player2.setFiredram(timeelapsed);
	}

	/**
	 * der Wind wird autogeneriert
	 */
	private void updateWind() {
		Random rnd = new Random();
		double j = 0;
		double x = rnd.nextDouble() * 10;
		if (x < 5 && this.wind < 1.5)
			j = 0.1;
		if (x > 5 && this.wind > -1.5)
			j = -0.1;
		this.wind += j;
		Main.setWind(this.wind);
	}

	public void setDemo(boolean demo) {
		this.demo = demo;
	}

}
