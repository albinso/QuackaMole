
import java.awt.event.KeyEvent;
import java.io.Serializable;

public abstract class KeyListenPackage implements Serializable {
	private final int playerID;
	public KeyListenPackage(int playerID){
		this.playerID = playerID;
	}

	/**
	* @return an unique ID that is used by both Server and Clients to identify the player on which the action is performed on/by.
	*/
	public int getPlayerID() {
		return playerID;
	}

	/**
	* Action that is performed once package arrives. Will be performed on player with ID playerID.
	*/
	public abstract void doAction(KeyListenPlayer p);
}