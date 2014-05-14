package quack.block;
import java.awt.*;

public class Obstacle {
	public static final int SIZE = 30; // default-value
	protected Color color;
	private Image image;
	protected int x, y;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.black;
	}

	public int getLeftSide() {
		return x;
	}

	public int getRightSide() {
		return x + SIZE;
	}

	public int getUpSide() {
		return y;
	}

	public int getDownSide() {
		return y + SIZE;
	}

	public boolean inBounds(int x, int y) {
		return this.x <= x && x < getRightSide() && 
				this.y <= y && y < getDownSide();
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, SIZE, SIZE);
		g.drawImage(image, x, y, SIZE, SIZE, null);
	}
}