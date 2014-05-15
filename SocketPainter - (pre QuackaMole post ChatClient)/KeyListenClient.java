import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyListenClient extends JFrame implements ActionListener {
	final int port = 8080; // the port used for the server

	private JLabel usernameLabel, serverAddressLabel, portNumberLabel;
	private JPanel usernamePanel, serverAddressPanel, portNumberPanel;
	private JTextField usernameField, serverAddressField, portNumberField;
	private JPanel buttonPanel;
	private JButton startButton;

	public KeyListenClient() {
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


	public static void main(String[] args) {
		JFrame frame = new JFrame("Send key inputs");
		InetSocketAddress adr = new InetSocketAddress("192.168.0.100", 8080);
		frame.setSize(600, 400);
		if(args.length == 2) {
			adr = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		}
		try {
			frame.add(new KeyListenClientBackend(adr));
		} catch(IOException e) {
			System.exit(1);
		}
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.requestFocus();
	}
}