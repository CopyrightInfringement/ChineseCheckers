package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * DrawZone is where the board, the players' icons and the chat is displayed.
 */
@SuppressWarnings("serial")
public class DrawZone extends JPanel {

	private Game game;
	private Player player;
	private BoardView boardView;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 */
	public DrawZone(final Game game, final Player player) {
		super();
		this.game = game;
		this.player = player;
		boardView = new BoardView(game.getBoard(), 800, 500);
	}

	@Override
	public void paint(Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d);
	}
}
