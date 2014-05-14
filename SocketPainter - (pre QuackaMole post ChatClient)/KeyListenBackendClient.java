import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class KeyListenBackendClient extends Socket {
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private String name;

	// DEBUGG
	private int id;

	public KeyListenBackendClient(SocketAddress adr, String name) throws IOException {
		this.name = name;
		try {
			connect(adr);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		inputStream = new ObjectInputStream(getInputStream());
		outputStream = new ObjectOutputStream(getOutputStream());
	}

	/**
	* Gets the next object that is sent from the server.
	*/
	public Object getObject() {
		Object o = null;
		try {
			o = inputStream.readObject();
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(3);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			System.exit(5);
		}
	}
}
