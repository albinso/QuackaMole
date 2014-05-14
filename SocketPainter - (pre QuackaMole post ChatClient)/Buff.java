import java.awt.Color;
import java.awt.Graphics;

public class Buff implements Serializable{
	public static final int SHIELD = 0,
							DIGGER = 1;

	private final int diameter = 30;
	private int x, y;
	private int type;
	private Color color;

	public Buff(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;

		if (type == SHIELD)
			color = Color.pink;
		else if (type == DIGGER)
			color = Color.gray;
		else
			color = Color.black;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, diameter, diameter);
		g.setColor(Color.black);
		g.drawOval(x, y, diameter, diameter);
	}
}