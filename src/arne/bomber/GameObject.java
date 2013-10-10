package arne.bomber;

import java.awt.Image;
/**
 * SPeichert den Zustand eines Objectes im Spiel.
 * Jegliche Zustandsaenderungen, werden von der GameEngine durchgefuehrt
 * */
public class GameObject {

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Texturen muessen zum Start geladen werden.
	 * Das muss in einem Extra Thread passieren, sonst haengt die GUI!
	 * */
	public Image getTexture() {
		// TODO Auto-generated method stub
		return null;
	}



}
