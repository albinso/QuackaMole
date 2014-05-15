public class StopPackage extends KeyListenPackage {
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final long serialVersionUID = 42L;
	private int direction;
	private int x, y;
	public StopPackage(KeyListenPlayer player, int direction) {
		super(player.getID());
		this.x = player.getX();
		this.y = player.getY();
		this.direction = direction;
	}

	public void doAction(KeyListenPlayer player) {
		player.setMoving(direction, false);
		player.setPosition(x, y);
	}
}