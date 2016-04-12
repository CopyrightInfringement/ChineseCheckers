package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a representation of a game, with its teams, players and functions.
 */
public class Game {

	/** Player currently playing. */
	private Player currentPlayer;

	/** Different players playing this game in the order they will play. */
	private final List<Player> players;

	/** Different teams in which the players are. */
	private final List<Team> teams;

	/** Current turn count (-1 before the game starts, and the first turn is the 0-th). */
	private int turnCount;

	/** Board used for this game. */
	private final AbstractBoard board;

	/** Number of zones per player. */
	private int numberOfZones;

	/**
	 * Constructs a game.
	 * @param board board used for this game.
	 */
	public Game(final AbstractBoard board) {
		players = new ArrayList<>();
		teams = new ArrayList<>();
		turnCount = -1;
		this.board = board;
		this.numberOfZones = 0;
	}

	/**
	 * Initializes this game.
	 * @throws RuntimeException if this game cannot begin.
	 */
	private void initialize() {
		if (teams.size() < 2) {
			throw new RuntimeException("The game cannot begin, not enough teams were registered (only "
				+ teams.size() + ")");
		}

		board.dispatchZones(teams, this.numberOfZones);

		if (!board.getPossiblePlayerNumbers().contains(players.size())) {
			throw new RuntimeException("The game cannot begin, its board " + board + " doesn't support "
				+ players.size() + " players.");
		}
	}

	/**
	 * Prepares this game for the next turn.
	 * @throws RuntimeException if this game cannot begin
	 */
	public void nextTurn() {
		if (turnCount == -1) {
			initialize();
		}
		turnCount++;

		currentPlayer = players.get(turnCount % players.size());
	}

	/**
	 * Indicates whether this game is over or not.
	 * @return true if this game is over
	 */
	public boolean isGameOver() {
		return getWinner() != null;
	}

	/**
	 * Returns the winning team of this game.
	 * @return winning team, or null
	 */
	public Team getWinner() {
		for (final Team team : teams) {
			if (team.hasWon()) {
				return team;
			}
		}
		return null;
	}

	/**
	 * Returns the current player.
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Returns the board.
	 * @return the board
	 */
	public AbstractBoard getBoard() {
		return board;
	}

	/**
	 * Returns the players involved in this game.
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns the teams in which the players are.
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Adds a team and its players to this game.
	 * @param team team to add.
	 * @return true if the adding process succeeded.
	 * TODO: check that each team has the same number of players
	 */
	public boolean addTeam(final Team team) {
		if (teams.contains(team)
				|| players.contains(team.get(0)) || players.contains(team.get(1))) {
			return false;
		}
		teams.add(team);
		for (int i = 0; i < team.size(); i++) {
			players.add(i * teams.size(), team.get(i));
		}
		return true;
	}

	/**
	 * Sets the number of zones per player.
	 * @param numberOfZones number of zones per player.
	 * @return false if the number of zones requested is not compatible with the number of players
	 * added so far.
	 */
	public boolean setNumberOfZones(final int numberOfZones) {
		if (!this.board.getPossibleZoneNumbers(this.players.size()).contains(numberOfZones)) {
			return false;
		}
		this.numberOfZones = numberOfZones;
		return true;
	}

	/**
	 * Returns the current turn count.
	 * @return current turn count
	 */
	public int getTurnCount() {
		return turnCount;
	}
}
