import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class StartPlace implements Serializable {
	private final int diameter = 20;
	private final int adjustment = 5;

	private int x, y, startNumber;

	public StartPlace(int x, int y, int startNumber) {
		this.x = x;
		this.y = y;
		this.startNumber = startNumber;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public void paint(Graphics g) {
		int x = this.x + adjustment;
		int y = this.y + adjustment;

		g.setColor(Color.black);
		g.drawOval(x, y, diameter, diameter);
		g.drawLine(x, y + diameter / 2, x + diameter, y + diameter / 2);
		g.drawLine(x + diameter / 2, y, x + diameter / 2, y + diameter);
	}
}