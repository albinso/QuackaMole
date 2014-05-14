import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class KeyListenPlayer implements Serializable {
	public static final long serialVersionUID = 41L;
	public int x, y;
	private final int SIZE = 30;
	private int id;
	private Buff buff;
	private transient boolean up, down, left, right;

	public KeyListenPlayer(int x, int y, int id) {
		setPosition(x, y);
		up = false;
		down = false;
		left = false;
		right = false;
		this.id = id;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setBuff(Buff buff) {
		this.buff = buff;
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
	}

	public void goDown() {
		up = false;
		down = true;
		left = false;
		right = false;
	}

	public void goLeft() {
		up = false;
		down = false;
		left = true;
		right = false;
	}

	public void goRight() {
		up = false;
		down = false;
		left = false;
		right = true;
	}

	/**
	* Stops all movement;
	*/
	public void stopMoving() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

	/**
	* Moves based on the direction variables.
	*/
	public void move() {
		if (up)
			y--;
		if (down)
			y++;
		if (left)
			x--;
		if (right)
			x++;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	* @return an id that is used to identify players in different locations.
	*/
	public int getID() {
		return id;
	}

	/**
	* @return true if player is currently colliding with the given obstacle.
	*/
	public boolean collided(Obstacle block) {
		if(block.inBounds(x, y)) {
			moveCornerOutsideBlock(block, x, y);
			return true;
		} 
		if(block.inBounds(x + SIZE, y)) {
			moveCornerOutsideBlock(block, x + SIZE, y);
			return true;
		} 
		if(block.inBounds(x, y + SIZE)) {
			moveCornerOutsideBlock(block, x, y + SIZE);
			return true;
		} 
		if(block.inBounds(x + SIZE, y + SIZE)) {
			moveCornerOutsideBlock(block, x + SIZE, y + SIZE);
			return true;
		}
		return false;
	}

	/**
	* Helps identify which way to move the player and then adjusts the position accordingly.
	*/
	private void moveCornerOutsideBlock(Obstacle block, int cornerX, int cornerY) {
		int right = block.getRightSide() - cornerX;
		int left = block.getLeftSide() - cornerX;
		int up = block.getUpSide() - cornerY;
		int down = block.getDownSide() - cornerY;

		int xDiff = Math.abs(left) < right ? left : right;
		int yDiff = Math.abs(up) < down ? up : down;

		if(Math.abs(xDiff) < Math.abs(yDiff)) {
			x += xDiff;
		} else {
			y += yDiff;
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawRect(x, y, SIZE, SIZE);
	}
}