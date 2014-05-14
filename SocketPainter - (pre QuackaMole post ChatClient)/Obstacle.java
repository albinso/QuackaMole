import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Obstacle implements Serializable {
	public static final int SIZE = 20; // default-value
	protected Color color;
	protected int x, y;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.black;
	}

	public void setColor(Color color) {
		this.color = color;
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
		return this.x <= x && x < (this.x + SIZE) && 
				this.y <= y && y < (this.y + SIZE);
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, SIZE, SIZE);
	}
}
