import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenCrate extends Obstacle {
	public KeyListenCrate(int x, int y) {
		super(x, y, getImages());
	}

	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("crate.jpg"));
		return new ImageIcon[0];
	}
}