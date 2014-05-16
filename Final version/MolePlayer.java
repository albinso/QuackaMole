import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * A controllable player.
 * Able to destroy Obstacles by colliding with them.
 * Can spawn Bullets but will also take damage by colliding with Bullets spawned by other players.
 * Can be serialized and sent to server.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class MolePlayer implements Serializable {
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

	public MolePlayer(int x, int y, int id) {
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

	/**
	 * Forces the player's position to (x, y).
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Removes any currently used buffs and grants a new one.
	 */
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

	/**
	 * Performs regular checks on buffs and shooting.
	 */
	public void tick() {
		shootTick();
		buffTick();
	}

	/**
	 * Checks if buff has run out.
	 */
	private void buffTick()  {
		if (buff == null)
			return;

		if (buff.duration() == 0) {
			resetBuff();
			buff = null;
		}
	}

	/**
	 * Counts towards the next available shot.
	 */
	private void shootTick() {
		shootCount++;
	}

	/**
	 * Returns all values that can be altered by buffs to normal.
	 */
	private void resetBuff() {
		digDamage = 1;
		shield = false;
		movement = 1;
	}

	/**
	 * Sets player direction to up.
	 */
	public void setUp(boolean bool) {
		up = bool;
		moving = up || down || left || right;
		if(!moving) {
			up = !up;
		} 
		if(up) {
			right = false;
			left = false;
			down = false;
		}
	}

	/**
	 * Sets player direction to down.
	 */
	public void setDown(boolean bool) {
		down = bool;
		moving = up || down || left || right;
		if(!moving) {
			down = !down;
		} 
		if(down) {
			up = false;
			right = false;
			left = false;
		}
	}

	/**
	 * Sets player direction to left.
	 */
	public void setLeft(boolean bool) {
		left = bool;
		moving = up || down || left || right;
		if(!moving) {
			left = !left;
		} 
		if(left) {
			up = false;
			right = false;
			down = false;
		}
	}

	/**
	 * Sets player direction to right.
	 */
	public void setRight(boolean bool) {
		right = bool;
		moving = up || down || left || right;
		if(!moving) {
			right = !right;
		} 
		if(right) {
			up = false;
			left = false;
			down = false;
		}
	}

	/**
	 * Sets the movement in the given direction to keepMoving.
	 */
	public void setMoving(int direction, boolean keepMoving) {
		switch(direction) {
			case MovePackage.UP:
				setUp(keepMoving);
				break;
			case MovePackage.DOWN:
				setDown(keepMoving);
				break;
			case MovePackage.LEFT:
				setLeft(keepMoving);
				break;
			case MovePackage.RIGHT:
				setRight(keepMoving);
				break;
		}
	}

	/**
	 * Movement based on the direction variables.
	 */
	public void move() {
		// Reduces durability of speed buff.
		if (buff != null && buff.getType() == Buff.SPEEDER)
			buff.durate();

		if(!moving) {
			return;
		}
		if (up)
			y -= movement;
		else if (down)
			y += movement;
		else if (left)
			x -= movement;
		else if (right)
			x += movement;
	}

	/** 
	 * @return a Bullet which moves in the player's current facing direction.
	 */
	public Bullet shoot() {
		if(shootCount >= shootCooldown) {
			shootCount = 0;
			int adjustment = SIZE/2 - Bullet.SIZE/2;
			int direction = 0;
			if(up) {
				direction = 0;
			} else if(down) {
				direction = 1;
			} else if(left) {
				direction = 2;
			} else if(right) {
				direction = 3;
			}
			return new Bullet(x + adjustment, y + adjustment, direction, getID());
		}
		return null;
	}

	/**
	 * @return the x-value of the player
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y-value of the player
	 */
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
		if(block.inBounds(x, y)) {
			if (up)
				y = block.getDownSide();
			else if (left)
				x = block.getRightSide();
			return true;
		} 
		if(block.inBounds(x + SIZE, y)) {
			if (up)
				y = block.getDownSide();
			else if (right)
				x = block.getLeftSide() - SIZE;
			return true;
		} 
		if(block.inBounds(x, y + SIZE)) {
			if (down)
				y = block.getUpSide() - SIZE;
			else if (left)
				x = block.getRightSide();
			return true;
		} 
		if(block.inBounds(x + SIZE, y + SIZE)) {
			if (right)
				x = block.getLeftSide() - SIZE;
			else if (down)
				y = block.getUpSide() - SIZE;
			return true;
		}
		return false;
	}

	/**
	 * @return if the player has been hit by the bullet.
	 */
	public boolean collidedWithBullet(Bullet bullet) {
		return bullet.inBounds(x, y, SIZE);
	}

	/**
	 * @return if the player can pick up the buff.
	 */
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

	/**
	 * Takes damage.
	 * @return true if damage is lethal.
	 */
	public boolean takeDamage(int damage) {
		if (shield) {
			buff.durate();
			return false;
		}

		health -= damage;
		return health <= 0;
	}

	/**
	 * Paints the player
	 */
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