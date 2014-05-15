import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Buff implements Serializable {
	public static final int SHIELD = 0,
							DIGGER = 1,
							SPEEDER = 2;
	public static final int BUFFTYPES = 3;

	private final int 	diameter = 30,
						adjust = 5;
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
		} else if (type == SPEEDER) {
			color = Color.blue;
			duration = 200;
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
	 * @return if one of the players corners is in the buff
	 */
	public boolean inBounds(int playerX, int playerY, int size) {
		int x = this.x + adjust;
		int y = this.y + adjust;
		return coordinateInBounds(x, y, playerX, playerY, size)
			|| coordinateInBounds(x + diameter, y, playerX, playerY, size)
			|| coordinateInBounds(x, y + diameter, playerX, playerY, size)
			|| coordinateInBounds(x + diameter, y + diameter, playerX, playerY, size);
	}

	// help-method for finding inBounds
	private boolean coordinateInBounds(int x, int y, int playerX, int playerY, int size) {
		return playerX < x && x < (playerX + size)
			&& playerY < y && y < (playerY + size);
	}

	// paints the buff in the given color
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(adjust + x, adjust + y, diameter, diameter);
		g.setColor(Color.black);
		g.drawOval(adjust + x, adjust + y, diameter, diameter);
	}
}