package arne.bomber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameEngine implements ActionListener{
	/**
	 * Wann wurde das letzte Frame berechnet?
	 * */
	long last_update = 0;
	
	
	/**
	 * Alle Events die passieren, werden ihm mitgeteilt
	 * */
	GameEventListener gamelistener = null;
	
	/**
	 * Hier stehen alle Objekte drin, die in der Spielwelt sind (Vector ist Thread save)
	 * */
	Vector<GameObject> objekte = new Vector<GameObject>();
	
	public GameEngine() {
		
	}
	/**
	 * Berechnet die Positionnen aller Objekte fuer das naechste Frame
	 * */
	public void update() {
		if(last_update == 0) {
			last_update = System.currentTimeMillis();
		}
		long delta = System.currentTimeMillis() - last_update; 
		updateAll(delta);
	}
	
	/**
	 * Alle objekte werden upgedatet
	 * */
	private void updateAll(long delta) {

	}
	public Vector<GameObject> getObjekte() {
		return objekte;
	}
	public void setGamelistener(GameEventListener gamelistener) {
		this.gamelistener = gamelistener;
	}
	/**
	 * Auf Benutzereingaben reagieren
	 * 
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
