import java.io.*; // TODO
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents one instance of the game.
 * Will create a map based on the map.txt file and distribute this among all players.
 * The Lobby will be distributing all information that needs to be shared between clients.
 * This include Player information, map layout and Player commands.
 * @author Per Nyberg, Albin Söderholm
 */
public class KeyListenLobby extends Thread {
	private final int adjustment = 8;

	private static String IMG_PATH;
	private ArrayList<ObjectOutputStream> outputList;
	private LinkedList<KeyListenPlayer> players;
	private LinkedList<Obstacle> obstacles;
	private Queue<Object> queue;

	public int width;
	public int height;
	
	public LinkedList<StartPlace> startPlaces;

	public KeyListenLobby(String mapPath) {
		startPlaces = new LinkedList<StartPlace>();
		outputList = new ArrayList<ObjectOutputStream>();
		queue = new LinkedList<Object>();
		players = new LinkedList<KeyListenPlayer>();
		obstacles = initMap(mapPath);
	}

	/**
	 * Initialises the map.
	 * Will read mapdata from the file at the given path.
	 */
	public LinkedList<Obstacle> initMap(String mapPath) {
		File mapFile = new File(mapPath);
		Scanner fileScanner = null;

		try {
			fileScanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-2);
		}

		LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();

		int x = 0;
		int y = 0;
		while (fileScanner.hasNext()) {
			Scanner lineScanner = new Scanner(fileScanner.nextLine());
			x = 0;
			while (lineScanner.hasNext()) {
				String token = lineScanner.next();

				if (token.equals("#"))
					obstacles.add(new KeyListenDirt(x, y));
				else if (token.equals("*"))
					obstacles.add(new KeyListenStone(x, y));
				else if (isNumeric(token)) {
					int startNumber = Integer.parseInt(token);
					startPlaces.add(new StartPlace(x, y, startNumber));
				}
				else if (token.equals("$"))
					obstacles.add(new KeyListenCrate(x, y));

				x += Obstacle.SIZE;
			}
			y += Obstacle.SIZE;
		}

		width = x;
		height = y;

		return obstacles;
	}

	/**
	 * Determines whether or not a String contains a numeric value.
	 */
	private boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	/**
	 * Gives information on where to spawn the player.
	 */
	private StartPlace getStartPlace(int startNumber) {
		for (int i = 0 ; i < startPlaces.size() ; i++)
			if (startPlaces.get(i).getStartNumber() == startNumber)
				return startPlaces.remove(i);
		return null;
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
			Integer id = addPlayer();
			outputList.get(outputList.size() - 1).writeObject(id); // Sends new player's id back to them.
			outputList.get(outputList.size() - 1).writeObject(obstacles); // Sends a list containing all Obstacles.
			for(int i = 0; i < id; i++) {
				outputList.get(id).writeObject(players.get(i)); // Sends all other players to the new one.
			}
			queue.add(players.get(id)); // Queues the new player for distribution.
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to client. Shutting down.");
			System.exit(-1);
		}

		// This Thread will listen for input from the client and queue it up for distribution.
		new Thread() {
			public void run() {
				try {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					while (true) {
						queue.add(inputStream.readObject());
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Information could not be transmitted properly. Shutting down.");
					System.exit(-1);
				} catch (ClassNotFoundException e) {
					e.printStackTrace(); // This should not happen and if it does a stackTrace is more useful than anything we have to say.
					System.exit(-2);
				}
			}
		}.start();
	}

	/**
	 * Adds a player to the game.
	 * @return the player's unique ID. This will be used by all clients to identify the player.
	 */
	private int addPlayer() {
		int x = 0;
		int y = 0;
		int id = players.size();

		if (startPlaces.size() > 0) {
			StartPlace startPlace = getStartPlace(id + 1);
			x = startPlace.getX() + adjustment;
			y = startPlace.getY() + adjustment;
		} else {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		
		players.add(new KeyListenPlayer(x, y, id));
		return id;
	}

	/**
	 * Relays commands and objects to all clients.
	 * Will iterate over the queue that contains all incoming commands and send all of them to all clients.
	 * 
	 */
	public void run() {
		while(true) {
			System.out.print(""); // Fixes a bug. Please don't touch.
			while (queue.peek() != null) {
				Object poll = queue.poll();
				for(int i = 0; i < outputList.size(); i++) {
					try {
						outputList.get(i).writeObject(poll);
					} catch(IOException e) {
						JOptionPane.showMessageDialog(null, "Information could not be transmitted properly. Shutting down.");
						System.exit(-1);
					}
				}
 			}
 		}
 	}
}