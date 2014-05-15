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
		this.durability = imgs.length;
		BREAK_STAGES = imgs;
		for(int i = 0; i < BREAK_STAGES.length; i++) {
			BREAK_STAGES[i] = resizeIcon(BREAK_STAGES[i], SIZE, SIZE);
		}

		if (imgs.length > 0)
			image = BREAK_STAGES[imgs.length - 1];
	}

	/**
	* Called when a player collides with the block.
	* @return true if durability drops to 0.
	*/
	public boolean takeDamage(int damage) {
		// TODO: Make test for this
		durability -= durability >= damage ? damage : durability;
		if(durability > 0) {
			image = BREAK_STAGES[durability-1]; // Fix once we have longer arrays.
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
		// TODO: Make test for this
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