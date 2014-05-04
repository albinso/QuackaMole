import java.awt.*;
import java.util.*;

public class QuackPlayer implements Player {
	private final int movementSpeed = 1;
	private final Color playerColor = Color.red;

	public int x, y;
	public int direction;
	public boolean up, right, down, left;

	private Weapon weapon;
	private LinkedList<Bullet> bullets;

	private Buff buff;

	public QuackPlayer(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;

		setDirections(false, false, false, false);

		addWeapon(new Pistol());
		bullets = new LinkedList<Bullet>();

		addBuff(null);
	}

/*	public void setDirection(double angle) {
		direction = angle;
	}*/

	private void setDirections(boolean up, boolean right, boolean down, boolean left) {
		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;
	}/*

	public void goUp(boolean up) {
		setDirections(up, false, false, false);
	}

	public void goRight(boolean right) {
		setDirections(false, right, false, false);
	}

	public void goDown(boolean down) {
		setDirections(false, false, down, false);
	}

	public void goLeft(boolean left) {
		setDirections(false, false, false, left);
	}*/

	public void addWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void addBuff(Buff buff) {
		this.buff = buff;
	}

	public void move() {
		if (right) {
			direction = Player.RIGHT;
			x += movementSpeed;
		}
		else if (up) {
			direction = Player.UP;
			y -= movementSpeed;
		}
		else if (left) {
			direction = Player.LEFT;
			x -= movementSpeed;
		}
		else if (down) {
			direction = Player.DOWN;
			y += movementSpeed;
		}
	}

	public void shoot() {
		int bulletx = x;
		int bullety = y;

		if (direction == Player.RIGHT) {
			x += Player.size;
			y += Player.size / 2;
		}
		if (direction == Player.DOWN) {
			x += Player.size / 2;
			y += Player.size;
		}
		if (direction == Player.LEFT) {
			x -= Bullet.depth;
			y += Player.size / 2;
		}
		if (direction == Player.UP) {
			x += Player.size / 2;
			y -= Bullet.depth;
		}

		Bullet bullet = weapon.shootBullet(bulletx, bullety, direction);
		bullets.add(bullet);
	}

	public void draw(Graphics g) {
		g.setColor(playerColor);
		g.fillRect(x, y, Player.size, Player.size);
		g.setColor(Color.black);
		g.drawRect(x, y, Player.size, Player.size);
	}
}