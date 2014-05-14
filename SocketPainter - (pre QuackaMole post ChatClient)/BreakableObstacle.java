public class BreakableObstacle extends Obstacle {
	protected final Image[] BREAK_STAGES;
	private int durability;

	public BreakableObstacle(int x, int y, int durability, Image[] imgs) {
		super(x, y);
		this.durability = durability;
		BREAK_STAGES = imgs;
	}

	public boolean takeDamage(int damage) {
		this.hp -= damage;
		setImage(BREAK_STAGES[hp]);
		return this.hp <= 0;
	}
}