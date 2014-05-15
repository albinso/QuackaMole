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
		buttonPanel = new JPanel();
		startButton = new JButton("Start server!");

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		add(usernameLabel);
		add(usernameField);
//		add(serverAdressLabel);
//		add(serverAdressField);

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(buttonPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
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