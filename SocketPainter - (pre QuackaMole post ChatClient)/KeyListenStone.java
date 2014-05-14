import java.awt.Image;

public class KeyListenStone extends Obstacle {
	public KeyListenStone(int x, int y) {
		super(x, y, getImages());
	}

	public static Image[] getImages() {
		return new Image[0];
	}
}