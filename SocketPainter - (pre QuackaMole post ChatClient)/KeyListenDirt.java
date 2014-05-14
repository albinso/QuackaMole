import java.awt.Color;

public class KeyListenDirt extends Obstacle {
	private final Color dirtColor = new Color(150, 100, 150);
	private int durability;

	public KeyListenDirt(int x, int y) {
		super(x, y);
		setColor(dirtColor);
	}

	public void demolish() {
		durability -= (durability > 0 ? 1 : 0);
	}

	public boolean demolished() {
		return durability == 0;
	}
}