import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles the backend of the server
 * which in this cases means starting the lobby
 * and endlessly trying to find clients to join
 * the lobby.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class KeyListenServerBackend extends ServerSocket {
	private KeyListenLobby lobby;

	/**
	 * Initiates the backend server
	 */
	public KeyListenServerBackend(int port) throws IOException {
		super(port);

		lobby = new KeyListenLobby();

		lobby.start(); // starts the lobby
		findClient();
	}

	/**
	* Waits for a client to connect, add it to the lobby and then repeats the process.
	*/
	public void findClient() throws IOException {
		while(true) {
			Socket s = accept(); // find a socket (client)
			System.out.println("Found a client, connecting!");

			lobby.addClient(s);
		}
	}
}