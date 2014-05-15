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

public class KeyListenServer extends JFrame implements ActionListener {
	final int port = 8080; // the port used for the server

	private JLabel usernameLabel, serverAddressLabel, portNumberLabel;
	private JPanel usernamePanel, serverAddressPanel, portNumberPanel;
	private JTextField usernameField, serverAddressField, portNumberField;
	private JPanel buttonPanel;
	private JButton startButton;

	public KeyListenServer() {
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
		startButton = new JButton("Start server!");

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		usernameField.setPreferredSize(new Dimension(50, 20));

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
		setSize(200, 100);
		setVisible(true);
	}

	public void startServer() {
		new Thread() {
			public void run() {
				try {
					new KeyListenServerBackend(port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton)
			startServer();
	}

	public static void main(String[] arg) {
		new KeyListenServer();
	}
}