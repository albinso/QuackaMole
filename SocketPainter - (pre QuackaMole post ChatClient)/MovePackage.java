public class MovePackage extends KeyListenPackage {
	static final long serialVersionUID = 43L;
	private int direction;
	public MovePackage(int playerID, int direction) {
		super(playerID);
		this.direction = direction;
	}
	@Override
	public void doAction(KeyListenPlayer p) {
		switch(direction) {
			case 0:
				p.goUp();
				break;
			case 1:
				p.goDown();
				break;
			case 2:
				p.goLeft();
				break;
			case 3:
				p.goRight();
				break;
		}
	}
}