package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.net.Message;

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

	private final Player player;
	private final BoardView boardView;
	private Point mouse;
	private final List<Message> messages;
	private final Map<Player, PlayerView> playerViews;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 * @param playerViews PlayerView of each Player
	 */
	public DrawZone(final Game game, final Player player, final Map<Player, PlayerView> playerViews) {
		super();
		this.player = player;
		boardView = new BoardView(game.getBoard(), player, playerViews, 800, 500);

		messages = new ArrayList<>();
		this.mouse = new Point(0, 0);
		addMouseMotionListener(this);
		LOGGER.setLevel(java.util.logging.Level.OFF);
		this.playerViews = playerViews;
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d, mouse);
		drawMessages(g2d);
	}

	@Override
	public void mouseMoved(final MouseEvent ev) {
		mouse = ev.getPoint();
		repaint();
	}

	@Override
	public void mouseDragged(final MouseEvent ev) {

	}

	public BoardView getBoardView() {
		return boardView;
	}

	public void addMessage(final String message, final String playerName,
		final boolean isChatMessage) {
		addMessage(new Message(message, playerName, isChatMessage));
	}

	public void addMessage(final String message, final String playerName) {
		addMessage(message, playerName, true);
	}

	public void addMessage(final String message) {
		addMessage(message, "", false);
	}

	public void addMessage(final Message message) {
		messages.add(message);
		if (messages.size() > 10) {
			messages.remove(0);
		}
	}

	public void drawMessages(final Graphics2D g2d) {
		final int x = 5;
		int y = 5;
		for (int i = messages.size() - 1; i >= 0 ; i--) {
			final Message message = messages.get(i);
			Color color = Color.BLACK;
			if (message.isChatMessage) {
				for (final PlayerView pv : playerViews.values()) {
					if (pv.player.getName().equals(message.playerName)) {
						color = pv.color;
					}
				}
			}
			g2d.setColor(color);
			g2d.drawString(message.message, x, getHeight() - y);
			y += 20;
		}
		g2d.setColor(Color.BLACK);
	}
}
