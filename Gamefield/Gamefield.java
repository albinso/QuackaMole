import java.awt.*;

interface Gamefield {
	/**
	 * Initiates the players and the game
	 */
	void intiGame();

	/**
	 * Initiates the field
	 */
	void initField();

	void startGame();

	void draw(Graphics g);
}
