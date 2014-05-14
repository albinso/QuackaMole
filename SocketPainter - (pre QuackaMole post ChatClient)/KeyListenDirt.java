import java.awt.Image;

public class KeyListenDirt extends BreakableObstacle{
	public KeyListenDirt(int x, int y) {
		super(x, y, 0, getImages());
	}

	public Image[] getImages() {
		return new Images[0];
	}
}