/**
 * 
 * 
 * @author Per Nyberg, Albin Söderholm
 */

/**
 * This is a package containing a signal that
 * says that the given player (through the ID)
 * should start moving.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class StopPackage extends QuackaMolePackage {
	public static final long serialVersionUID = 42L;

	private int x, y;
	private int direction;

	public StopPackage(KeyListenPlayer player, int direction) {
		super(player.getID());

		this.x = player.getX();
		this.y = player.getY();
		this.direction = direction;
	}

	/**
	 * Does the action for the package, stops the movement
	 * of the targeted player and gives him a final
	 * position.
	 */
	@Override
	public void doAction(KeyListenPlayer player) {
		player.setMoving(direction, false);
		player.setPosition(x, y);
	}
}