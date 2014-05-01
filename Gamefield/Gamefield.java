import java.awt.*;
import java.awt.event.*;
import java.io.*;

interface Gamefield {
	void initGame();

	void initField();

	void startGame();

	void actionPerformed(ActionEvent e);

	void paintComponent(Graphics g);
}