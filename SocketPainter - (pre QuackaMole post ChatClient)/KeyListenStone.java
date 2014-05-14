import java.awt.Color;

public class KeyListenStone extends Obstacle {
	private final Color stoneColor = new Color(100, 100, 100);

	public KeyListenStone(int x, int y) {
		super(x, y);
		setColor(stoneColor);
	}
}