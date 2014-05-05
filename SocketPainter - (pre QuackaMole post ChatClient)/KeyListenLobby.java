import java.io.*; // TODO
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import java.util.*; // DEBUGG

public class KeyListenLobby extends Thread implements Serializable {
	private ArrayList<ObjectOutputStream> outputList;
	private Queue<KeyListenPackage> queue;
/*DEBUGG*/private KeyListenPanel panel;

	public KeyListenLobby(KeyListenPanel panel) {
/*DEBUGG*/this.panel = panel;
		outputList = new ArrayList<ObjectOutputStream>();
		queue = new LinkedList<KeyListenPackage>();
	}

	public void addClient(final Socket socket) {
		try {
			outputList.add(new ObjectOutputStream(socket.getOutputStream()));
/*DEBUGG*/	Integer id = panel.addPlayer();
			outputList.get(outputList.size() - 1).writeObject(id); // TODO: Make this look good.
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					while (true) {
						queue.add((KeyListenPackage)inputStream.readObject());
						/**
						 * Möjligtvis göra alla förflyttningsgrejer här?
						 */
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void run() {
		new Thread() {
			public void run() {
				while (true) {
					if (!panel.updated())
						continue;
					try {
						for (int i = 0 ; i < outputList.size() ; i++) {
							outputList.get(i).reset();
							outputList.get(i).writeObject(panel.getPlayer(0));
							System.out.println(panel.getPlayer(0).x);
							outputList.get(i).flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
	//				} catch (InterruptedException e) {
	//					e.printStackTrace();
					}
				}
			}
		}.start();

		while(true) {
			System.out.print(""); // BUGG-CODE
			while (queue.peek() != null) {
				KeyListenPackage poll = queue.poll();
/*DEBUGG*/		panel.actionHandling(poll);
 			}
 		}
 	}
}