import quack.player.Weapon;
import quack.player.Bullet;

public class Pistol implements Weapon{
	private int bullets;

	public Pistol() {
		bullets = 10;
	}

	public Bullet shootBullet(int bulletx, int bullety, int bulletDirection) {
		if (bullets > 0) {
			bullets--;
			return new PistolBullet(bulletx, bullety, bulletDirection);
		}

		return null;
	}	
}