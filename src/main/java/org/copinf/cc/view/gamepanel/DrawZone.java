package org.copinf.cc.view.gamepanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.PathFinding;
import org.copinf.cc.model.Player;
import org.copinf.cc.net.Message;

/**
 * DrawZone is where the board, the players' icons and the chat are displayed.
 */
@SuppressWarnings("serial")
public class DrawZone extends JPanel implements MouseMotionListener {

	private Coordinates selection;
	/**
	 * The BoardView associated with this DrawZone.
	 */
	public final BoardView boardView;
	private Point mouse;
	private final List<Message> messages;
	private final Map<Player, PlayerView> playerViews;
	private static final int MAX_MESSAGES = 20;
	private final Movement currentMovement;

	/**
	 * Constructs a new DrawZone.
	 * @param game the current game
	 * @param player the playing player
	 * @param playerViews PlayerView of each Player
	 */
	public DrawZone(final Game game, final Player player, final Map<Player, PlayerView> playerViews, final Movement currentMovement) {
		super();
		this.currentMovement = currentMovement;
		boardView = new BoardView(game.getBoard(), player, playerViews, 800, 500, this.currentMovement);

		selection = null;
		messages = new ArrayList<>();
		this.mouse = new Point(0, 0);
		addMouseMotionListener(this);
		this.playerViews = playerViews;
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		boardView.paint(g2d, mouse, selection);
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

	/**
	 * Add a message to the list of message to display.
	 * @param playerName The name of the sender
	 * @param isChatMessage Whether this message is a a chat message or not (a
	 *            chat message or an error message)
	 */
	public void addMessage(final String message, final String playerName, final boolean isChatMessage) {
		addMessage(new Message(message, playerName, isChatMessage));
	}

	/**
	 * Add a message to the list of message to display.
	 * @param playerName The name of the sender
	 */
	public void addMessage(final String message, final String playerName) {
		addMessage(message, playerName, true);
	}

	/**
	 * Add a message to the list of message to display.
	 */
	public void addMessage(final String message) {
		addMessage(message, "", false);
	}

	/**
	 * Add a message to the list of message to display.
	 */
	public void addMessage(final Message message) {
		messages.add(message);
		if (messages.size() > MAX_MESSAGES) {
			messages.remove(0);
		}
	}

	private void drawMessages(final Graphics2D g2d) {
		final int x = 5;
		int y = 5;
		for (int i = messages.size() - 1; i >= 0; i--) {
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

	/**
	 * Set the coordinates of the selected square.
	 */
	public void setSelectedSquare(final Coordinates selection) {
		this.selection = selection;
	}
}
