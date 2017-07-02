package data;

import java.awt.Image;

/*
 * Oberklasse, von der alle zu zeichnenden Objekte erben
 */

public abstract class Paintable {

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

}
