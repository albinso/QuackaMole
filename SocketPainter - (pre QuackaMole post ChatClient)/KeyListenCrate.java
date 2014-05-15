import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class KeyListenCrate extends Obstacle {
	private int buffType;

	public KeyListenCrate(int x, int y) {
		this(x, y, (int)((Math.random() * 10) % Buff.BUFFTYPES));
	}

	public KeyListenCrate(int x, int y, int buffType) {
		super(x, y, getImages());
		this.buffType = buffType;
	}

	public Buff destroyCrate() {
		System.out.println(buffType);
		return new Buff(x, y, buffType);
	}

	/**
	* Just a place to read images and put them in an array.
	* Only for use in constructor of superclass.
	*/
	private static ImageIcon[] getImages() {
		ImageIcon[] images = new ImageIcon[1];
		images[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage("crate_v2.png"));
		return images;
	}
}