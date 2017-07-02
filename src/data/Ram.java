package data;

import java.awt.Image;
import java.awt.Rectangle;

import main.Main;

public class Ram extends Paintable {
	
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
	private int damage = -30;
	private String direction;

	public Ram(int xpos, int ypos, String direction) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		if (direction == "left")
			this.setImage(Main.getRamImageLeft());
		else
			this.setImage(Main.getRamImageRight());
		this.direction = direction;

	}

	public Rectangle getCollisionbox() {
		return (new Rectangle(getXpos() + 5, getYpos(), 40, 30));
	}

	public String getDirection() {
		return direction;
	}

	public int getDamage() {
		return damage;
	}

}
