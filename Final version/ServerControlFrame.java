import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * TODO: 
 * - fix portNumberLabel-position
 * - fix BindException (start, stop, start)
 */
/**
 * This class handles the GUI for the serverside.
 * It will accept a port number to listen on.
 * Control of both starting and closing the server is available.
 * @author Per Nyberg, Albin Söderholm
 */
public class ServerControlFrame extends JFrame implements ActionListener {
	private JLabel portNumberLabel, serverStatus;
	private JPanel portNumberPanel;
	private JTextField portNumberField;
	private JPanel buttonPanel;
	private JButton startButton;

	public ServerControlFrame() {
		serverStatus = new JLabel("Server offline");
		portNumberLabel = new JLabel("Port number");
		portNumberField = new JTextField("8080");
		portNumberPanel = new JPanel();

		buttonPanel = new JPanel();
		startButton = new JButton("Start server!");

 		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		portNumberField.setColumns(6);

		portNumberPanel.add(portNumberField);

		add(portNumberLabel);
		add(portNumberPanel);
		add(serverStatus);

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(buttonPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(250, 150);
		setVisible(true);
	}

	/**
	* Starts a server which will host the game.
	* 
	*/
	public void startServer() {
		int portNumber = 0;
		try {
			String text = portNumberField.getText();
			portNumber = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter a numerical port number.");
		}

		final int port = portNumber;

		new Thread() { 
			// By multithreading we allow for potential future support for multiple games on one host.
			public void run() {
				try {
					new QuackaMoleBackendServer(port);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Something went wrong. Please restart the server.");
				}
			}
		}.start();
	}

	/**
	* Performed on clicking the "Start server!" or "Stop server!" buttons.
	*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			if (serverStatus.getText().equals("Server offline")) {
				serverStatus.setText("Server online");
				startButton.setText("Stop server!");
				startServer();
			} else { // Kills the server and allows for a new instance to be created.
				MAKE THIS WORK
				serverStatus.setText("Server offline");
				startButton.setText("Start server!");
			}
		}
	}

	public static void main(String[] arg) {
		new KeyListenServer();
	}
}