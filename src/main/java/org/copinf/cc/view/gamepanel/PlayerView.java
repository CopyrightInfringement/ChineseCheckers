package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Player;

import java.awt.Color;

/**
 * Provides a data structure to hold and display informations about players.
 */
public class PlayerView {

	private Player player;
	private Color color;

	// TODO any number of players
	private static final Color[] COLORS = new Color[] {
		Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE
	};

	/**
	 * Constructs a new PlayerView.
	 * @param player player to display
	 * @param i index of this player on the board which will be used to assignate a color to this
	 * player.
	 */
	public PlayerView(final Player player, final int i) {
		this(player, COLORS[i]);
	}

	/**
	 * Constructs a new PlayerView.
	 * @param player player to display
	 * @param color to use with this player
	 */
	public PlayerView(final Player player, final Color color) {
		this.player = player;
		this.color = color;
	}

	/**
	 * Gets the player associated with this PlayerView.
	 * @return a player
	 */
	public Player player() {
		return player;
	}

	/**
	 * Gets the color associated with the player of this PlayerView.
	 * @return a color
	 */
	public Color getColor() {
		return color;
	}
}