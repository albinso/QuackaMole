import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.io.Serializable;

public class KeyListenClient extends JPanel implements KeyListener, Serializable {
	TestClient client;
	KeyListenPlayer field = null;
	public KeyListenClient(InetSocketAddress adr) throws IOException{
		this.client = new TestClient(adr, "Rick Astley");
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		new Thread() {
			public void run() {
				while(true) {
					field = (KeyListenPlayer)(client.getObject());
				}
			}
		}.start();
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Send key inputs");
		InetSocketAddress adr = new InetSocketAddress("192.168.0.100", 8080);
		frame.setSize(600, 400);
		if(args.length == 2) {
			adr = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		}
		try {
			frame.add(new KeyListenClient(adr));
		} catch(IOException e) {
			System.exit(1);
		}
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.requestFocus();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(field != null) {
			field.paint(g);
		}
		requestFocus();
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		KeyListenPackage p = new KeyListenPackage(0, e.getKeyCode(), true);
		client.sendObject(p);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		KeyListenPackage p = new KeyListenPackage(0, e.getKeyCode(), false);
		client.sendObject(p);
	}
}