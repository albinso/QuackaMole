import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class KeyListenPlayer implements Serializable {
	public int x, y;
	private transient boolean up, down, left, right;

	public KeyListenPlayer(int x, int y) {
		this.x = x;
		this.y = y;
		up = false;
		down = false;
		left = false;
		right = false;
	}

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

	public void stopMoving() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

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

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 20, 20);
		g.setColor(Color.black);
		g.drawRect(x, y, 20, 20);
	}
}