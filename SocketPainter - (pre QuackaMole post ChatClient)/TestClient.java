import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import java.io.*; // DEBUGG

// TODO rename to KeyListenBackendClient
public class TestClient extends Socket {
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private String name;

	// DEBUGG
	private int id;

	public TestClient(SocketAddress adr, String name) throws IOException {
		this.name = name;
		try {
			connect(adr);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		inputStream = new ObjectInputStream(getInputStream());
		outputStream = new ObjectOutputStream(getOutputStream());

		try {
			id = (int)(inputStream.readObject()); // possibly cast Integer
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

// 	public void run() {
// 		while(true) {
// 			try {
// 				String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
// 				outputStream.writeObject(name + ": " + s);
// //				Thread.sleep(1000);
// 			} catch(IOException e) {
// 				e.printStackTrace();
// 				System.exit(-1);
// //			} catch(InterruptedException e) {
// //				e.printStackTrace();
// 			}
// 		}
// 	}

	public Object getObject() {
		Object o = null;
		try {
			o = inputStream.readObject();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return o;
	}

	public void sendObject(Object o) {
		try {
			outputStream.writeObject(o);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public int getID() {
		return id;
	}

/*	public static void main(String[] args) throws IOException {
		InetSocketAddress adr = new InetSocketAddress("192.168.0.100", 8087);

		new TestClient(adr, args[0]);
	}*/
}
