import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*; // TODO
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KeyListenPanel extends JPanel implements Serializable {
	public KeyListenPanel() {
		// a new thread that handles the server
		new Thread() {
			public void run() {
				int port = 8080; // the port used for the server
				try {
					new KeyListenServer(port);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}