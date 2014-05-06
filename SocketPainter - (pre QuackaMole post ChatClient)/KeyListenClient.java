import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.io.Serializable;

public class KeyListenClient extends JPanel implements KeyListener, Serializable {
	KeyListenBackendClient client;
	int playerID;
	private KeyListenPlayer[] players = new KeyListenPlayer[4]; // TODO: We don't want a hard coded 4 in here. We don't even want the client to have any say in the number of players.
	public KeyListenClient(InetSocketAddress adr) throws IOException{
		this.client = new KeyListenBackendClient(adr, "Rick Astley");
		this.playerID = client.getID();
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		new Thread() {
			public void run() {
				while(true) {
					Integer index = (Integer)(client.getObject());
					players[index] = (KeyListenPlayer)(client.getObject());
				}
			}
		}.start();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(KeyListenPlayer player : players) {
			if(player == null) {
				continue;
			}
			player.paint(g);
		}

		requestFocus();
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		KeyListenPackage p = new KeyListenPackage(playerID, e.getKeyCode(), true);
		client.sendObject(p);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		KeyListenPackage p = new KeyListenPackage(playerID, e.getKeyCode(), false);
		client.sendObject(p);
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
}