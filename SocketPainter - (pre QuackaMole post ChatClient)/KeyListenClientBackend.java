
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

public class KeyListenClientBackend extends JPanel implements KeyListener, Serializable {
	private static final int LOST = -1, RUNNING = 0, WON = 1;
	KeyListenBackendClient client;
	int playerID;
	boolean isMoving = true;
	Queue<KeyListenPackage> actions;
	private LinkedList<Obstacle> obstacles;
	private LinkedList<Buff> buffs;
	private LinkedList<Bullet> bullets;
	private int result = RUNNING;
	private Font endScreenFont;
	private KeyListenPlayer[] players = new KeyListenPlayer[4]; // TODO: We don't want a hard coded 4 in here. We don't even want the client to have any say in the number of players.

	public KeyListenClientBackend(InetSocketAddress adr) throws IOException {
		actions = new LinkedList<KeyListenPackage>();
		buffs = new LinkedList<Buff>();
		bullets = new LinkedList<Bullet>();
		endScreenFont = new Font("Comic Sans", 0, 300);

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
					} else if(temp instanceof Bullet) {
						bullets.add((Bullet)temp);
					} else if(temp instanceof PlayerDeath) {
						players[((PlayerDeath)temp).getID()] = null;
						boolean won = true;
						for(KeyListenPlayer p : players) {
							if(p != null && p != players[playerID]) {
								won = false;
							}
						}
						if(won) { // This player won the game!
							result = WON;
						}
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
					for(int i = 0; i < players.length; i++) {
						KeyListenPlayer p = players[i];
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
	* Handles damage dealt to obstacles.
	*/
	private void checkPlayerCollision(KeyListenPlayer p) {
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle block = obstacles.get(i);
			if(block != null && p.collidedWithBlock(block) && block.takeDamage(p.getDigDamage())) {
				if(block instanceof KeyListenCrate) {
					buffs.add(((KeyListenCrate)block).destroyCrate());
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

	private void checkBulletCollision(Bullet bullet) {
		for(int i = 0; i < obstacles.size(); i++) {
			Obstacle block = obstacles.get(i);
			if(block != null && bullet.collidedWithBlock(block)) {
				bullets.remove(bullet);
			}
		}

		for(int i = 0; i < players.length; i++) {
			KeyListenPlayer p = players[i];
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//panel.getImage().paintIcon(null, g, 0, 0);
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
			KeyListenPlayer player = players[i];
			if(player == null) {
				continue;
			}
			//if(Math.abs(player.getX() - players[playerID].getX()) < 100 && Math.abs(player.getY() - players[playerID].getY()) < 100) {
				player.paint(g);
			//}
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			Bullet bullet = players[playerID].shoot();
			if(bullet != null) {
				client.sendObject(bullet);
			}
			return;
		}
		if(isMoving) {
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
			isMoving = true;
			MovePackage p = new MovePackage(playerID, direction);
			client.sendObject(p);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

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
			client.sendObject(p);
		}
	}
}