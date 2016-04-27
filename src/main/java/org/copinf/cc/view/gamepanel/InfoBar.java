package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * The InfoBar at the top of the Window, providing informations such as the player's name,
 * the number of turns...
 */
@SuppressWarnings("serial")
public class InfoBar extends JPanel {

	private final Game game;
	private final Player player;

	private final JLabel turnCountLabel;
	private final JLabel currentPlayerLabel;

	/**
	 * Constructs a new InfoBar.
	 * @param game the current game
	 * @param player the playing player
	 */
	public InfoBar(final Game game, final Player player, final Color color) {
		super();
		this.game = game;
		this.player = player;

		// UI

		final JLabel playerNameLabel = new JLabel();
		playerNameLabel.setText("You are " + player.getName());

		
		currentPlayerLabel = new JLabel();
		turnCountLabel = new JLabel();
		
		currentPlayerLabel.setForeground(color);
		turnCountLabel.setForeground(color);
		playerNameLabel.setForeground(color);

		add(playerNameLabel);
		add(new JSeparator(SwingConstants.VERTICAL));
		add(currentPlayerLabel);
		add(new JSeparator(SwingConstants.VERTICAL));
		add(turnCountLabel);

		updateLabels();
	}

	/**
	 * Update this InfoBar.
	 */
	public void updateLabels() {
		turnCountLabel.setText("Turn: " + game.getTurnCount());
		if (game.getCurrentPlayer() == player) {
			currentPlayerLabel.setText("You are playing !");
		} else {
			currentPlayerLabel.setText(game.getCurrentPlayer().getName() + " is playing !");
		}
	}
}
