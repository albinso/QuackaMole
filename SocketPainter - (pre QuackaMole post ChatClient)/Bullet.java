import java.awt.Graphics;
import java.io.Serializable;

public class Bullet implements Serializable {
	private final boolean up, down, left, right;
	private int x, y, ownerID;
	private final int SIZE;
	private final int SPEED = 2;
	private int damage = 100;
	public Bullet(int x, int y, boolean up, boolean down, boolean left, boolean right, int ownerID) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.x = x;
		this.y = y;
		this.ownerID = ownerID;
		SIZE = 15;
	}

	public void paint(Graphics g) {
		g.fillOval(x, y, SIZE, SIZE);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return SIZE;
	}

	public boolean isOwner(KeyListenPlayer p) {
		return p.getID() == ownerID;
	}

	/**
	* Tells if the square defined by the params contains any of the bullet's corners.
	*/
	public boolean inBounds(int playerX, int playerY, int size) {
		return ((playerX < x && x < (playerX + size)) || (playerX < (x + SIZE) && (x + SIZE) < (playerX + size)))
			&& ((playerY < y && y < (playerY + size)) || (playerY < (y + SIZE) && (y + SIZE) < (playerY + size)));
	}

	public boolean collidedWithBlock(Obstacle obs) {
		return obs.inBounds(x, y) || obs.inBounds(x + SIZE, y) || 
			   obs.inBounds(x, y + SIZE) || obs.inBounds(x + SIZE, y + SIZE);
	}

	public void move() {
		if(up) {
			y -= SPEED;
		}
		if(down) {
			y += SPEED;
		}
		if(left) {
			x -= SPEED;
		}
		if(right) {
			x += SPEED;
		}
	}

	public int getDamage() {
		return damage;
	}

	public String toString() {
		return "Fired by: " + ownerID;
	}
}