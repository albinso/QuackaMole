import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Asks user for ip and port number of the desired server.
 * Will allow the user to request a connection to said server and will, if successful, join or start a game.
 * If request fails a popup will appear with information and the user may try new input.
 *
 * @author Per Nyberg, Albin Söderholm
 */
public class QuackaMoleClient extends JFrame implements ActionListener {
	private JLabel usernameLabel, serverAddressLabel, portNumberLabel;
	private JPanel usernamePanel, serverAddressPanel, portNumberPanel;
	private JTextField usernameField, serverAddressField, portNumberField;
	private JPanel buttonPanel;
	private JButton startButton;

	/**
	 * Creates labels for username, server address and port number.
	 * The constructor also creates textfields placed on panels with
	 * some default data for the user.
	 * They are all placed out using BoxLayout along the Y-axis.
	 */
	public QuackaMoleClient() {
		usernameLabel = new JLabel("Username");
		serverAddressLabel = new JLabel("Server address");
		portNumberLabel = new JLabel("Port number");

		usernameField = new JTextField("Per");
		serverAddressField = new JTextField("localhost");
		portNumberField = new JTextField("8080");

		usernamePanel = new JPanel();
		serverAddressPanel = new JPanel();
		portNumberPanel = new JPanel();

		buttonPanel = new JPanel();
		startButton = new JButton("Connect to server!");

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		usernameField.setColumns(9);
		serverAddressField.setColumns(11);
		portNumberField.setColumns(6);

		usernamePanel.add(usernameField);
		serverAddressPanel.add(serverAddressField);
		portNumberPanel.add(portNumberField);

		add(usernameLabel);
		add(usernamePanel);
		add(serverAddressLabel);
		add(serverAddressPanel);
		add(portNumberLabel);
		add(portNumberPanel);

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(buttonPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(200, 250);
		setVisible(true);
	}

	/**
	 * Attempts to connect to a server using the values given by the user.
	 * If failed a popup will inform the user of this and the attempt will be aborted.
	 */
	public void connectToServer() {
		JFrame frame = new JFrame("QuackaMole!");
		String ipAddress = serverAddressField.getText();
		int port = 0;

		// get the port number.
		try {
			String portNumberText = portNumberField.getText();
			port = Integer.parseInt(portNumberText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter a numerical port number.");
			return;
		}

		InetSocketAddress adr = new InetSocketAddress(ipAddress, port);

		try {
			frame.add(new ClientGamePanel(adr));
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find host using given ip and port number.");
			return;
		}

		// fixes the attributes for the frame
		frame.setSize(1200, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.requestFocus();

		// hides the connect-window
		setVisible(false);
	}

	/**
	 * Checks if the startbutton has been pressed
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton)
			connectToServer();
	}

	/**
	 * The engine that starts the client
	 */
	public static void main(String[] args) {
		new QuackaMoleClient();
	}
}