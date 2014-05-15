import java.awt.Graphics;

public class Bullet {
	private final boolean up, down, left, right;
	private int x, y;
	private final int SPEED = 2;
	public Bullet(int x, int y, boolean up, boolean down, boolean left, boolean right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g) {
		g.drawOval(x, y, 15, 15);
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
}