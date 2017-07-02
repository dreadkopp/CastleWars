package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Main;
import painter.Painter;
import data.Player;

/*
 * Die Gui ist für das Anzeigen der Fenster und Management der Key/Actionevents zuständig
 */

public class Gui implements Runnable, ActionListener, KeyListener {

	private JButton startlocal;
	private JButton startnetwork;
	private JButton host;
	private JButton client;
	private JButton EngineDemoGUI;
	private JButton EngineDemoNoGUI;
	private JButton stopDemo;
	private JButton morehealth;
	private JButton betterballs;
	private JButton betterfirerate;
	private JLabel Greeting;
	private JLabel Levelup;
	private JFrame wait;
	private JLabel waitmessage;
	private JPanel waitpanel;
	private JButton endwait;
	private JFrame multiplayer;
	private JFrame menuGui;
	private JFrame gameGui;
	private JFrame levelupGui;
	private JFrame errorGui;
	private JFrame DemoStop;
	private JPanel errorpanel;
	private JLabel errorlabel;
	private JButton errorseen;
	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panelmultiplayerbuttons;
	private JPanel IP;
	private JPanel multiplayerpanel;
	private JLabel IPtext;
	private int winner;

	@Override
	public void run() {

		// Menufenster ertsellen

		menuGui = new JFrame("CastelWars");
		panel0 = new JPanel(new GridLayout(2, 1));
		panel1 = new JPanel(new GridLayout(1, 1));
		panel2 = new JPanel(new GridLayout(1, 4));
		panel3 = new JPanel(new GridLayout(1, 1));
		startlocal = new JButton("Starte lokales Spiel");
		startnetwork = new JButton("Starte Netzwerkspiel");
		EngineDemoGUI = new JButton("Starte EngineDemo mit GUI");
		EngineDemoNoGUI = new JButton("Starte EngineDemo ohne GUI");
		Greeting = new JLabel(
				"Willkommen bei Castlewars, einem Spiel, so innovativ, dass man fast vom Stuhl fällt.");
		Greeting.setHorizontalAlignment(SwingConstants.CENTER);
		startlocal.addActionListener(this);
		startnetwork.addActionListener(this);
		EngineDemoGUI.addActionListener(this);
		EngineDemoNoGUI.addActionListener(this);
		menuGui.setResizable(false);
		menuGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuGui.setPreferredSize(new Dimension(1400, 700));
		menuGui.getContentPane().setLayout(new GridLayout(1, 1));
		panel1.add(Greeting);
		panel2.add(startlocal);
		panel2.add(startnetwork);
		panel2.add(EngineDemoGUI);
		panel2.add(EngineDemoNoGUI);
		panel0.add(panel1);
		panel0.add(panel2);
		menuGui.add(panel0);
		menuGui.pack();
		menuGui.setLocationRelativeTo(null);
		menuGui.setVisible(true);

		// Spielfenster vorbereiten:

		panel3.add(new Painter());
		gameGui = new JFrame("Castle Wars");
		gameGui.add(panel3);
		gameGui.addKeyListener(this);
		gameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameGui.setPreferredSize(new Dimension(1400, 700));
		gameGui.setLocationRelativeTo(null);

	}

	/**
	 * Auswahlmenu für Multiplayer(Netzwerk)
	 * 
	 * @throws UnknownHostException
	 */
	public void multiplayer() throws UnknownHostException {
		IPtext = new JLabel("Meine IP: "
				+ InetAddress.getLocalHost().getHostAddress());
		host = new JButton("Spiel hosten");
		host.addActionListener(this);
		client = new JButton("einem Spiel beitreten");
		client.addActionListener(this);
		multiplayerpanel = new JPanel(new GridLayout(2, 1));
		IP = new JPanel(new GridLayout(1, 1));
		IP.add(IPtext);
		panelmultiplayerbuttons = new JPanel(new GridLayout(1, 2));
		panelmultiplayerbuttons.add(host);
		panelmultiplayerbuttons.add(client);
		multiplayer = new JFrame("Netzwerkspiel");
		multiplayerpanel.add(IP);
		multiplayerpanel.add(panelmultiplayerbuttons);
		multiplayer.add(multiplayerpanel);
		multiplayer.pack();
		multiplayer.setLocationRelativeTo(null);
		multiplayer.setVisible(true);
	}

	/**
	 * wartefenster
	 */

	public void waitforconnecting() {
		endwait = new JButton("Abbrechen");
		endwait.addActionListener(this);
		waitmessage = new JLabel("Warte auf Verbindung des Mitspielers");
		waitpanel = new JPanel(new GridLayout(2, 1));
		wait = new JFrame("Warten");
		waitpanel.add(waitmessage);
		waitpanel.add(endwait);
		wait.add(waitpanel);
		wait.setLocationRelativeTo(null);
		wait.pack();
		wait.setVisible(true);
	}

	/**
	 * Gibt Fehlermeldung als Fenster aus
	 * 
	 * @param error
	 */
	public void error(String error) {
		errorlabel = new JLabel("   " + error + "   ");
		errorpanel = new JPanel(new GridLayout(2, 1));
		errorseen = new JButton("OK");
		errorseen.addActionListener(this);
		errorpanel.add(errorlabel);
		errorpanel.add(errorseen);
		errorGui = new JFrame("Fehler");
		errorGui.add(errorpanel);
		errorGui.pack();
		errorGui.setLocationRelativeTo(null);
		errorGui.setVisible(true);

	}

	/**
	 * * Wird aufgerufen, sofern ein Spieler eine Runde gewonnen hat. Neues
	 * Fenster+ Auswahl von Goodies, danach neue Runde
	 * 
	 * @param player
	 */

	public void levelup(int player) {

		this.winner = player;

		morehealth = new JButton("Gesundheit");
		betterballs = new JButton("Kanonenkugel");
		betterfirerate = new JButton("Feuerrate");
		morehealth.addActionListener(this);
		betterballs.addActionListener(this);
		betterfirerate.addActionListener(this);
		panel4 = new JPanel(new GridLayout(1, 3));
		panel5 = new JPanel(new GridLayout(2, 1));
		if (!Main.isConnected()) {
			panel4.add(morehealth);
			panel4.add(betterballs);
			panel4.add(betterfirerate);
			Levelup = new JLabel("Glückwunsch Spieler " + player + "! "
					+ "Wähle eine Verbesserung aus!");
			panel5.add(Levelup);
			panel5.add(panel4);
			levelupGui = new JFrame("Gewonnen");
		} else if (Main.isConnected() && Main.isHost() && winner == 1) {
			panel4.add(morehealth);
			panel4.add(betterballs);
			panel4.add(betterfirerate);
			Levelup = new JLabel("Glückwunsch Spieler " + player + "! "
					+ "Wähle eine Verbesserung aus!");
			panel5.add(Levelup);
			panel5.add(panel4);
			levelupGui = new JFrame("Gewonnen");
		} else if (Main.isConnected() && !Main.isHost() && winner == 2) {
			panel4.add(morehealth);
			panel4.add(betterballs);
			panel4.add(betterfirerate);
			Levelup = new JLabel("Glückwunsch Spieler " + player + "! "
					+ "Wähle eine Verbesserung aus!");
			panel5.add(Levelup);
			panel5.add(panel4);
			levelupGui = new JFrame("Gewonnen");
		} else {
			Levelup = new JLabel(
					"Verloren. Bitte warte, bis sich dein Mitspieler eine Verbesserung ausgesucht hat.");
			panel5.add(Levelup);
			levelupGui = new JFrame("Verloren");
		}
		levelupGui.add(panel5);
		levelupGui.pack();
		levelupGui.setPreferredSize(new Dimension(640, 480));
		levelupGui.setLocationRelativeTo(null);
		levelupGui.setVisible(true);
	}

	/**
	 * MultiplayerStart
	 */

	public void startNetworkGame() {
		System.out.println("starting the game");

		if (Main.isHost()) {
			Main.refresh();
			boolean demo = false;
			Main.startEngine(demo);
		}

		gameGui.pack();
		gameGui.setVisible(true);
		gameGui.setLocationRelativeTo(null);
		menuGui.dispose();
	}

	/**
	 * Action und Keylistener
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Player player1 = Main.getPlayer1();
		Player player2 = Main.getPlayer2();

		if (e.getSource() == this.startlocal) {
			Main.setHost(true);
			System.out.println("starting the game");
			Main.refresh();
			boolean demo = false;
			Main.startEngine(demo);
			gameGui.pack();
			gameGui.setVisible(true);
			gameGui.setLocationRelativeTo(null);
			menuGui.dispose();

		} else if (e.getSource() == this.morehealth) {
			levelupGui.dispose();
			if (Main.isConnected() && !Main.isHost()) {

			}
			if (Main.isConnected() && !Main.isHost()) {
				int[] stats = { player2.getDefaulthealth() + 50, 0, 0 };
				Main.getCLIENT().setNewclientstats(stats);
			} else if (winner == 1)
				player1.setDefaulthealth(player1.getDefaulthealth() + 50);
			else
				player2.setDefaulthealth(player2.getDefaulthealth() + 50);
			Main.refresh();

		} else if (e.getSource() == this.betterfirerate) {
			levelupGui.dispose();
			if (Main.isConnected() && !Main.isHost()) {
				int[] stats = { 0, 0, (int) (player2.getFirerate() * 0.75) };
				Main.getCLIENT().setNewclientstats(stats);
			} else if (winner == 1)
				player1.setFirerate((int) (player1.getFirerate() * 0.75));
			else
				player2.setFirerate((int) (player2.getFirerate() * 0.75));
			Main.refresh();

		} else if (e.getSource() == this.betterballs) {
			if (winner == 1 && player1.getCannonballlevel() >= 3) {
				error("Maximales Kanonenlevel erreicht");

			} else if (winner == 2 && player2.getCannonballlevel() >= 3) {
				error("Maximales Kanonenlevel erreicht");
			}

			else {
				levelupGui.dispose();
				if (Main.isConnected() && !Main.isHost()) {
					int[] stats = { 0, player2.getCannonballlevel() + 1, 0 };
					Main.getCLIENT().setNewclientstats(stats);
				} else if (winner == 1)
					player1.setCannonballlevel(player1.getCannonballlevel() + 1);
				else
					player2.setCannonballlevel(player2.getCannonballlevel() + 1);
				Main.refresh();
			}
		}

		else if (e.getSource() == this.errorseen) {
			errorGui.dispose();
		}

		else if (e.getSource() == this.startnetwork) {
			try {
				multiplayer();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				error(e1.getMessage());
				e1.printStackTrace();
			}

		}

		else if (e.getSource() == this.host) {
			Main.setHost(true);
			try {
				Main.startSender();
				waitforconnecting();
				// Main.waitforconnect(true);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				error(e1.getMessage());
				e1.printStackTrace();
			}

		}

		else if (e.getSource() == this.client) {
			Main.setHost(false);
			try {

				Main.startReceiver();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				error(e1.getMessage());
				e1.printStackTrace();
			}

		}

		else if (e.getSource() == this.EngineDemoGUI) {
			Main.setHost(true);
			System.out.println("starting the demo");
			Main.refresh();
			Main.startEngine(true);
			stopDemo = new JButton("Demo beenden");
			stopDemo.addActionListener(this);
			DemoStop = new JFrame();
			DemoStop.add(stopDemo);
			DemoStop.pack();
			Main.startDemo();
			gameGui.pack();
			gameGui.setLocationRelativeTo(null);
			gameGui.setVisible(true);
			DemoStop.setVisible(true);
			DemoStop.setLocationRelativeTo(null);
		} else if (e.getSource() == this.EngineDemoNoGUI) {
			Main.setHost(true);
			System.out.println("starting the demo without Gui");
			Main.refresh();
			Main.startEngine(true);
			stopDemo = new JButton("Demo beenden");
			stopDemo.addActionListener(this);
			DemoStop = new JFrame();
			DemoStop.add(stopDemo);
			DemoStop.pack();
			DemoStop.setLocationRelativeTo(null);
			DemoStop.setVisible(true);
			Main.startDemo();
		}

		else if (e.getSource() == this.stopDemo) {
			DemoStop.dispose();
			gameGui.dispose();
			Main.stopEngine();
		}

		else if (e.getSource() == this.endwait) {
			wait.dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Die Steuerung über Tastatur. Im lokalen Spiel steuert Spieler 1 die Energie mit W und S, den Winkel mit A und D, feuert Kanonen mit LEER und Rammen mit STRG, Spieler 2 die Energie mit HOCH/RUNTER, den Winkel mit RECHTS/LINKS und feuert mit ENTER bzw. SHIFT
	 * Im Netzwerkspiel steuert jeder Spieler mit den Pfeiltasten und feuert mit LEER/STRG
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		Semaphore sem = Main.getLock();

		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			error(e1.getMessage());
			e1.printStackTrace();
		}

		Player player1 = Main.getPlayer1();
		Player player2 = Main.getPlayer2();

		if (!Main.isConnected()) {
			switch (e.getKeyCode()) {
			// W
			case 87: {
				// TODO: aktion
				player1.setPower(player1.getPower() + 1);
				break;
			}
			// A
			case 65: {
				// TODO: aktion uups. typo.
				player1.setAngle(player1.getAngle() + 1);
				break;
			}
			// S
			case 83: {
				// TODO: aktion
				player1.setPower(player1.getPower() - 1);
				break;
			}
			// D
			case 68: {
				// TODO: aktion
				player1.setAngle(player1.getAngle() - 1);
				break;
			}
			// STRG
			case 17:
				// TODO: aktion
				if (!player1.isRamlock())
					player1.fireram();

				break;

			// LEER
			case 32:
				// TODO: aktion

				if (!player1.isCannonlock())
					player1.firecannon();

				break;

			// HOCH
			case 38: {
				// TODO: aktion
				player2.setPower(player2.getPower() + 1);
				break;
			}
			// RUNTER
			case 40: {
				// TODO: aktion
				player2.setPower(player2.getPower() - 1);
				break;
			}
			// LINKS
			case 37: {
				// TODO: aktion
				player2.setAngle(player2.getAngle() + 1);
				break;
			}
			// RECHTS
			case 39: {
				// TODO: aktion
				player2.setAngle(player2.getAngle() - 1);
				break;
			}
			// SHIFT
			case 16:
				// TODO: aktion

				if (!player2.isRamlock())
					player2.fireram();
				break;

			// ENTER
			case 10:
				// TODO: aktion

				if (!player2.isCannonlock())
					player2.firecannon();

				break;

			default:
			}
		}

		else if (Main.isConnected() && Main.isHost()) {
			switch (e.getKeyCode()) {

			// HOCH
			case 38: {
				// TODO: aktion
				player1.setPower(player1.getPower() + 1);
				break;
			}
			// RUNTER
			case 40: {
				// TODO: aktion
				player1.setPower(player1.getPower() - 1);
				break;
			}
			// LINKS
			case 37: {
				// TODO: aktion
				player1.setAngle(player1.getAngle() + 1);
				break;
			}
			// RECHTS
			case 39: {
				// TODO: aktion
				player1.setAngle(player1.getAngle() - 1);
				break;
			}

			// STRG
			case 17:
				// TODO: aktion
				if (!player1.isRamlock())
					player1.fireram();

				break;

			// LEER
			case 32:
				// TODO: aktion

				if (!player1.isCannonlock())
					player1.firecannon();

				break;
			}
		}

		else {
			switch (e.getKeyCode()) {

			// HOCH
			case 38: {
				// TODO: aktion
				player2.setPower(player2.getPower() + 1);
				break;
			}
			// RUNTER
			case 40: {
				// TODO: aktion
				player2.setPower(player2.getPower() - 1);
				break;
			}
			// LINKS
			case 37: {
				// TODO: aktion
				player2.setAngle(player2.getAngle() + 1);
				break;
			}
			// RECHTS
			case 39: {
				// TODO: aktion
				player2.setAngle(player2.getAngle() - 1);
				break;
			}

			// STRG
			case 17:
				// TODO: aktion
				if (!player2.isRamlock())
					player2.fireram();

				break;

			// LEER
			case 32:
				// TODO: aktion

				if (!player2.isCannonlock())
					player2.firecannon();

				break;
			}
		}

		sem.release();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public JFrame getWait() {
		return wait;
	}

	public JFrame getMultiplayer() {
		return multiplayer;
	}

	public JFrame getLevelupGui() {
		return levelupGui;
	}

}
