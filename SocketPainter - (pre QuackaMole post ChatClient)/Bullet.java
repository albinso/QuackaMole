import java.awt.Graphics;

public class Bullet {
	private final boolean up, down, left, right;
	private int x, y;
	private final int SIZE;
	private final int SPEED = 2;
	public Bullet(int x, int y, boolean up, boolean down, boolean left, boolean right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.x = x;
		this.y = y;
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

	/**
	* Tells if the given point is contained in the bullet.
	*/
	public boolean inBounds(int x, int y) {
		// TODO: Make test for this
		return this.x <= x && x < x + SIZE && 
				this.y <= y && y < y + SIZE;
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
		return 1;
	}
}