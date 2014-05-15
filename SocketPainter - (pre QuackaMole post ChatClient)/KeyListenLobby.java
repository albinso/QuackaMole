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

	/**
	* Adds a client to the system. 
	* The client will be included in the "mailing list" that relays commands. 
	* The server will also be listening to the client's commands and relay them.
	* A player will be created and connected to the client.
	* Additionally a playerID, list of obstacles and all other players will be sent to the client.
	* Finally the client's player will be relayed to all clients, including the newly added one.
	*/
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
						queue.add(inputStream.readObject());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	* Adds a player to the game.
	* @return the player's unique ID.
	*/
	public int addPlayer() {
		// TODO: Make test for this
		int x = 0;
		int y = 0;
		int id = players.size();
		if (panel.startPlaces.size() > 0) {
			StartPlace startPlace = panel.getStartPlace(id + 1);
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

	/**
	* Relays commands to all clients.
	*/
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