import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;

public class QuackGamefield extends JPanel implements Gamefield, ActionListener {
	private Timer timer;
	private File mapFile;

	private int width, height;
	private int obstacleWidth, obstacleHeight, obstacleSize;

	private LinkedList<Obstacle> obstacles;

	public QuackGamefield(int width, int height, int obstacleWidth, int obstacleHeight, int obstacleSize, File mapFile) {
		timer = new Timer(10, this);
		this.mapFile = mapFile;

		this.width = width;
		this.height = height;
		this.obstacleWidth = obstacleWidth;
		this.obstacleHeight = obstacleHeight;
		this.obstacleSize = obstacleSize;

		initGame();
	}

	public void initGame() {
		obstacles = new LinkedList<Obstacle>();

		initField();
	}

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
		for (int rowIndex = 0 ; scanner.hasNext() ; rowIndex++)
			readRow(rowIndex, scanner.nextLine());
	}

	// reads the line and creates obstacles from the information given
	private void readRow(int rowIndex, String line) {
		Scanner scanner = new Scanner(line);

		for (int columnIndex = 0 ; scanner.hasNext() ; columnIndex++) {
			String s = scanner.next();
			int x = columnIndex * obstacleSize;
			int y = rowIndex * obstacleSize;
			if (s.equals("1"))
				obstacles.add(new Obstacle(x, y, obstacleWidth, obstacleHeight));
		}
	}

	// starts the timer (and therefore the game)
	public void startGame() {
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {

	}

	// pains the gamefield
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// paint the field

		// paint the "on-field" object
		for (Obstacle obstacle : obstacles)
			obstacle.draw(g);
		// paint players
		// 
	}
}