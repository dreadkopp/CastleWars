package engine;

import java.util.Random;
import java.util.concurrent.Semaphore;

import data.Player;
import main.Main;

public class Demo implements Runnable {

	/**
	 * wählt willkürlich winkel und power und erzeugt objekte
	 */
	@Override
	public void run() {

		Semaphore sem = Main.getLock();

		while (true) {

			try {
				sem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Main.getGUI().error(e.getMessage());
				e.printStackTrace();
			}

			Player player1 = Main.getPlayer1();
			Player player2 = Main.getPlayer2();
			Random rnd = new Random();
			int angle = rnd.nextInt(180);
			rnd = new Random();
			int power = rnd.nextInt(100);
			rnd = new Random();
			player1.setAngle(angle);
			player1.setPower(power);
			int ramorcannon = rnd.nextInt(10);
			rnd = new Random();
			int cblvl = rnd.nextInt(3) + 1;
			player1.setCannonballlevel(cblvl);
			if (ramorcannon == 1) {
				player1.fireram();
			} else
				player1.firecannon();
			rnd = new Random();
			angle = rnd.nextInt(180);
			rnd = new Random();
			power = rnd.nextInt(100);
			rnd = new Random();
			player2.setAngle(angle);
			player2.setPower(power);
			ramorcannon = rnd.nextInt(10);
			rnd = new Random();
			cblvl = rnd.nextInt(3) + 1;
			player2.setCannonballlevel(cblvl);
			if (ramorcannon == 1) {
				player2.fireram();
			} else
				player2.firecannon();

			sem.release();
			// nicht überlasten, gelle ;)
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Main.getGUI().error(e.getMessage());
				e.printStackTrace();
			}

		}
	}

}
