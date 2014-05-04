import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import java.io.*; // DEBUGG

public class TestClient extends Socket {
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private String name;

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

		new Thread() {
			public void run() {
				while(true) {
					try {
						System.out.println(inputStream.readObject());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		run();
	}

	public void run() {
		while(true) {
			try {
				String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
				outputStream.writeObject(name + ": " + s);
//				Thread.sleep(1000);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(-1);
//			} catch(InterruptedException e) {
//				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		InetSocketAddress adr = new InetSocketAddress("127.0.0.1", 8080);

		new TestClient(adr, args[0]);
	}
}
