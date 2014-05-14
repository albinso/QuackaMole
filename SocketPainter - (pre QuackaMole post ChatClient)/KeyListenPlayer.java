import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class KeyListenPlayer implements Serializable {
	public static final long serialVersionUID = 41L;
	public int x, y;
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
		return block.inBounds(x, y);
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 20, 20);
		g.setColor(Color.black);
		g.drawRect(x, y, 20, 20);
	}
}