package arne.bomber;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameView extends JFrame implements GameEventListener {
	
	/**
	 * Die Hintergrundfarbe zum loeschen des Frames
	 * */
	private Color bgcolor = Color.WHITE;
	
	private GameEngine engine = null;
	/**
	 * Das Double Buffer Bild auf dem gezeichnet wird
	 * */
	private BufferedImage offscreen = null;
	
	/**
	 * Initialisiere die GUI hier (Actionlistener setzen und so)
	 * */
	public GameView(int width, int height) {
		offscreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.engine = new GameEngine();
		engine.setGamelistener(this);
		//Benutzereingaben verarbeiten
		//button.addActionListener(engine);
	}
	
	
	/**
	 * Diese Funktion k&uumlmmert sich um das Double Buffering.
	 * */
	@Override
	public void update(Graphics o) {
		Graphics g = offscreen.getGraphics();
		
		// Das offscreen leer machen
		g.setColor(bgcolor);
		g.fillRect(0, 0, offscreen.getWidth(), offscreen.getHeight());
		
		// den Zustand aller Objekte erneurern
		engine.update();
		
		/*
		 * Zeichne alle Objekte
		 * */
		for(GameObject go : engine.getObjekte()) {
			g.drawImage(go.getTexture(), go.getX(), go.getY(), this);
		}
			
	}
	
	/**
	 * Hier wird nur noch das offscreen auf den Bildschirm gelegt
	 * */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(offscreen, 0, 0, this);
	}

	/**
	 * Events, die im SPiel passieren
	 * */
	@Override
	public void collisionOccurred() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Events, die im Spiel passieren
	 * */
	@Override
	public void playerWin() {
		// TODO Auto-generated method stub
		
	}


	public Color getBgcolor() {
		return bgcolor;
	}


	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

}
