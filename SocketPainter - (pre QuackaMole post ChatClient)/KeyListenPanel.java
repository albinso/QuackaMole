import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*; // TODO
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenPanel extends JPanel implements Serializable {
	public int width;
	public int height;
	private ImageIcon gamefield;
	
	private LinkedList<Obstacle> obstacles;
	public LinkedList<StartPlace> startPlaces;
	private boolean updated;

	public KeyListenPanel() {
		obstacles = new LinkedList<Obstacle>();
		startPlaces = new LinkedList<StartPlace>();
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

	/**
	* This is bad and should be removed.
	* "Initialises" the map. This means an ImageIcon is retrieved and obstacles are generated from a map.
	*/
	public LinkedList<Obstacle> initMap() {
		LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
		Image gamefieldImage = Toolkit.getDefaultToolkit().getImage("stripes.jpg");
		gamefield = new ImageIcon(gamefieldImage);

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

				if (token.equals("#"))
					obstacles.add(new KeyListenDirt(x, y));
				else if (token.equals("*"))
					obstacles.add(new KeyListenStone(x, y));
				else if (isNumeric(token))
					startPlaces.add(new StartPlace(x, y));
				else if (token.equals("$"))
					obstacles.add(new KeyListenCrate(x, y));

				x += Obstacle.SIZE;
			}
			y += Obstacle.SIZE;
		}

		width = x;
		height = y;
		return obstacles;
	}

	private boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	/**
	* if not updated return false
	* if updated, set updated to false and return true
	*/
	public boolean updated() {
		if (!updated)
			return false;

		updated = false;
		return true;
	}

	public LinkedList<Obstacle> getObstacles() {
		return obstacles;
	}

	public ImageIcon getImage() {
		return gamefield;
	}

	// TODO: bör inte ritas upp (egentligen)
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		gamefield.paintIcon(null, g, 0, 0);

		for (Obstacle obstacle : obstacles)
			obstacle.paint(g);
		for (StartPlace startPlace : startPlaces)
			startPlace.paint(g);

//		repaint();
	}
}