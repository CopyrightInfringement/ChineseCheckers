package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides a representation of a game, with its teams, players and functions.
 */
public class Game {

	/** Player currently playing. */
	private Player currentPlayer;

	/** Different players playing this game in the order they will play. */
	private List<Player> players;

	/** Different teams in which the players are. */
	private Set<Team> teams;

	/** Current turn count (-1 before the game starts, and the first turn is the 0-th). */
	private int turnCount;

	/** Board used for this game. */
	private AbstractBoard board;

	/** Number of zones per player. */
	private int numberOfZones;
	
	/**
	 * Constructs a game.
	 * @param board board used for this game.
	 */
	public Game(final AbstractBoard board) {
		players = new ArrayList<>();
		teams = new HashSet<>();
		turnCount = -1;
		this.board = board;
		this.numberOfZones = 0;
	}

	/**
	 * Initializes this game.
	 * @throws RuntimeException if this game cannot begin.
	 */
	private void initialize() {
		if (teams.isEmpty())
			throw new RuntimeException("The game cannot begin, no team were registered.");

		board.dispatchZones(teams, this.numberOfZones);

		if (!board.getPossiblePlayerNumbers().contains(players.size()))
			throw new RuntimeException("The game cannot begin, its board " + board + " doesn't support " + players.size() + " players.");
	}

	/**
	 * Prepares this game for the next turn.
	 * @throws RuntimeException if this game cannot begin
	 */
	public void nextTurn() {
		turnCount++;

		if (turnCount == 0)
			initialize();

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
		for (final Team team : teams)
			if (team.hasWon())
				return team;
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
	public Collection<Player> getPlayer() {
		return players;
	}

	/**
	 * Returns the teams in which the players are.
	 * @return the teams
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	/**
	 * Adds a team and its players to this game.
	 * @param team team to add.
	 * @return true if the adding process succeeded.
	 */
	public boolean addTeam(final Team team) {
		if (teams.contains(team))
			return false;

		for (final Player p : team.getPlayers())
			if (players.contains(p))
				return false;

		teams.add(team);
		players.addAll(team.getPlayers());

		return true;
	}

	/**
	 * Sets the number of zones per player.
	 * @param numberOfZones The number of zones per player.
	 * @return false if the number of zones requested is not compatible with the number of players added so far.
	 */
	public boolean setNumberOfZones (int numberOfZones){
		if (!this.board.getPossiblesZoneNumber(this.players.size()).contains(numberOfZones))
			return false;
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
