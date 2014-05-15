import java.io.Serializable;

/**
 * This class is used to send a signal to  all the clients
 * telling them that the player specified by the ID is dead.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class PlayerDeath implements Serializable {
	private int playerID;

	public PlayerDeath(int id) {
		this.playerID = id;
	}

	/**
	 * @return id for the dead player.
	 */
	public int getID() {
		return playerID;
	}
}