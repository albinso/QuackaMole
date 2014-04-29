import java.awt.*;

public class Obstacle {
	private int x, y, width, height;

	Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * The left/west side of the obstacle
	 */
	int getLeftSide() {
		return x;
	}

	/**
	 * The right/east side of the obstacle
	 */
	int getRightSide() {
		return x + width;
	}

	/**
	 * The up/north side of the obstacle
	 */
	int getUpSide() {
		return y;
	}

	/**
	 * The down/south side of the obstacle
	 */
	int getDownSide() {
		return y + height;
	}

	/**
	 * If given point is in the obstacle
	 */
	boolean inBounds(int x, int y) {
		return this.x <= x && x < (this.x + width) && 
				this.y <= y && y < (this.y + height);
	}

	/**
	 * Draws the obstacle
	 */
	void draw(Graphics g) {
		super.paintComponent(g);
	}
}
