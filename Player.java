import java.awt.*;

interface Player {
	public static int size = 30;
//	public final double UP = -Math.PI/2, LEFT = Math.PI, DOWN = Math.PI/2, RIGHT = 0;
	public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	/**
	* Shoots using the currently equipped weapon in the current facing direction.
	*/
	void shoot();

	/**
	* Adds the effect of buff to the Player.
	*/
	void addBuff(Buff buff);

	/**
	* Sets the players current weapon to gun.
	*/
	void addWeapon(Weapon gun);

	/**
	* Performs a move in the players current direction
	*/
	void move();

	/**
	* Sets the current facing and movement direction to angle.
	* Should only ever be used with the constant direction variables in Player.
	*/
/*	void setDirection(double angle);

	void goUp(boolean up);
	void goRight(boolean right);
	void goDown(boolean down);
	void goLeft(boolean left);*/

	/**
	 * Draws the player on the field
	 */
	void draw(Graphics g);
}