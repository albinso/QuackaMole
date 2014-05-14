import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*; // TODO
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Scanner;

public class KeyListenPanel extends JPanel implements ActionListener, Serializable {
	private int width;
	private int height;
	private LinkedList<KeyListenPlayer> players;
	private LinkedList<Obstacle> obstacles;
	private LinkedList<StartPlace> startPlaces;
	private Timer timer;
	private boolean updated;

	public KeyListenPanel() {
		players = new LinkedList<KeyListenPlayer>();
		obstacles = new LinkedList<Obstacle>();
		startPlaces = new LinkedList<StartPlace>();

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
		File mapFile = new File("map.txt");
		Scanner fileScanner = null;

		try {
			fileScanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-2);
		}

		int x = 0;
		int y = 0;
		while (fileScanner.hasNext()) {
			Scanner lineScanner = new Scanner(fileScanner.nextLine());
			x = 0;
			while (lineScanner.hasNext()) {
				String token = lineScanner.next();

				if (token.equals("1"))
					obstacles.add(new KeyListenDirt(x, y));
				else if (token.equals("2"))
					obstacles.add(new KeyListenStone(x, y));
				else if (token.equals("3"))
					startPlaces.add(new StartPlace(x, y));

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
		players.add(new KeyListenPlayer(x, y, players.size()));

		// DEBUGG
		System.out.println("Player added");

		repaint();

		return players.size() - 1;
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
		for (Obstacle obstacle : obstacles)
			obstacle.paint(g);
		for (StartPlace startPlace : startPlaces)
			startPlace.paint(g);
	}
}