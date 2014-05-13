public class StopPackage extends KeyListenPackage {
	private int x, y;
	public StopPackage(int playerID, KeyListenPlayer player) {
		super(playerID);
		this.x = player.getX();
		this.y = player.getY();
	}

	public void doAction(KeyListenPlayer player) {
		player.stopMoving();
		player.setPosition(x, y);
	}
}