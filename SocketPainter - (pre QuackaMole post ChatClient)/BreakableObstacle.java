public class BreakableObstacle extends Obstacle {
	protected final Image[] BREAK_STAGES;
	private int durability;

	public BreakableObstacle(int x, int y, int durability, Image[] imgs) {
		super(x, y);
		this.durability = durability;
		BREAK_STAGES = imgs;
	}

	public boolean takeDamage(int damage) {
		this.durability -= damage;
		setImage(BREAK_STAGES[durability]);
		return this.durability <= 0;
	}
}