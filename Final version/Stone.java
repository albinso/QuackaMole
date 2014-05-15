import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * Stones are unbreakable obstacles for 
 * the player.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Stone extends Obstacle {
	/**
	 * Just redirects the parameters to the constructor 
	 * of the super-class and adds the class specific
	 * image. Since stone is unbreakable the durability
	 * is irrelevant and is set to -1.
	 */
	public Stone(int x, int y) {
		super(x, y, -1, getImage());
	}

	/**
	 * @return the image for stones.
	 */
	private static ImageIcon getImage() {
		Image image = Toolkit.getDefaultToolkit().getImage("stone.png");
		return new ImageIcon(image);
	}

	/**
	 * KeyListenStone is unbreakable. No damage should be taken.
	 */
	public boolean takeDamage(int damage) {
		// DO NOTHING
		return false;
	}
}