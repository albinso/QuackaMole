import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyListenPackage implements Serializable {
	private final int playerID;
	private long timeSent;
	private final int content;
	private boolean pressing;
	public KeyListenPackage(int playerID, int content, boolean pressing){
		this.playerID = playerID;
		this.content = content;
		this.pressing = pressing;
		timeSent = System.nanoTime();
	}

	public int getPlayerID() {
		return playerID;
	}

	public int getContent() {
		return content;
	}

	public long getTime() {
		return timeSent;
	}

	/**
	* @return true if key is being pressed down. false if key is being released.
	*/
	public boolean isPressing() {
		return pressing;
	}
}