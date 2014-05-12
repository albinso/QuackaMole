import java.awt.*;
import quack.block.Obstacle;

public class Wall extends Obstacle {
	public Wall(int x, int y) {
		super(x, y);
		ballColor = Color.darkGray;
	}
}