import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 * This class is used to make a general way of sending 
 * information through the streams.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public abstract class QuackaMolePackage implements Serializable {
	private final int playerID;

	public QuackaMolePackage(int playerID){
		this.playerID = playerID;
	}

	/**
	 * @return an unique ID that is used by both server and clients to 
	 *			identify the player on which the action is performed on/by.
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Action that is performed once package arrives. Will be performed on 
	 * player with ID playerID.
	 */
	public abstract void doAction(KeyListenPlayer p);
}