import javax.swing.JFrame;
import java.io.IOException;

public class KeyListenServer extends JFrame {
	final int port = 8080; // the port used for the server

	public KeyListenServer() {
		startServer();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}

	public void startServer() {
		new Thread() {
			public void run() {
				try {
					new KeyListenServerBackend(port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void main(String[] arg) {
		new KeyListenServer();
	}
}