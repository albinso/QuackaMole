import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * This class represents the spawn points for the players.
 * It is created from the file map.txt and is thereafter 
 * stored in a list until it's used and is then finally 
 * removed from the list.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class SpawnPoint implements Serializable {
	private int x, y, startNumber;

	public SpawnPoint(int x, int y, int startNumber) {
		this.x = x;
		this.y = y;
		this.startNumber = startNumber;
	}

	/**
	 * @return the x-value of the spawnpoint.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y-value of the spawnpoint.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the id-number of the spawnpoint.
	 */
	public int getStartNumber() {
		return startNumber;
	}
}