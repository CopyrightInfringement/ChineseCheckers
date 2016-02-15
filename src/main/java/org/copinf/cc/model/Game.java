package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Game class.
 * @author Pierre
 */

public class Game {

	/**The player currently playing*/
	private Player currentPlayer;
	/** The different players playing this game in the order they will play */
	private List<Player> players;
	/** The different teams in which the players are*/
	private Set<Team> teams;
	/** The current turn count (-1 before the game starts, and the first turn is the 0-th)*/
	private int turnCount;
	/** The board used for this game*/
	private AbstractBoard board;
	
	/**
	 * Constructs the game.
	 * @param board The board used for this game.
	 */
	public Game(AbstractBoard board) {
		currentPlayer = null;
		players = new ArrayList<> ();
		teams = new HashSet<> ();
		turnCount = -1;
		this.board = board;
	}
	
	/**
	 * Initializes the game.
	 * @throws RuntimeException If the game cannot begin.
	 */
	private void initialize (){
		if (teams.size() == 0)
			throw new RuntimeException ("La partie ne peut pas commencer : aucune équipe n'a été enregistrée.");
		
		board.dispatchZones(teams);
		
		if (!board.getPossiblePlayerNumbers().contains(players.size()))
			throw new RuntimeException ("La partie ne peut pas commencer : le plateau " + board + " ne permet pas de jouer à " + players.size() + ".");
	}
	
	/** Prepares the game for the next turn
	 * @throws RuntimeException If the game cannot begin.
	 */
	public void nextTurn() throws RuntimeException {
		turnCount++;

		if (turnCount == 0)
			initialize();
		
		currentPlayer = players.get(turnCount % players.size());
	}

	/** Indicates whether the game is over or not.
	 * @return true if the game is over.
	 */
	public boolean isGameOver() {
		return getWinner() != null;
	}

	/**
	 * Returns the winner of the game.
	 * @return the winning player, if there is one.
	 */
	public Player getWinner() {
		for (Player p : players)
			if (p.hasWon())
				return p;
		return null;
	}

	/**
	 * Returns the current player.
	 * @return the current player.
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Returns the board.
	 * @return the board.
	 */
	public AbstractBoard getBoard() {
		return board;
	}

	/**
	 * Returns the players involved in this game.
	 * @return the players.
	 */
	public Collection<Player> getPlayer() {
		return players;
	}

	/**
	 * Returns the teams in which the players are.
	 * @return the teams.
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	/**
	 * Adds a team and its players to this game.
	 * @param team The team to add.
	 * @return false if the adding process failed.
	 */
	public boolean addTeam(final Team team) {
		if (teams.contains(team))
			return false;
		
		for (Player p : team.getPlayers())
			if (players.contains(p))
				return false;
		
		teams.add(team);
		players.addAll(team.getPlayers());
		
		return true;
	}

	/**
	 * Returns the current turn count.
	 * @return the current turn count.
	 */
	public int getTurnCount() {
		return turnCount;
	}
}
