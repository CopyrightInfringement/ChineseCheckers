package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JPanel;

/**
 * DrawZone is where the board, the players' icons and the chat is displayed.
 */
@SuppressWarnings("serial")
public class DrawZone extends JPanel {

	private final Game game;
	private final Player player;
	private final BoardView boardView;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 */
	public DrawZone(final Game game, final Player player, final Map<Player, PlayerView> playerViews) {
		super();
		this.game = game;
		this.player = player;
		boardView = new BoardView(game.getBoard(), playerViews, 800, 500);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d);
	}
}
