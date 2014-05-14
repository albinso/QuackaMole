import java.awt.*;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Obstacle implements Serializable {
	public static final long serialVersionUID = 20L;
	public static final int SIZE = 30; // default-value

	protected final ImageIcon[] BREAK_STAGES;

	private int durability;
	private ImageIcon image;
	protected int x, y;

	public Obstacle(int x, int y, ImageIcon[] imgs) {
		this.x = x;
		this.y = y;
		this.durability = imgs.length;
		BREAK_STAGES = imgs;

		if (imgs.length > 0)
			image = BREAK_STAGES[imgs.length - 1];
	}

	public boolean takeDamage(int damage) {
		durability -= durability >= damage ? damage : durability;
		image = BREAK_STAGES[durability];
		return durability == 0;
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

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, 30, 30);
		if (image != null)
			image.paintIcon(null, g, x, y);
	}
}