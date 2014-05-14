public class BreakableObstacle extends Obstacle {
	protected final Image[] BREAK_STAGES;
	private int durability;

	public BreakableObstacle(int x, int y, int durability) {
		super(x, y);
		this.durability = durability;
		BREAK_STAGES = new Image[durability];
	}

	public void takeDamage(int damage) {
		this.hp -= hp < damage ? hp : damage;
	}


}