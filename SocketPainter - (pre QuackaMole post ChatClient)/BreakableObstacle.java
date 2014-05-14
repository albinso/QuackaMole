import java.awt.Image;

public class BreakableObstacle extends Obstacle {
	protected final Image[] BREAK_STAGES;
	private int durability;

	public BreakableObstacle(int x, int y, Image[] imgs) {
		super(x, y);
		this.durability = imgs.length;
		BREAK_STAGES = imgs;
	}

	public boolean takeDamage(int damage) {
		this.durability -= damage;
		setImage(BREAK_STAGES[durability]);
		return this.durability <= 0;
	}
}