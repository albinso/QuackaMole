import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyListenLoginWindow extends JFrame {
	private JButton login;
	private JTextField name, ipadress, port;
	private JPanel panel;

	public KeyListenLoginWindow() {
		panel = new JPanel();
		name = new JTextField();
		ipadress = new JTextField();
		port = new JTextField();
		login = new JButton("Log in");

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(name);
		panel.add(ipadress);
		panel.add(port);
		panel.add(login);

		add(panel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(100, 200);
		setVisible(true);
	}

	public static void main(String[] arg) {
		new KeyListenLoginWindow();
	}
}