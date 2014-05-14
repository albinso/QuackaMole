import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenDirt extends Obstacle {
	public KeyListenDirt(int x, int y) {
		super(x, y, getImages());
	}

	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("dirt.jpg"));
		return new ImageIcon[0];
	}
}