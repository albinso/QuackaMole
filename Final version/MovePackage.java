/**
 * This is a package containing a signal that
 * says that the given player (through the ID)
 * should start moving.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class MovePackage extends QuackaMolePackage {
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	private static final long serialVersionUID = 43L;

	private int direction;

	public MovePackage(int playerID, int direction) {
		super(playerID);
		this.direction = direction;
	}
	
	/**
	 * Does the action for the package, starts the movement
	 * of the targeted player.
	 */
	@Override
	public void doAction(MolePlayer p) {
		p.setMoving(direction, true);
	}
}