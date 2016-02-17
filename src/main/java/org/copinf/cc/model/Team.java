package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Team {

	/** Opponent team. */
	private Team opponentTeam;

	/** Players of the current team. */
	private Set<Player> players;

	/**
	 * Constructs a new team.
	 */
	public Team() {
		this(new HashSet<>());
	}

	/**
	 * Constructs a new team.
	 * @param players players in the team
	 */
	public Team(final Set<Player> players) {
		this.players = players;
	}

	/**
	 * Adds a player to this theam.
	 * @param player player to add
	 * @return false if the adding process failed
	 */
	public boolean addPlayer(final Player player) {
		return players.add(player);
	}

	/**
	 * Returns the players of this team.
	 * @return players of this team
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the opponent team.
	 * @param team opponent team
	 */
	public void setOpponentTeam(final Team team) {
		opponentTeam = team;
	}

	/**
	 * Returns the opponent team.
	 * @return opponent team
	 */
	public Team getOpponentTeam() {
		return opponentTeam;
	}

	/**
	 * Checks if this team has won, that is if each player in this team has won.
	 * @return true if this team has won
	 */
	public boolean hasWon() {
		for (final Player player : players)
			if (!player.hasWon())
				return false;
		return true;
	}
}
