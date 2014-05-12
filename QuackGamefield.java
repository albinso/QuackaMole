import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
import quack.block.Obstacle;
import quack.player.Player;

public class QuackGamefield extends JPanel implements Gamefield, ActionListener, KeyListener {
	private Timer timer;
	private File mapFile;

	private int width, height;
	private int obstacleSize;

	private QuackPlayer player;
	private LinkedList<Obstacle> obstacles;

	public QuackGamefield(int obstacleSize, File mapFile) {
		timer = new Timer(1000, this);
		this.mapFile = mapFile;

		this.obstacleSize = obstacleSize;
		Obstacle.size = obstacleSize;

		addKeyListener(this);

		initGame();
		repaint();
	}

	// initiates objects and the field
	public void initGame() {
		obstacles = new LinkedList<Obstacle>();

		initField();
		initPlayers();
	}

	// initiate the player/s
	public void initPlayers() {
		player = new QuackPlayer(90, 90, Player.RIGHT);
	}	

	// initiates the field through scanning a file
	public void initField() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be found, please check that the adress is right");
			System.exit(1);
		}

		readFile(scanner);
	}

	// reads all the lines in the document
	private void readFile(Scanner scanner) {
		int rowIndex = 0;
		for ( ; scanner.hasNext() ; rowIndex++)
			readRow(rowIndex, scanner.nextLine());
		height = rowIndex * obstacleSize;
	}

	// reads the line and creates obstacles from the information given
	private void readRow(int rowIndex, String line) {
		Scanner scanner = new Scanner(line);

		int columnIndex = 0;
		for ( ; scanner.hasNext() ; columnIndex++) {
			String s = scanner.next();
			int x = columnIndex * obstacleSize;
			int y = rowIndex * obstacleSize;
			if (s.equals("1")) {
				obstacles.add(new Wall(x, y));
			} else if (s.equals("0")) {
				obstacles.add(new Dirt(x, y));
			}
		}
		width = columnIndex * obstacleSize;
	}

	// starts the timer (and therefore the game)
	public void startGame() {
		timer.start();
	}

	// every time the timer ticks
	public void actionPerformed(ActionEvent e) {
		player.move();
		System.out.println(timer);
		repaint();
	}

	// => vi behöver ha det på detta sättet (enligt min modell) för att det ska gå att spela utan "lagg"
	private void keyPressHelper(KeyEvent e, boolean press) {
		player.down = false;
		player.up = false;
		player.left = false;
		player.right = false;
		if (e.getKeyChar() == 'd') {
			player.right = press;
		}
		if (e.getKeyChar() == 's') {
			player.down = press;
		}
		if (e.getKeyChar() == 'a') {
			player.left = press;
		}
		if (e.getKeyChar() == 'w') {
			player.up = press;
		}
	}

	public void keyPressed(KeyEvent e) {
		keyPressHelper(e, true);
	}

	public void keyReleased(KeyEvent e) {
		keyPressHelper(e, false);
	}

	public void keyTyped(KeyEvent e) {
	}

	// pains the gamefield
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// paint the field
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		// paint the "on-field" object
		for (Obstacle obstacle : obstacles)
			obstacle.draw(g);
		// paint players
		player.draw(g);
	}
}