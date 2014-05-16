import javax.swing.JOptionPane;

/**
 * Provides a standardised way to handle error notifications.
 *
 * @author Per Nyberg, Albin Söderholm
 */
public class ErrorHandling {
	public static void exit(String message, int exitCode) {
		JOptionPane.showMessageDialog(null, message);
		System.exit(exitCode);
	}
}