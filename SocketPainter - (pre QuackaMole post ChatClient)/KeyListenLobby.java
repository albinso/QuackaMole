import java.io.*; // TODO
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import java.util.*; // DEBUGG

public class KeyListenLobby extends Thread implements Serializable {
	private static String IMG_PATH;
	private ArrayList<ObjectOutputStream> outputList;
	private Queue<Object> queue;
/*DEBUGG*/private KeyListenPanel panel;

	public KeyListenLobby(KeyListenPanel panel) {
/*DEBUGG*/this.panel = panel;
		outputList = new ArrayList<ObjectOutputStream>();
		queue = new LinkedList<Object>();
	}

	public void addClient(final Socket socket) {
		try {
			outputList.add(new ObjectOutputStream(socket.getOutputStream()));
/*DEBUGG*/	Integer id = panel.addPlayer();
			outputList.get(outputList.size() - 1).writeObject(id); // TODO: Make this look good.
			outputList.get(outputList.size() - 1).writeObject(panel);
			try {
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < id; i++) {
				outputList.get(id).writeObject(panel.getPlayer(i));
			}
			queue.add(panel.getPlayer(id));
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
		while(true) {
			System.out.print(""); // BUGG-CODE
			while (queue.peek() != null) {
				Object poll = queue.poll();
/*DEBUGG*/		for(int i = 0; i < outputList.size(); i++) {
					try {
						outputList.get(i).writeObject(poll);
						System.out.println("Sent " + poll + " to " + i);
						outputList.get(i).flush();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
 			}
 		}
 	}
}