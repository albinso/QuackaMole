import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Buffs are used in the game to move faster, 
 * shield against damage or dig faster.
 * They are hidden in crates and then dropped
 * when the crates are destroyed. The buffs 
 * take effect when they are picked up.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Buff implements Serializable {
	public static final int SHIELD = 0,
							DIGGER = 1,
							SPEEDER = 2;

	public static final int BUFFTYPES = 3;

	private final int 	diameter = 30,
						adjust = 9;

	private int x, y;
	private int type;
	private int duration;
	private Color color;

	/**
	 * Sets the duration and the color to the buff 
	 * depending on which type it is.
	 */
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

	/**
	 * Ticking away the duration of the buff.
	 */
	public void durate() {
		duration -= duration > 0 ? 1 : 0;
	}

	/**
	 * The duration of the buff.
	 */
	public int duration() {
		return duration;
	}

	/**
	 * @return type of the buff.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return if the buff is inside the player.
	 */
	public boolean inBounds(int playerX, int playerY, int size) {
		int x = this.x + adjust;
		int y = this.y + adjust;

		return ((playerX < x && x < (playerX + size)) || (playerX < (x + diameter) && (x + diameter) < (playerX + size)))
			&& ((playerY < y && y < (playerY + size)) || (playerY < (y + diameter) && (y + diameter) < (playerY + size)));
	}

	/**
	 * Paints the buff in the given color.
	 */
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(adjust + x, adjust + y, diameter, diameter);
		g.setColor(Color.black);
		g.drawOval(adjust + x, adjust + y, diameter, diameter);
	}
}