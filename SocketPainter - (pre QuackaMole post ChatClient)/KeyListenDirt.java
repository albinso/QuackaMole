import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenDirt extends Obstacle {
	public KeyListenDirt(int x, int y) {
		super(x, y, getImages());
	}

	/**
	* Just a place to read images and put them in an array.
	* Only for use in constructor of superclass.
	*/
	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[5];
		for(int i = 0; i < images.length; i++) {
			images[i] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("dirt_v2.png"));
		}
		return images;
	}
}