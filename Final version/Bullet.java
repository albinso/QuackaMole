import java.awt.Graphics;
import java.io.Serializable;

/**
 * The bullets is used to kill other players
 * and moves in 90-degree directions doing so.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Bullet implements Serializable {
	public final static int SIZE = 15;
	private final int SPEED = 2;
	private final int DAMAGE = 100;

	private int x, y;
	private int direction;
	private int ownerID;

	public Bullet(int x, int y, int direction, int ownerID) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.ownerID = ownerID;
	}

	/**
	 * @return the x-value of the bullet.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y-value of the bullet.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the size of the bullet.
	 */
	public int getSize() {
		return SIZE;
	}

	/**
	 * @return if the given player is the shooter of the bullet.
	 */
	public boolean isOwner(KeyListenPlayer p) {
		return p.getID() == ownerID;
	}

	/**
	* @return if the square defined by the params contains any of the bullet's corners.
	*/
	public boolean inBounds(int playerX, int playerY, int size) {
		return (betweenValues(x, playerX, playerX + size) || betweenValues(x + SIZE, playerX, playerX + size))
			&& (betweenValues(y, playerY, playerY + size) || betweenValues(y + SIZE, playerY, playerY + size));
	}

	/**
	 * Help-method for inBounds. Decides if a value is between two others.
	 */
	private boolean betweenValues(int value, int low, int high) {
		return low < value && value < high;
	}

	/**
	 * @return if the bullet has collided with a block.
	 */
	public boolean collidedWithBlock(Obstacle obs) {
		return obs.inBounds(x, y) || obs.inBounds(x + SIZE, y) || 
			   obs.inBounds(x, y + SIZE) || obs.inBounds(x + SIZE, y + SIZE);
	}

	/**
	 * Moves the player in the given speed if the player is active.
	 */
	public void move() {
		if(up) {
			y -= SPEED;
		}
		if(down) {
			y += SPEED;
		}
		if(left) {
			x -= SPEED;
		}
		if(right) {
			x += SPEED;
		}
	}

	/**
	 * @return the damage of the bullet.
	 */
	public int getDamage() {
		return DAMAGE;
	}

	/**
	 * @return information about who shot the bullet.
	 */
	public String toString() {
		return "Fired by: " + ownerID;
	}

	/**
	 * Paints the bullet.
	 */
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x, y, SIZE, SIZE);
	}
}