import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*; // TODO
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenPanel extends JPanel implements ActionListener, Serializable {
	public static final long serialVersionUID = 50L;
	private int width;
	private int height;
	private LinkedList<KeyListenPlayer> players;
	private LinkedList<Obstacle> obstacles;
	private Timer timer;
	private boolean updated;

	public KeyListenPanel() {
		players = new LinkedList<KeyListenPlayer>();
		obstacles = new LinkedList<Obstacle>();
		timer = new Timer(10, this);
		updated = false;

		initMap();

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

	public void initMap() {
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		int y = 0;
		int x = 0;
		while(fileScanner.hasNext()) {
			x = 0;
			Scanner lineScanner = new Scanner(fileScanner.nextLine());
			while(lineScanner.hasNext()) {
				String token = lineScanner.next();
				if (token.equals("1")) {
					obstacles.add(new KeyListenDirt(x, y));
				} else if (token.equals("2")) {
					obstacles.add(new KeyListenStone(x, y));
				}
				x += Obstacle.SIZE;
			}
			y += Obstacle.SIZE;
		}

		width = x;
		height = y;
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
			player.move(); // TODO: kollar movement mot hinder

		updated = true;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		for (KeyListenPlayer player : players)
			player.paint(g);
		for (Obstacle obstacle : obstacles)
			obstacle.paint(g);
	}
}