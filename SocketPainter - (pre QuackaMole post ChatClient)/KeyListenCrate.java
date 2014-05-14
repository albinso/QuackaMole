import java.awt.Image;

public class KeyListenCrate extends BreakableObstacle {
	public KeyListenCrate(int x, int y) {
		super(x, y, getImages());
	}

	public Image[] getImages() {
		return new Image[0];
	}
}