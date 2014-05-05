import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*; // TODO
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenPanel extends JPanel implements ActionListener, Serializable {
	private final int width = 500;
	private final int height = 300;
	private LinkedList<KeyListenPlayer> players;
	private Timer timer;
	private boolean updated;

	public KeyListenPanel() {
		players = new LinkedList<KeyListenPlayer>();
		timer = new Timer(10, this);
		updated = false;

		final KeyListenPanel panel = this;

		new Thread() {
			public void run() {
				int port = 8080; // the port used for the server
				try {
					new KeyListenServer(port, panel);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void addPlayer() {
		if (players.size() == 0)
			timer.start();

		int x = (int)(Math.random() * width);
		int y = (int)(Math.random() * height);
		players.add(new KeyListenPlayer(x, y));
		System.out.println("Player added");
		repaint();
	}

	public KeyListenPlayer getPlayer(int index) {
		return players.get(index);
	}

	public boolean updated() {
		if (!updated)
			return false;

		updated = false;
		return true;
	}

	public void actionHandling(KeyListenPackage pack) {
		System.out.println(pack.getPlayerID() + " " + pack.getContent());
		// Use KeyEvent.VK_UP etc.
		if (pack.getContent() == 37)
			players.get(0).goLeft();
		if (pack.getContent() == 38)
			players.get(0).goUp();
		if (pack.getContent() == 40)
			players.get(0).goDown();
		if (pack.getContent() == 39)
			players.get(0).goRight();
		if(!pack.isPressing()) {
			players.get(0).stopMoving();
		}
	}

	public void actionPerformed(ActionEvent e) {
		players.get(0).move();

		updated = true;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		for (KeyListenPlayer player : players)
			player.paint(g);
	}
}