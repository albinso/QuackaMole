import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer extends ServerSocket {
	private TestLobby lobby;
	public TestServer(int port) throws IOException {
		super(port);

		lobby = new TestLobby();

		lobby.start(); // starts the lobbyyy

		findClient();
	}

	public void findClient() throws IOException {
		while(true) {
			Socket s = accept(); // find a socket (client)
			System.out.println("Found a client, connecting!");

			lobby.addClient(s);
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 8080; // the port used for the server
		new TestServer(port);
	}
}