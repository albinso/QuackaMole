import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenStone extends Obstacle {
	public KeyListenStone(int x, int y) {
		super(x, y, getImages());
	}

	public boolean takeDamage(int damage) {
		// DO NOTHING (mohaha)
		return false;
	}

	/**
	* Just a place to read images and put them in an array.
	* Only for use in constructor of superclass.
	*/
	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("stone_v2.png"));
		return images;
	}
}