public interface Frame {
	/**
	* Starts the game.
	*/
	void start();

	/**
	* Toggles pause state.
	* @return true if toggling to paused.
	*/
	boolean pause();

	/**
	* Toggles options menu.
	* @return true if toggling to menu state.
	*/
	boolean options();

	/**
	* Exits the game.
	*/
	void quit();
}