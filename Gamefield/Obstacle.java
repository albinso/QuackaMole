public class Obstacle {
	private int x, y, width, height;

	Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	int getLeftSide() {
		return x;
	}

	int getRightSide() {
		return x + width;
	}

	int getUpSide() {
		return y;
	}

	int getDownSide() {
		return y + height;
	}

	boolean inBounds(int x, int y) {
		return this.x <= x && x < (this.x + width) && 
				this.y <= y && y < (this.y + height);
	}
}