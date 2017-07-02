package data;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import main.Main;

public class Cannonball1 extends Paintable implements Serializable {

	
	private Image image;

	private int xpos;
	private int ypos;

	public int getXpos() {
		return this.xpos;
	}

	public int getYpos() {
		return this.ypos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1100080018711774878L;
	private int damage = -10;
	private int weight = 10;
	private double speedX;
	private double speedY;

	// Konstruktor der Kanonenkugel
	public Cannonball1(int xpos, int ypos, int power, int angle) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setImage(Main.getCannonball1Image());
		this.speedX = power * Math.cos(angle / (180 / Math.PI));
		this.speedY = -(power * Math.sin(angle / (180 / Math.PI)));

	}

	// Konstruktor für Netzwerkspiel
	public Cannonball1(int xpos, int ypos, double speedx, double speedy) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setSpeedX(speedx);
		this.setSpeedY(speedy);
		this.setImage(Main.getCannonball1Image());
	}

	// die Kolisionbox
	public Rectangle getCollisionbox() {
		return (new Rectangle(getXpos(), getYpos(), 20, 20));
	}

	public double getSpeedX() {
		return this.speedX;
	}

	public double getSpeedY() {
		return this.speedY;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public int getDamage() {
		return this.damage;
	}

	public int getWeight() {
		return this.weight;
	}

}
