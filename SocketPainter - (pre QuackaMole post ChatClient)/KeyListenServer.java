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
public class KeyListenServer extends JFrame implements ActionListener {
	private JLabel portNumberLabel, serverStatus;
	private JPanel portNumberPanel;
	private JTextField portNumberField;
	private JPanel buttonPanel;
	private JButton startButton;

	public KeyListenServer() {
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

	public void startServer() {
		int portNumber = 0;
		try {
			String text = portNumberField.getText();
			portNumber = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		final int port = portNumber;

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
		if (e.getSource() == startButton) {
			if (serverStatus.getText().equals("Server offline")) {
				serverStatus.setText("Server online");
				startButton.setText("Stop server!");
				startServer();
			} else {
				serverStatus.setText("Server offline");
				startButton.setText("Start server!");
			}
		}
	}

	public static void main(String[] arg) {
		new KeyListenServer();
	}
}