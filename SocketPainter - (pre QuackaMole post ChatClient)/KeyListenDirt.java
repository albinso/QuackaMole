import java.awt.Image;

public class KeyListenDirt extends BreakableObstacle{
	public KeyListenDirt(int x, int y) {
		super(x, y, getImages());
	}

	public Image[] getImages() {
		return new Image[0];
	}
}