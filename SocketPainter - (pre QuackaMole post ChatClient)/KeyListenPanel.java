import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*; // TODO
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenPanel extends JPanel implements ActionListener, Serializable {
	public static final long serialVersionUID = 50L;
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

		// a new thread that handles the server
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

	// called by the server when a new player has connected
	public int addPlayer() {
		// if the game hasen't started yet
		// TODO start timer when all players are ready?
		if (players.size() == 0)
			timer.start();

		// creates a player (with random coordinates)
		// TODO set position realtive to the map
		int x = (int)(Math.random() * width);
		int y = (int)(Math.random() * height);
		int id = players.size();
		players.add(new KeyListenPlayer(x, y, id));

		// DEBUGG
		System.out.println("Player added");

		repaint();

		return id;
	}

	public KeyListenPlayer getPlayer(int index) {
		return players.get(index);
	}

	// if not updated return false
	// if updated, set updated to false and return true
	public boolean updated() {
		if (!updated)
			return false;

		updated = false;
		return true;
	}


	public void actionPerformed(ActionEvent e) {
		for (KeyListenPlayer player : players)
			player.move();

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