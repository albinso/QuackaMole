import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Buff implements Serializable {
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
			duration = 5;
		} else if (type == DIGGER) {
			color = Color.gray;
			duration = 10;
		} else {
			color = Color.black;
			duration = 0;
		}
	}

	public void durate() {
		duration -= duration > 0 ? 1 : 0;
	}

	/**
	 * Ticks the duration of the buff
	 */
	public int duration() {
		return duration;
	}

	/**
	 * @return type of the buff
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return if given coordinate is in bounds
	 */
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