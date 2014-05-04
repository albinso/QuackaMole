import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class QuackaMole extends JFrame implements ActionListener {
	private QuackGamefield gamefield;
	private JPanel buttonPanel;
	private JButton startButton;

/** DEBUGG */
	File mapFile = new File("QuackaText.txt");

	public QuackaMole() {
		gamefield = new QuackGamefield(30, mapFile);
		buttonPanel = new JPanel();
		startButton = new JButton("Start Quacking!");

		startButton.addActionListener(this);

		buttonPanel.add(startButton);		

		add(gamefield);
		add(buttonPanel, BorderLayout.SOUTH);

		setSize(800, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			gamefield.startGame();
			gamefield.requestFocus();
		}
	}

	public static void main(String[] arg) {
		new QuackaMole();
	}
}