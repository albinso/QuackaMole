import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * An obstacle is in the field an object
 * that the players cannot move through 
 * and in some cases destroy. This is
 * a super-class for all the used obstacles.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Obstacle implements Serializable {
	public static final long serialVersionUID = 20L;
	public static final int SIZE = 48;

	protected int x, y;
	private int durability;
	private ImageIcon image;

	/**
	 * This constructor is used for all obstacles.
	 */
	public Obstacle(int x, int y, int durability, ImageIcon image) {
		this.x = x;
		this.y = y;
		this.durability = durability;

		this.image = image;
	}

	/**
	 * Called when a player collides with the block.
	 * @return true if durability drops to 0.
	 */
	public boolean takeDamage(int damage) {
		durability -= durability >= damage ? damage : durability;

		return durability == 0;
	}

	/**
	 * @return the left side of the obstacle.
	 */
	public int getLeftSide() {
		return x;
	}

	/**
	 * @return the right side of the obstacle.
	 */
	public int getRightSide() {
		return x + SIZE;
	}

	/**
	 * @return the up-side of the obstacle.
	 */
	public int getUpSide() {
		return y;
	}

	/**
	 * @return the down-side of the obstacle.
	 */
	public int getDownSide() {
		return y + SIZE;
	}

	/**
	 * Resizes the icon to a preferbly size.
	 * @return the resized imageicon.
	 */
	public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * @return if the given point is contained in the block.
	 */
	public boolean inBounds(int x, int y) {
		return this.x < x && x < getRightSide() && 
				this.y < y && y < getDownSide();
	}

	/**
	 * Paints the image for obstacle (but only if it isn't null).
	 */
	public void paint(Graphics g) {
		if (image != null)
			image.paintIcon(null, g, x, y);
	}
}