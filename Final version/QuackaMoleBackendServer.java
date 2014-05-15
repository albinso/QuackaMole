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
public class QuackaMoleBackendServer extends ServerSocket {
	private GameLobby lobby;

	/**
	 * Initiates the backend server.
	 */
	public QuackaMoleBackendServer(int port) throws IOException {
		super(port);

		lobby = new GameLobby();

		lobby.start();

		findClient();
	}

	/**
	 * Waits for a client to connect, add it to the lobby and then repeats the process.
	 */
	public void findClient() throws IOException {
		while(true) {
			Socket socket = accept(); // find a socket (client)

			lobby.addClient(socket);
		}
	}
}