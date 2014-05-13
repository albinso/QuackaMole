public class StopPackage extends KeyListenPackage {
	public StopPackage(int playerID) {
		super(playerID);
	}

	public void doAction(KeyListenPlayer player) {
		player.stopMoving();
	}
}