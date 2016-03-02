package org.copinf.cc.controller;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.view.Window;
import org.copinf.cc.view.gamepanel.GamePanel;

/**
 * Controls the game state.
 */
public class GameController extends AbstractController {

	private Game game;
	private Window window;
	private GamePanel gamePanel;

	/**
	 * Constructs a new GameController.
	 * @param game the current game
	 * @param player the playing player
	 * @param window the window to display the game on
	 */
	public GameController(final Game game, final Player player, final Window window) {
		super();
		this.game = game;
		this.window = window;
		this.gamePanel = new GamePanel(game, player);
		window.setContentPane(gamePanel);
		window.validate();
		window.pack();
	}

	@Override
	public void start() {

	}
}
