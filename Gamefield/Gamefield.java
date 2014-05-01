import java.awt.*;
import java.io.*;

interface Gamefield {
	void initGame();

	void initField(File mapFile);

	void startGame();

	void paintComponent(Graphics g);
}