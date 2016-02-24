package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The InfoBar at the top of the Window, providing informations such as the player's name,
 * the number of turns...
 */
@SuppressWarnings("serial")
public class InfoBar extends JPanel {

	private Game game;
	private Player player;

	private JLabel turnCountLabel;
	private JLabel playerNameLabel;

	/**
	 * Constructs a new InfoBar.
	 * @param game the current game
	 * @param player the playing player
	 */
	public InfoBar(final Game game, final Player player) {
		super();
		this.game = game;
		this.player = player;

		// UI
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		playerNameLabel = new JLabel();
		add(playerNameLabel);
		playerNameLabel.setText(player.getName());

		turnCountLabel = new JLabel();
		add(turnCountLabel);
	}

	/**
	 * Update this InfoBar.
	 */
	public void update() {
		turnCountLabel.setText("Turn: " + game.getTurnCount());
	}
}
