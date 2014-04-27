interface Player {
	private final double UP = -Math.PI/2, LEFT = Math.PI, DOWN = Math.PI/2, RIGHT = 0;
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
	void setDirection(double angle);
}