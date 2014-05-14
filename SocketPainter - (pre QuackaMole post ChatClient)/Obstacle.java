import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Obstacle implements Serializable {
	public static final long serialVersionUID = 20L;
	public static final int SIZE = 48; // default-value

	protected final ImageIcon[] BREAK_STAGES;

	private int durability;
	private ImageIcon image;
	protected int x, y;

	public Obstacle(int x, int y, ImageIcon[] imgs) {
		this.x = x;
		this.y = y;
		this.durability = imgs.length + 2;
		BREAK_STAGES = imgs;

		if (imgs.length > 0)
			image = BREAK_STAGES[imgs.length - 1];
		image = resizeIcon(image, SIZE, SIZE);
	}

	/**
	* Called when a player collides with the block.
	* @return true if durability drops to 0.
	*/
	public boolean takeDamage(int damage) {
		//durability -= durability >= damage ? damage : durability;
		durability -= damage;
		if(durability < 0) {
			durability = 0;
		}
		if(durability > 0) {
			image = BREAK_STAGES[0];
		}
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

	private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	* Tells if the given point is contained in the block.
	*/
	public boolean inBounds(int x, int y) {
		return this.x <= x && x < getRightSide() && 
				this.y <= y && y < getDownSide();
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, SIZE, SIZE);
		if (image != null)
			image.paintIcon(null, g, x, y);
	}
}