import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * Crates are obstacles containing buffs that 
 * drops when the crates are destroyed.
 * 
 * @author Per Nyberg, Albin Söderholm
 */
public class Crate extends Obstacle {
	private final int durability = 5;
	private int buffType;

	/**
	 * Randomizes the buff in the crate and then redirects to the
	 * other constructor.
	 */
	public Crate(int x, int y) {
		this(x, y, (int)((Math.random() * 10) % Buff.BUFFTYPES));
	}

	/**
	 * Creates a crate containing a predecided buff and then 
	 * redirects the parameters to the constructor of the 
	 * super-class and adds the class specific image.
	 */
	public Crate(int x, int y, int buffType) {
		super(x, y, durability, getImage());
		this.buffType = buffType;
	}

	/**
	 * @return the image for crates.
	 */
	private static ImageIcon getImage() {
		Image image = Toolkit.getDefaultToolkit().getImage("crate.png");
		return new ImageIcon(image);
	}

	/**
	 * When the crate is destroyed the buff is set free.
	 */
	public Buff destroyCrate() {
		return new Buff(x, y, buffType);
	}
}