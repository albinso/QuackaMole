
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class KeyListenClient extends JPanel implements KeyListener, Serializable {
	KeyListenBackendClient client;
	int playerID;
	boolean isMoving = true;
	Queue<KeyListenPackage> actions;
	private LinkedList<Obstacle> obstacles;
	private LinkedList<Buff> buffs;
	private LinkedList<Bullet> bullets;
	private KeyListenPlayer[] players = new KeyListenPlayer[4]; // TODO: We don't want a hard coded 4 in here. We don't even want the client to have any say in the number of players.
	public KeyListenClient(InetSocketAddress adr) throws IOException {
		actions = new LinkedList<KeyListenPackage>();
		buffs = new LinkedList<Buff>();
		bullets = new LinkedList<Bullet>();

		this.client = new KeyListenBackendClient(adr, "Rick Astley");
		this.playerID = (int)client.getObject();
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

	/**
	* Receives actions and players from the Server. Will put them at appropriate places.
	*/
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

	/**
	* Creates a Thread that will iterate over all incoming actions and perform them.
	* @param pause Amount of milliseconds that the Thread will sleep between each action. Needed to avoid certain sychronization issues.
	*/
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

	/**
	* Creates a Thread that will handle all movement and subsequent collision detection for the client.
	*/
	private Thread moveHandler() {
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
						checkPlayerCollision(p);
					}
					for(Bullet b : bullets) {
						b.move();
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

	/**
	* Compares the location of p to all obstacles and "shoves" the player away from the obstacle.
	* Handles damage dealt to obstacles.
	*/
	private void checkPlayerCollision(KeyListenPlayer p) {
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle block = obstacles.get(i);
			if(block != null && p.collidedWithBlock(block) && block.takeDamage(p.getDigDamage())) {
				if(block instanceof KeyListenCrate) {
					buffs.add((KeyListenCrate)block).destroyCrate());
				}
				obstacles.set(i, null);
			}
		}
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bullet != null && p.collidedWithBullet(bullet) && !bullet.isOwner(p) && p.takeDamage(bullet.getDamage())) {
				players[p.getID()] = null;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//panel.getImage().paintIcon(null, g, 0, 0);
		for(Obstacle obstacle : obstacles) {
			if(obstacle != null) {
				obstacle.paint(g);
			}
		}
		for(Buff buff : buffs) {
			if(buff != null) {
				buff.paint(g);
			}
		}

		for(int i = 0; i < players.length; i++) {
			KeyListenPlayer player = players[i];
			if(player == null) {
				continue;
			}
			//if(Math.abs(player.getX() - players[playerID].getX()) < 100 && Math.abs(player.getY() - players[playerID].getY()) < 100) {
				player.paint(g);
			//}
		}

		for(Bullet bullet : bullets) {
			if(bullet != null) {
				bullet.paint(g);
			}
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
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			bullets.add(players[playerID].shoot());
			return;
		}

		MovePackage p = new MovePackage(playerID, direction);
		client.sendObject(p);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT) {
			return;
		}
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