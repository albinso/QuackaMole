import java.io.Serializable;

public class PlayerDeath implements Serializable {
	private int playerID;
	public PlayerDeath(int id) {
		this.playerID = id;
	}

	public int getID() {
		return playerID;
	}
}