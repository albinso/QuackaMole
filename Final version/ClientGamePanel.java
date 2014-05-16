import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.net.*;
import java.io.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A panel which will display the current state of the game as we know it.
 * This does not necessarily exactly match other clients but will hopefully be close enough.
 * The panel will synchronize certain player input and events with the other clients.
 *
 * @author Per Nyberg, Albin Söderholm
 */
public class ClientGamePanel extends JPanel implements KeyListener, Serializable {
	// Determines current state of game
	private static final int LOST = -1, RUNNING = 0, WON = 1;

	private QuackaMoleBackendClient client;
	private Queue<QuackaMolePackage> actions;
	private LinkedList<Obstacle> obstacles;
	private LinkedList<Buff> buffs;
	private LinkedList<Bullet> bullets;
	private int playerID;
	private boolean isMoving = true;
	private int result = RUNNING;
	private Font endScreenFont;
	private int refreshPlayerCount, refreshCooldown; // Used to occasionally refresh position in other clients while moving.
	private MolePlayer[] players = new MolePlayer[4]; // TODO: We don't want a hard coded 4 in here. We don't even want the client to have any say in the number of players.

	public ClientGamePanel(InetSocketAddress adr) throws IOException {
		actions = new LinkedList<QuackaMolePackage>();
		buffs = new LinkedList<Buff>();
		bullets = new LinkedList<Bullet>();
		endScreenFont = new Font("Comic Sans", 0, 300);

		refreshCooldown = 30;

		this.client = new QuackaMoleBackendClient(adr, "Rick Astley");
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
	 * Receives actions and players from the Server. Will handle them as appropriate.
	 */
	public Thread receiver() {
		return new Thread() {
			public void run() {
				while(true) {
					Object temp = client.getObject();
					if(temp instanceof QuackaMolePackage) {
						actions.add((QuackaMolePackage)temp);
					} else if (temp instanceof MolePlayer){
						MolePlayer tempPlay = (MolePlayer)temp;
						players[tempPlay.getID()] = tempPlay;
					} else if(temp instanceof Bullet) {
						bullets.add((Bullet)temp);
					} else if(temp instanceof PlayerDeath) {
						players[((PlayerDeath)temp).getID()] = null;
						
						if(checkVictory()) { // This player won the game!
							result = WON;
						}
					}
				}
			}
		};
	}

	/**
	 * Checks if this client's player is the only remaining one alive.
	 */
	private boolean checkVictory() {
		for(MolePlayer p : players) {
			if(p != null && p != players[playerID]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Creates a Thread that will iterate over all incoming actions and perform them.
	 * @param pause Amount of milliseconds that the Thread will sleep between each action. Needed to avoid certain sychronization issues.
	 */
	public Thread actionHandler(final long pause) {
		return new Thread() {
			public void run() {
				while(true) {
					QuackaMolePackage poll = actions.poll();
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
					for(int i = 0; i < players.length; i++) {
						MolePlayer p = players[i];
						if(p == null) {
							continue;
						}
						count++;
						p.move();
						p.tick();
						checkPlayerCollision(p);
					}
					for(int i = 0; i < bullets.size(); i++) {
						Bullet b = bullets.get(i);
						b.move();
						checkBulletCollision(b);
					}
					try {
						sleep(20);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	/**
	 * Compares the location of p to all obstacles and "shoves" the player away from the obstacle.
	 * Handles damage dealt to and destruction of obstacles.
	 * Also handles retrieval of buffs.
	 */
	private void checkPlayerCollision(MolePlayer p) {
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle block = obstacles.get(i);
			if(block != null && p.collidedWithBlock(block) && block.takeDamage(p.getDigDamage())) {
				if(block instanceof Crate) {
					buffs.add(((Crate)block).destroyCrate());
				}
				obstacles.set(i, null);
			}
		}

		for(int i = 0; i < buffs.size(); i++) {
			Buff buff = buffs.get(i);
			if(buff != null && p.collidedWithBuff(buff)) {
				players[p.getID()].setBuff(buff);
				buffs.set(i, null);
			}
		}
	}

	/**
	 * Will check if bullet has collided with a wall or player in which case it will be removed from the game.
	 * If collided with a player said player will take damage from bullet first.
	 */
	private void checkBulletCollision(Bullet bullet) {
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle block = obstacles.get(i);
			if(block != null && bullet.collidedWithBlock(block)) {
				bullets.remove(bullet);
			}
		}

		for(int i = 0; i < players.length; i++) {
			MolePlayer p = players[i];
			if(p == null) {
				continue;
			}
			if(bullet != null && p.collidedWithBullet(bullet) && !bullet.isOwner(p)) {
				if(p.takeDamage(bullet.getDamage()) && i == playerID) {
					client.sendObject(new PlayerDeath(playerID));
					result = LOST;
				}
				bullets.remove(bullet);
			}
		}
	}

	/**
	 * Paints the gamepanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle obstacle = obstacles.get(i);
			if(obstacle != null) {
				obstacle.paint(g);
			}
		}
		for(int i = 0; i < buffs.size(); i++) {
			Buff buff = buffs.get(i);
			if(buff != null) {
				buff.paint(g);
			} else {
				buffs.remove(i);
				i--;
			}
		}

		for(int i = 0; i < players.length; i++) {
			MolePlayer player = players[i];
			if(player == null) {
				continue;
			}

			player.paint(g);
		}

		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bullet != null) {
				bullet.paint(g);
			}
		}

		g.setFont(endScreenFont);
		g.setColor(new Color(222, 49, 99));
		if(result == WON) {
			g.drawString("Victory!", 100, 400);
		} else if(result == LOST) {
			g.drawString("LOST", 200, 400);
		}

		requestFocus();
		repaint();
	}

	/**
	 * Registers key presses. If an arrow key is pressed an event will be sent to the server to notify other clients.
	 * If space is pressed a bullet will be fired and distributed to other clients.
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			Bullet bullet = players[playerID].shoot();
			if(bullet != null) {
				client.sendObject(bullet);
			}
			return;
		}
		if(isMoving && refreshPlayerCount < refreshCooldown) {
			refreshPlayerCount++;
			return;
		}
		refreshPlayerCount = 0;
		int direction = -1;
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			direction = 0;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = 1;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = 2;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = 3;
		}

		if(direction != -1) {
			isMoving = true;
			MovePackage p = new MovePackage(players[playerID], direction);
			client.sendObject(p);
		}
	}

	/**
	 * Checks if any key has been typed, not relevant in our code.
	 */
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	/**
	 * Checks for arrow keys being released.
	 * Will send an event to the server that the player should stop moving.
	 * The event will include information about the player's current position for synchronization purposes.
	 */
	public void keyReleased(KeyEvent e) {
		if(!isMoving) {
			return;
		}
		int direction = -1;
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			direction = 0;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = 1;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = 2;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = 3;
		}

		if(direction != -1) {
			isMoving = false;
			StopPackage p = new StopPackage(players[playerID], direction);
			p.doAction(players[playerID]);
			client.sendObject(p);
		}
	}
}