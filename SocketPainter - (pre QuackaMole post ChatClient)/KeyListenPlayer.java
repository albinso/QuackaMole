import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class KeyListenPlayer implements Serializable {
	public static final long serialVersionUID = 41L;
	private final Color lePink = new Color(255, 20, 147, 150);
	private final int 	SIZE = 32, MAXIMALHEALTH = 100;
	private int digCooldown, digCount, digDamage;
	private int shootCooldown, shootCount;
	public int x, y;
	private int id, health, movement;
	private boolean shield;
	private ImageIcon image;
	private Buff buff;
	private transient boolean up, down, left, right, moving;

	public KeyListenPlayer(int x, int y, int id) {
		setPosition(x, y);
		up = false;
		down = false;
		left = false;
		right = false;
		this.id = id;
		movement = 1;
		health = MAXIMALHEALTH;
		digCooldown = 10;
		digDamage = 1;
		shootCooldown = 40;
		shield = false;
		image = Obstacle.resizeIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("TheMole.png")), SIZE, SIZE);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setBuff(Buff buff) {
		this.buff = buff;

		resetBuff();
		if (buff.getType() == Buff.SHIELD) {
			shield = true;
		} else if (buff.getType() == Buff.DIGGER) {
			digDamage = 5; // TODO: stop debugging
		} else if (buff.getType() == Buff.SPEEDER) {
			movement = 2;
		}
	}

	public void tick() {
		shootTick();
		buffTick();
	}

	public void buffTick()  {
		if (buff == null)
			return;

		if (buff.duration() == 0) {
			resetBuff();
			buff = null;
		}
	}

	private void shootTick() {
		shootCount++;
	}

	private void resetBuff() {
		digDamage = 1;
		shield = false;
		movement = 1;
	}

	/**
	* Sets player direction to straight up.
	* Equivalent methods for other directions exist.
	*/
	public void goUp() {
		up = true;
		down = false;
		left = false;
		right = false;
		moving = true;
	}

	public void goDown() {
		up = false;
		down = true;
		left = false;
		right = false;
		moving = true;
	}

	public void goLeft() {
		up = false;
		down = false;
		left = true;
		right = false;
		moving = true;
	}

	public void goRight() {
		up = false;
		down = false;
		left = false;
		right = true;
		moving = true;
	}

	/**
	* Sets the movement in the given direction to keepMoving.
	*/
	public void setMoving(int direction, boolean keepMoving) {
		boolean oldUp = up, oldDown = down, oldLeft = left, oldRight = right;
		switch(direction) {
			case MovePackage.UP:
				up = keepMoving;
				break;
			case MovePackage.DOWN:
				down = keepMoving;
				break;
			case MovePackage.LEFT:
				left = keepMoving;
				break;
			case MovePackage.RIGHT:
				right = keepMoving;
				break;
		}
		moving = keepMoving;
	}

	/**
	* Moves based on the direction variables.
	*/
	public void move() {
		if (buff != null && buff.getType() == Buff.SPEEDER)
			buff.durate();

		if(!moving) {
			return;
		}
		if (up)
			y -= movement;
		if (down)
			y += movement;
		if (left)
			x -= movement;
		if (right)
			x += movement;
	}

	public Bullet shoot() {
		if(shootCount >= shootCooldown) {
			shootCount = 0;
			int adjustment = SIZE/2 - Bullet.SIZE/2;
			return new Bullet(x + adjustment, y + adjustment, up, down, left, right, getID());
		}
		return null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	* @return an id that is used to identify players in both server and client.
	*/
	public int getID() {
		return id;
	}

	/**
	* The damage the player will do to an object. 
	* Has a cooldown between uses. If on cooldown damage returned will be 0.
	*/
	public int getDigDamage() {
		// TODO: Make test for this
		if(digCount > digCooldown) {
			if (buff != null && buff.getType() == Buff.DIGGER)
				buff.durate();

			digCount = 0;
			return digDamage;
		}
		digCount++;
		return 0;
	}

	/**
	* Determines whether or not the player is colliding with the given block.
	* Will move the player in case collision is detected.
	* @param block must always have a size larger than or equal to that of this. 
	* @return true if player is currently colliding with the given obstacle.
	*/
	public boolean collidedWithBlock(Obstacle block) {
		// TODO: nicer direction-variable/s
		int	direction = -1;

		if (up)
			direction = 0;
		else if (right)
			direction = 1;
		else if (down)
			direction = 2;
		else if (left)
			direction = 3;

		if(block.inBounds(x, y)) {
			if (direction == 0)
				y = block.getDownSide();
			else if (direction == 3)
				x = block.getRightSide();
			return true;
		} 
		if(block.inBounds(x + SIZE, y)) {
			if (direction == 0)
				y = block.getDownSide();
			else if (direction == 1)
				x = block.getLeftSide() - SIZE;
			return true;
		} 
		if(block.inBounds(x, y + SIZE)) {
			if (direction == 2)
				y = block.getUpSide() - SIZE;
			else if (direction == 3)
				x = block.getRightSide();
			return true;
		} 
		if(block.inBounds(x + SIZE, y + SIZE)) {
			if (direction == 1)
				x = block.getLeftSide() - SIZE;
			else if (direction == 2)
				y = block.getUpSide() - SIZE;
			return true;
		}
		return false;
	}

	public boolean collidedWithBullet(Bullet bullet) {
		return bullet.inBounds(x, y, SIZE);
	}

	public boolean collidedWithBuff(Buff buff) {
		return buff.inBounds(x, y, SIZE);
	}

	/**
	* Helps identify which way to move the player and then adjusts the position accordingly.
	*/
	private void moveCornerOutsideBlock(Obstacle block, int cornerX, int cornerY) {
		int right = block.getRightSide() - cornerX;
		int left = block.getLeftSide() - cornerX;
		int up = block.getUpSide() - cornerY;
		int down = block.getDownSide() - cornerY;

		int xDiff = Math.abs(left) < Math.abs(right) ? left : right;
		int yDiff = Math.abs(up) < Math.abs(down) ? up : down;

		if(Math.abs(xDiff) < Math.abs(yDiff)) {
			x += xDiff;
		} else {
			y += yDiff;
		}
	}

	public boolean takeDamage(int damage) {
		if (shield) {
			buff.durate();
			return false;
		}

		health -= damage;
		return health <= 0;
	}

	public void paint(Graphics g) {
		image.paintIcon(null, g, x, y);

		if (shield) {
			g.setColor(lePink);
			g.fillOval(x, y, SIZE, SIZE);
		} else if (buff != null && buff.getType() == Buff.DIGGER) {
			g.setColor(Color.gray);
			g.fillOval(x, y, SIZE, SIZE);
		}
	}
}