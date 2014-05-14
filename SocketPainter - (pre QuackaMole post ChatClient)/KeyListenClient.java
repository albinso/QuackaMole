
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

// public class KeyListenClient extends JPanel implements KeyListener, ActionListener, Serializable {
// 	TestClient client;
// 	JButton up, down, left, right, nothing;
// 	KeyListenPlayer field = null;
// 	public KeyListenClient() throws IOException{
// 		InetSocketAddress adr = new InetSocketAddress("192.168.0.103", 8080);
// 		this.client = new TestClient(adr, "Rick Astley");
// 		up = new JButton("up");
// 		down = new JButton("down");
// 		left = new JButton("left");
// 		right = new JButton("right");
// 		nothing = new JButton("null");
// 		up.addActionListener(this);
// 		down.addActionListener(this);
// 		left.addActionListener(this);
// 		right.addActionListener(this);
// 		nothing.addActionListener(this);
// 		add(up);
// 		add(down);
// 		add(left);
// 		add(right);
// 		add(nothing);

public class KeyListenClient extends JPanel implements KeyListener, Serializable {
	KeyListenBackendClient client;
	int playerID;
	boolean isMoving = true;
	Queue<KeyListenPackage> actions;
	private LinkedList<Obstacle> obstacles;
	private KeyListenPlayer[] players = new KeyListenPlayer[4]; // TODO: We don't want a hard coded 4 in here. We don't even want the client to have any say in the number of players.
	public KeyListenClient(InetSocketAddress adr) throws IOException {
		actions = new LinkedList<KeyListenPackage>();
		this.client = new KeyListenBackendClient(adr, "Rick Astley");
		this.playerID = client.getID();
		System.out.println(playerID);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		obstacles = (LinkedList<Obstacle>) client.getObject();
		receiver().start();

		actionHandler(10).start();

		moveHandler().start();
		
		repaint();
	}

	public Thread receiver() {
		return new Thread() {
			public void run() {
				while(true) {
					Object temp = client.getObject();
					if(temp instanceof KeyListenPackage) {
						actions.add((KeyListenPackage)temp);
					} else if (temp instanceof KeyListenPlayer){
						KeyListenPlayer tempPlay = (KeyListenPlayer)temp;
						players[tempPlay.getID()] = tempPlay;
					}
				}
			}
		};
	}

	public Thread actionHandler(final long pause) {
		return new Thread() {
			public void run() {
				while(true) {
					KeyListenPackage poll = actions.poll();
					if(poll != null) {
						poll.doAction(players[poll.getPlayerID()]);
					}
					try {
						sleep(pause);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	public Thread moveHandler() {
		return new Thread() {
			public void run() {
				while(true) {
					int count = 0;
					for(KeyListenPlayer p : players) {
						if(p == null) {
							continue;
						}
						count++;
						p.move();
						for(int i = 0; i < obstacles.size(); i++) {
							if(obstacles.get(i) != null) {
								if (p.collided(obstacles.get(i)))
									if(obstacles.get(i).takeDamage(1)) {
										obstacles.set(i, null);
									}
							}
						}
					}
					try {
						sleep(20);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(count);
				}
			}
		};
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//panel.getImage().paintIcon(null, g, 0, 0);
		for(Obstacle obstacle : obstacles) {
			if(obstacle != null) {
				obstacle.paint(g);
			}
		}
		for(int i = 0; i < players.length; i++) {
			KeyListenPlayer player = players[i];
			if(player == null) {
				continue;
			}
			player.paint(g);
		}

		requestFocus();
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		if(isMoving) {
			return;
		}
		isMoving = true;
		int direction = 3;
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			direction = 0;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = 1;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = 2;
		} 

		MovePackage p = new MovePackage(playerID, direction);
		client.sendObject(p);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		isMoving = false;
		StopPackage p = new StopPackage(playerID, players[playerID]);
		client.sendObject(p);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Send key inputs");
		InetSocketAddress adr = new InetSocketAddress("192.168.0.100", 8080);
		frame.setSize(600, 400);
		if(args.length == 2) {
			adr = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		}
		try {
			frame.add(new KeyListenClient(adr));
		} catch(IOException e) {
			System.exit(1);
		}
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.requestFocus();
	}
}