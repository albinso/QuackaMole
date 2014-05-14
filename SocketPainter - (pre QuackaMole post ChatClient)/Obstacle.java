import java.awt.*;
import java.awt.Image;
import java.io.Serializable;

public class Obstacle implements Serializable {
	public static final long serialVersionUID = 20L;
	public static final int SIZE = 30; // default-value

	protected final Image[] BREAK_STAGES;

	private int durability;
	private Image image;
	protected int x, y;

	public Obstacle(int x, int y, Image[] imgs) {
		this.x = x;
		this.y = y;
		this.durability = imgs.length;
		BREAK_STAGES = imgs;
	}

	public void setImage(Image img) {
		this.image = img;
	}

	public boolean takeDamage(int damage) {
		this.durability -= damage;
		setImage(BREAK_STAGES[durability]);
		return this.durability <= 0;
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
		g.fillRect(x, y, SIZE, SIZE);
		g.drawImage(image, x, y, SIZE, SIZE, null);
	}
}