import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * Dirt is an obstacle for the player and 
 * can be destroyed by digging.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Dirt extends Obstacle {
	private final int durability = 5;

	/**
	 * Just redirects the parameters to the constructor 
	 * of the super-class and adds the class specific
	 * image.
	 */
	public Dirt(int x, int y) {
		super(x, y, durability, getImage());
	}

	/**
	 * @return the image for dirt.
	 */
	private static ImageIcon getImage() {
		Image image = Toolkit.getDefaultToolkit().getImage("dirt.png");
		return new ImageIcon(image);
	}
}