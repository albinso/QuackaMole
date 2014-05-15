import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class KeyListenServer extends ServerSocket implements Serializable {
	private KeyListenLobby lobby;

	public KeyListenServer(int port) throws IOException {
		super(port);

		lobby = new KeyListenLobby();

		lobby.start(); // starts the lobbyyy

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