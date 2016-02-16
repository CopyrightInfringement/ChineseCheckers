package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Team {

	private Team opponentTeam;
	private Set<Player> players;

	public Team() {
		this(new HashSet<>());
	}

	public Team(final Set<Player> players) {
		this.players = players;
	}

	public boolean addPlayer(final Player player) {
		return players.add(player);
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setOpponentTeam(final Team team) {
		opponentTeam = team;
	}

	public Team getOpponentTeam() {
		return opponentTeam;
	}
}
