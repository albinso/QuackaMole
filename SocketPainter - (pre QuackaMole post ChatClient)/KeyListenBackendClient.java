import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Handles all actual communication with the server.
 * Will simply fetch and send objects.
 * @author Per Nyberg, Albin Söderholm
 */
public class KeyListenBackendClient extends Socket {
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private String name; // For future use.

	public KeyListenBackendClient(SocketAddress adr, String name) throws IOException {
		this.name = name;
		connect(adr);

		inputStream = new ObjectInputStream(getInputStream());
		outputStream = new ObjectOutputStream(getOutputStream());
	}

	/**
	 * Gets the next object that is sent from the server.
	 */
	public Object getObject() {
		// TODO: Make test for this
		Object o = null;
		try {
			o = inputStream.readObject();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Could not communicate with server. Shutting down.");
			System.exit(3);
		} catch(ClassNotFoundException e) {
			e.printStackTrace(); // Files are lacking or corrupt. A stackTrace is all we've got here.
			System.exit(4);
		}
		return o;
	}

	/**
	* Sends an object to the server. If nothing goes wrong it will be distributed between all other clients.
	*/
	public void sendObject(Object o) {
		try {
			outputStream.writeObject(o);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Could not communicate with server. Shutting down.");
			System.exit(3);
		}
	}
}
