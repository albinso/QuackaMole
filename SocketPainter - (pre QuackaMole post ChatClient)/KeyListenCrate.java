import java.awt.Image;

public class KeyListenCrate extends BreakableObstacle {
	public KeyListenCrate(int x, int y) {
		super(x, y, 0, getImages());
	}

	public Image[] getImages() {
		return new Images[0];
	}
}