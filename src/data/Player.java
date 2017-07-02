package data;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Main;

/*
 * Ein Spieler... selbsterklärende Bezeichnugen der Werte, denke ich.
 */
public class Player extends Paintable {


	
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

	public void setImage(Image image) {
		this.image = image;
	}
	
	private int whichplayer;
	private int health;
	private int defaulthealth;
	private int firerate;
	private int cannonballlevel = 1;
	private int angle = 90;
	private int power = 50;
	private Image fullhealth = Main.getCastlefullhealthImage();
	private Image mediumhealth = Main.getCastlemediumhealthImage();
	private Image lowhealth = Main.getCastlelowhealthImage();
	private int firedram;
	private int firedcannon;
	private int wins = 0;
	private boolean ramlock = false;
	private boolean cannonlock = false;

	// startinit
	public Player(int defaulthealth, int firerate, int whichplayer) {
		this.defaulthealth = defaulthealth;
		this.firerate = firerate;
		this.whichplayer = whichplayer;
		;
	}

	// mach nen echten player draus :P
	public Player(int xpos, int ypos, int defaulthealth, int health,
			int firerate, int cannonballlevel, int wins, int whichplayer) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.defaulthealth = defaulthealth;
		this.health = health;
		this.firerate = firerate;
		this.cannonballlevel = cannonballlevel;
		this.wins = wins;
		this.whichplayer = whichplayer;
	}

	// fürs netzwerkspiel
	public Player(int xpos, int ypos, int power, int angle, int defaulthealth,
			int health, int firerate, int cannonballlevel, int wins,
			int whichplayer, boolean cannonlock, boolean ramlock, int firedcannon, int firedram) {
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.defaulthealth = defaulthealth;
		this.health = health;
		this.firerate = firerate;
		this.cannonballlevel = cannonballlevel;
		this.wins = wins;
		this.whichplayer = whichplayer;
		this.angle = angle;
		this.power = power;
		this.cannonlock = cannonlock;
		this.ramlock = ramlock;
		this.firedcannon = firedcannon;
		this.firedram = firedram;
	}

	public boolean isRamlock() {
		if (firerate * 5 <  firedram) {
			ramlock = false;
		}
		return ramlock;
	}

	public void setRamlock(boolean ramlock) {
		this.ramlock = ramlock;
	}

	public boolean isCannonlock() {
		if (firerate < firedcannon) {
			cannonlock = false;
		}
		return cannonlock;
	}

	public void firecannon() {
		switch (cannonballlevel) {
		case 1:
			Main.getCannonballs1().add(
					new Cannonball1(getXpos() + 80, getYpos(), power, angle));
			firedcannon = 0;
			cannonlock = true;
			break;
		case 2:
			Main.getCannonballs2().add(
					new Cannonball2(getXpos() + 80, getYpos(), power, angle));
			firedcannon = 0;
			cannonlock = true;
			break;
		case 3:
			Main.getCannonballs3().add(
					new Cannonball3(getXpos() + 80, getYpos(), power, angle));
			firedcannon = 0;
			cannonlock = true;
			break;
		default:
			break;
		}
	}

	public void fireram() {
		switch (whichplayer) {
		case 1:
			Main.getRams().add(
					new Ram(getXpos() + 80, getYpos() + 100, "right"));
			firedram = 0;
			ramlock = true;
			break;
		case 2:
			Main.getRams()
					.add(new Ram(getXpos() + 80, getYpos() + 100, "left"));
			firedram = 0;
			ramlock = true;
			break;
		default:
			break;

		}

	}

	public void setRamlocked(boolean ramlock) {
		this.ramlock = ramlock;
	}

	public void setCannonlock(boolean cannonlock) {
		this.cannonlock = cannonlock;
	}

	public int getWins() {
		return this.wins;
	}

	public void addWin() {
		this.wins += 1;
	}

	public int getFiredram() {
		return this.firedram;
	}

	public int getFiredcannon() {
		return this.firedcannon;
}


	public void setFiredram(long firedram) {
		this.firedram += firedram;
	}

	public void setFiredcannon(long firedcannon) {
		this.firedcannon += firedcannon;
	}

	public int getWhichplayer() {
		return whichplayer;
	}

	public ArrayList<Rectangle> getCollisionboxes() {
		ArrayList<Rectangle> Collisionboxes = new ArrayList<Rectangle>();
		Rectangle Box1 = new Rectangle(getXpos() + 30, getYpos() + 40, 40, 80);
		Rectangle Box2 = new Rectangle(getXpos() + 75, getYpos() + 16, 30, 104);
		Rectangle Box3 = new Rectangle(getXpos() + 110, getYpos() + 40, 40, 80);
		Collisionboxes.add(Box1);
		Collisionboxes.add(Box2);
		Collisionboxes.add(Box3);
		return Collisionboxes;

	}

	public int getHealth() {
		return this.health;
	}

	public int getFirerate() {
		return this.firerate;
	}

	public int getCannonballlevel() {
		return this.cannonballlevel;
	}

	public int getAngle() {
		return this.angle;
	}

	public int getPower() {
		return this.power;
	}

	public void setHealth(int health) {
		this.health += health;
	}

	public void setFirerate(int firerate) {
		this.firerate = firerate;
	}

	public void setCannonballlevel(int cannonballlevel) {
		this.cannonballlevel = cannonballlevel;
	}

	public void setAngle(int angel) {
		if (angel <= 180 && angel >= 0)
			this.angle = angel;
	}

	public void setPower(int power) {
		if (power <= 100 && power >= 0)
			this.power = power;
	}

	public Image getImage() {
		double healthratio = (double) this.health / this.defaulthealth;
		if (healthratio > 0.7)
			return this.fullhealth;
		else if (healthratio > 0.3)
			return this.mediumhealth;
		else
			return this.lowhealth;
	}

	public int getDefaulthealth() {
		return this.defaulthealth;
	}

	public void setDefaulthealth(int defaulthealth) {
		this.defaulthealth = defaulthealth;
	}

}
