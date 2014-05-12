package quack.block;
import java.awt.*;

public class Obstacle {
	public static int size = 10; // default-value
	protected Color ballColor;
	protected int x, y;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
		ballColor = Color.black;
	}

	public int getLeftSide() {
		return x;
	}

	public int getRightSide() {
		return x + size;
	}

	public int getUpSide() {
		return y;
	}

	public int getDownSide() {
		return y + size;
	}

	public boolean inBounds(int x, int y) {
		return this.x <= x && x < (this.x + size) && 
				this.y <= y && y < (this.y + size);
	}

	public void draw(Graphics g) {
		g.setColor(ballColor);
		g.fillRect(x, y, size, size);
	}
}