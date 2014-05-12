package quack.player;

public interface Weapon {
	/**
	* Shoots a bullet in the current facing direction.
	*/
	Bullet shootBullet(int bulletx, int bullety, int bulletDirection);
}