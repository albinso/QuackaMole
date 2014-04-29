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

	/**
	 * Starts the game (the timer)
	 */
	void startGame();

	/**
	 * Draws the gamefield with all its objects
	 */
	void draw(Graphics g);
}
