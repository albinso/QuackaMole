public class MovePackage extends KeyListenPackage {
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	private static final long serialVersionUID = 43L;
	private int direction;
	public MovePackage(int playerID, int direction) {
		super(playerID);
		this.direction = direction;
	}
	
	@Override
	public void doAction(KeyListenPlayer p) {
		p.setMoving(direction, true);
	}
}