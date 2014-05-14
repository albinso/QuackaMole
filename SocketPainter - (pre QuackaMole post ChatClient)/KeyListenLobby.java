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
	private LinkedList<KeyListenPlayer> players;
	private LinkedList<Obstacle> obstacles;
	private Queue<Object> queue;
/*DEBUGG*/private KeyListenPanel panel;


	public KeyListenLobby(KeyListenPanel panel) {
/*DEBUGG*/this.panel = panel;
		obstacles = panel.initMap();
		outputList = new ArrayList<ObjectOutputStream>();
		queue = new LinkedList<Object>();
		players = new LinkedList<KeyListenPlayer>();
	}

	public void addClient(final Socket socket) {
		try {
			outputList.add(new ObjectOutputStream(socket.getOutputStream()));
/*DEBUGG*/	Integer id = addPlayer();
			outputList.get(outputList.size() - 1).writeObject(id); // TODO: Make this look good.
			outputList.get(outputList.size() - 1).writeObject(obstacles);
			for(int i = 0; i < id; i++) {
				outputList.get(id).writeObject(players.get(i));
			}
			queue.add(players.get(id));
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

	// called by the server when a new player has connected
	public int addPlayer() {
		// if the game hasen't started yet
		// TODO start timer when all players are ready?
		// creates a player (with random coordinates)
		// TODO set position realtive to the map
		int x = 0;
		int y = 0;
		int id = players.size();
		if (panel.startPlaces.size() > 0) {
			StartPlace startPlace = panel.startPlaces.remove(0);
			x = startPlace.getX();
			y = startPlace.getY();
		} else {
			x = (int)(Math.random() * panel.width);
			y = (int)(Math.random() * panel.height);
		}
		players.add(new KeyListenPlayer(x, y, id));

		// DEBUGG
		System.out.println("Player added");

		//repaint();

		return id;
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