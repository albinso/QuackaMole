import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class StartPlace implements Serializable {
	private final int diameter = 20;

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
		g.setColor(Color.black);
		g.drawOval(x + 5, y + 5, diameter, diameter);
		g.drawLine(x + 5, y + 5 + diameter / 2, x + 5 + diameter, y + 5 + diameter / 2);
		g.drawLine(x + 5 + diameter / 2, y + 5, x + 5 + diameter / 2, y + 5 + diameter);
	}
}