import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenCrate extends Obstacle {
	public KeyListenCrate(int x, int y) {
		super(x, y, getImages());
	}

	/**
	* Just a place to read images and put them in an array.
	* Only for use in constructor of superclass.
	*/
	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("crate_v2.png"));
		return images;
	}
}