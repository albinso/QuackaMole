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

	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("stone.jpg"));
		return new ImageIcon[0];
	}
}