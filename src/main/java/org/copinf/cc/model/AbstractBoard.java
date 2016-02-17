package org.copinf.cc.model;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Provides a base class for board representation.
 */
public abstract class AbstractBoard {

	/**
	 * Internal data structure for the board. A null Square means that the square is outside of the
	 * board and should not be accessed.
	 * The board is accessed with [X][Y] coordinates.
	 */
	protected Square[][] board;

	private final int width;
	private final int height;

	/**
	 * Constructs a new board.
	 * @param width width of this board
	 * @param height height of this board
	 */
	public AbstractBoard(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.board = new Square[width][height];
	}

	/**
	 * Gets a pawn at specified coordinates.
	 * @param coordinates the pawn's location
	 * @return the pawn
	 */
	public Pawn getPawn(final Coordinates coordinates) {
		Square s = board[coordinates.x][coordinates.y];
		if (s != null)
			return s.getPawn();
		return null;
	}

	/**
	 * Sets a pawn at specified coordinates.
	 * @param pawn the pawn
	 * @param coordinates the pawn's location
	 */
	public void setPawn(final Pawn pawn, final Coordinates coordinates) {
		board[coordinates.x][coordinates.y].setPawn(pawn);
	}

	/**
	 * Moves a pawn from a square to another square, referenced by their coordinates.
	 * This method does not perform any checks on the movement validity.
	 * @param orig the orgigin coordinates
	 * @param dest the destination coordinates
	 */
	public void move(final Coordinates orig, final Coordinates dest) {
		board[dest.x][dest.y].setPawn(board[orig.x][orig.y].getPawn());
	}

	/**
	 * Checks if a movement is valid. A movement is represented by a list of coordinates representing
	 * the sequence of jumps performed. This method returns false if at any point in the coordinates
	 * list a movement is invalid. It returns true if the whole movement is valid.
	 * @param path list of movements
	 * @param player player performing the movements
	 * @return true if the whole movement is valid.
	 */
	public boolean checkMove(final List<Coordinates> path, final Player player) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the possible number of players on the board.
	 * @return an ascending sorted set of the possible player numbers.
	 */
	public abstract SortedSet<Integer> getPossiblePlayerNumbers();

	/**
	 * Assigns to each player a zone on the board, taking in account the teams.
	 * @param teams the set of teams.
	 */
	public abstract void dispatchZones(final Set<Team> teams);
}
