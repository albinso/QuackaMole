import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyListenPackage implements Serializable {
	private final int playerID;
	private long timeSent;
	private final int content;
	public KeyListenPackage(int playerID, int content){
		this.playerID = playerID;
		this.content = content;
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
}