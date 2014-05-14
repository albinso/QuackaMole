import java.awt.*;
import java.awt.Image;

public class Obstacle {
	public static final int SIZE = 30; // default-value
	private Image image;
	protected int x, y;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
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

	public void setImage(Image img) {
		this.image = img;
	}

	public void paint(Graphics g) {
		g.fillRect(x, y, SIZE, SIZE);
		g.drawImage(image, x, y, SIZE, SIZE, null);
	}
}