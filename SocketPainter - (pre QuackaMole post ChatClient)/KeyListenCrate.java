import java.awt.Image;

public class KeyListenCrate extends Obstacle {
	public KeyListenCrate(int x, int y) {
		super(x, y, getImages());
	}

	public static Image[] getImages() {
		return new Image[0];
	}
}