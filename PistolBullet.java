public class PistolBullet implements Bullet {
	private final int damage = 1;
	private int x, y;
	private int direction;

	public PistolBullet(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int damage() {
		return damage;
	}
}