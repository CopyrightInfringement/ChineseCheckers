package org.copinf.cc.model;

import java.util.Set;

public class Game {

	private Player currentPlayer;
	private Set<Player> players;
	private Set<Team> teams;
	private int turnCount;
	private AbstractBoard board;

	public Game() {
		throw new UnsupportedOperationException();
	}

	public void nextTurn() {
		throw new UnsupportedOperationException();
	}

	public boolean isGameOver() {
		throw new UnsupportedOperationException();
	}

	public Player getWinner() {
		throw new UnsupportedOperationException();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public AbstractBoard getBoard() {
		return board;
	}

	public Set<Player> getPlayer() {
		return players;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public boolean addTeam(final Team team) {
		return teams.add(team);
	}

	public int getTurnCount() {
		return turnCount;
	}
}
