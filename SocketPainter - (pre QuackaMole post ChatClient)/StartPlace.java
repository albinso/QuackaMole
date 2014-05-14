import java.awt.Graphics;
import java.awt.Image;

public class StartPlace  {
	private final int diameter = 20;
	private int x, y;

	public StartPlace(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawOval(x + 5, y + 5, diameter, diameter);
	}
}