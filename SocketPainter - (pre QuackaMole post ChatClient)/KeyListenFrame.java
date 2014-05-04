import javax.swing.JFrame;

public class KeyListenFrame extends JFrame {
	private KeyListenPanel panel;

	public KeyListenFrame() {
		panel = new KeyListenPanel();

		add(panel);

		panel.repaint();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new KeyListenFrame();
	}
}