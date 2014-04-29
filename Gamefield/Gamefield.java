import javax.swing.*;

interface Gamefield extends JPanel {
	private int width, height;

	Gamefield(int width, int height) {
		this.width = width;
		this.height = height;
	}

	void intiGame();

	void initField();

	void startGame();

	void paintComponent(Graphics g);
}