package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * DrawZone is where the board, the players' icons and the chat are displayed.
 */
@SuppressWarnings("serial")
public class DrawZone extends JPanel implements MouseMotionListener {

	private static final Logger LOGGER = Logger.getLogger(DrawZone.class.getName());

	private final Game game;
	private final Player player;
	private final BoardView boardView;
	private Point mouse;
	private List<Message> messages;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 * @param playerViews PlayerView of each Player
	 */
	public DrawZone(final Game game, final Player player, final Map<Player, PlayerView> playerViews) {
		super();
		this.game = game;
		this.player = player;
		boardView = new BoardView(game.getBoard(), player, playerViews, 800, 500);
		messages = new ArrayList<>();

		this.mouse = new Point(0, 0);
		addMouseMotionListener(this);
		LOGGER.setLevel(java.util.logging.Level.OFF);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d, mouse);
	}

	@Override
	public void mouseMoved(final MouseEvent ev) {
		LOGGER.entering("DrawZone", "mouseMoved");
		mouse = ev.getPoint();
		LOGGER.info(mouse.toString());
		repaint();
		LOGGER.exiting("DrawZone", "mouseMoved");
	}

	@Override
	public void mouseDragged(final MouseEvent ev) {

	}

	public BoardView getBoardView() {
		return boardView;
	}

	public void addMessage(final String message, final Color color) {
		messages.add(new Message(message, color));
		if (messages.size() > 10) {
			messages.remove(messages.size() - 1);
		}
	}

	public void drawMessages(final Graphics2D g2d) {
		int x = 0;
		int y = 0;
		for (final Message message : messages) {
			g2d.setColor(message.color);
			g2d.drawString(message.message, x, y);
			y += 20;
		}
		g2d.setColor(Color.BLACK);
	}

	private static class Message {
		public final String message;
		public final Color color;

		public Message(final String message, final Color color) {
			this.message = message;
			this.color = color;
		}
	}
}
