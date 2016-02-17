package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Team {
	
	/** The opponent team */
	private Team opponentTeam;
	/** The players of the current team */
	private Set<Player> players;
	
	/**
	 * Constructs a new team
	 */
	public Team() {
		this(new HashSet<>());
	}
	
	/**
	 * Constructs a new team
	 * @param players The players in the team
	 */
	public Team(final Set<Player> players) {
		this.players = players;
	}
	
	/**
	 * /**
	 * Adds a player to players
	 * @param player The player to add
	 * @return false if the adding process failed
	 */
	public boolean addPlayer(final Player player) {
		return players.add(player);
	}
	
	/**
	 * Returns the players of the current team
	 * @return the players of the current team
	 */
	public Set<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Sets the opponent team
	 * @param team
	 */
	public void setOpponentTeam(final Team team) {
		opponentTeam = team;
	}
	
	/**
	 * Returns the opponent team
	 * @return the opponent team
	 */
	public Team getOpponentTeam() {
		return opponentTeam;
	}
}