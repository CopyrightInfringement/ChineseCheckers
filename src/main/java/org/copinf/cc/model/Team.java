package org.copinf.cc.model;

public class Team {

	/** Players of the current team. */
	private Player[] players;

	/**
	 * Constructs a new team.
	 */
	public Team() {
		players = new Player[2];
	}

	/**
	 * Adds a player to this theam.
	 * @param player player to add
	 * @return false if the adding process failed
	 */
	public boolean addPlayer(final Player player) {
		if (players[0] == null) {
			players[0] = player;
		} else if (players[1] == null) {
			players[1] = player;
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Gets the i-th player of this team.
	 * @param i index
	 * @return the i-th player
	 */
	public Player get(final int i) {
		return players[i];
	}

	/**
	 * Returns the size of this team.
	 * @return the size
	 */
	public int size() {
		return players[0] == null ? 0 : (players[1] == null ? 1 : 2);
	}

	/**
	 * Checks if this team has won, that is if each player in this team has won.
	 * @return true if this team has won
	 */
	public boolean hasWon() {
		for (final Player player : players) {
			if (!player.hasWon()) {
				return false;
			}
		}
		return true;
	}
}
