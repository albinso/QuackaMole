import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Buff implements Serializable{
	public static final int SHIELD = 0,
							DIGGER = 1;
	public static final int BUFFTYPES = 2;

	private final int diameter = 30;
	private int x, y;
	private int type;
	private int duration;
	private Color color;

	public Buff(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;

		if (type == SHIELD) {
			color = Color.pink;
			duration = 500;
		}
		else if (type == DIGGER) {
			color = Color.gray;
			duration = 500;
		}
		else {
			color = Color.black;
			duration = 0;
		}
	}

	public int duration() {
		return duration > 0 ? duration-- : duration;
	}

	public int getType() {
		return type;
	}

	public boolean inBounds(int x, int y) {
		return this.x <= x && x < (this.x + diameter) && 
				this.y <= y && y < (this.y + diameter);
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(5 + x, 5 + y, diameter, diameter);
		g.setColor(Color.black);
		g.drawOval(5 + x, 5 + y, diameter, diameter);
	}
}