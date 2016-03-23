package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * DrawZone is where the board, the players' icons and the chat are displayed.
 */
@SuppressWarnings("serial")
public class DrawZone extends JPanel implements MouseMotionListener {

	private final static Logger LOGGER = Logger.getLogger(DrawZone.class.getName());

	private final Game game;
	private final Player player;
	private final BoardView boardView;
	private Point mouse;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 */
	public DrawZone(final Game game, final Player player, final Map<Player, PlayerView> playerViews) {
		super();
		this.game = game;
		this.player = player;
		boardView = new BoardView(game.getBoard(), player, playerViews, 800, 500);

		this.mouse = new Point(0, 0);
		addMouseMotionListener(this);
		LOGGER.setLevel(java.util.logging.Level.OFF);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d, mouse);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		LOGGER.entering("DrawZone", "mouseMoved");
		mouse = e.getPoint();
		LOGGER.info(mouse.toString());
		repaint();
		LOGGER.exiting("DrawZone", "mouseMoved");
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	public BoardView getBoardView() {
		return boardView;
	}
}
