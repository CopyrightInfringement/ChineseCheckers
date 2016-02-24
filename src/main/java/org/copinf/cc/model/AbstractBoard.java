package org.copinf.cc.model;

import java.util.Iterator;
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

	/** Width of this board. */
	protected final int width;

	/** Height of this board. */
	protected final int height;

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
		final Square square = board[coordinates.x][coordinates.y];
		if (square == null) {
			throw new RuntimeException("Square at " + coordinates + " is outside of the board.");
		}
		return square.getPawn();
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
	 * @param orig the origin coordinates
	 * @param dest the destination coordinates
	 */
	public void move(final Coordinates orig, final Coordinates dest) {
		board[dest.x][dest.y].setPawn(board[orig.x][orig.y].getPawn());
		board[orig.x][orig.y].setPawn(null);
	}

	/**
	 * Checks if a movement is valid. A movement is represented by a list of coordinates representing
	 * the sequence of jumps performed. This method returns false if at any point in the coordinates
	 * list a movement is invalid. It returns true if the whole movement is valid.
	 * @param path list of movements
	 * @param player player performing the movements
	 * @return true if the whole movement is valid.
	 * @throws NullPointerException if the parameters are null or if the Coordinates list contains a
	 * 	null
	 */
	public boolean checkMove(final List<Coordinates> path, final Player player) {
		if (path.size() < 2 ) {
			return false;
		}

		Coordinates orig = path.get(0);
		Coordinates dest;
		final Pawn pawn = getPawn(orig);
		if (pawn == null || pawn.getOwner() != player) {
			return false;
		}

		if (path.size() == 2) {
			dest = path.get(1);
			return orig.isAdjacentTo(dest) && getPawn(dest) == null;
		}

		Coordinates middle;
		for (Iterator<Coordinates> it = path.subList(1, path.size()).iterator(); it.hasNext();) {
			dest = it.next();

			if (orig.isAdjacentTo(dest)) return false;

			middle = orig.getMiddleCoordinates(dest);
			if (middle == null || getPawn(middle) == null) return false;
			
			orig = dest;
		}

		return true;
	}

	/**
	 * Gets this board width.
	 * @return board width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets this board height.
	 * @return board height
	 */
	 public int getHeight() {
		 return height;
	 }

	/**
	 * Gets the possible number of players on the board.
	 * @return an ascending sorted set of the possible player numbers.
	 */
	public abstract SortedSet<Integer> getPossiblePlayerNumbers();

	/**
	 * Assigns to each player a zone on the board, taking in account the teams.
	 * @param teams the set of teams.
	 * @param numberOfZones the number of zones per player
	 */
	public abstract void dispatchZones(final Set<Team> teams, int numberOfZones);
	
	/**
	 * Gets the possible number of zones per player.
	 * @param playerNumber The number of players.
	 * @return an ascending sorted set of the possible zones numbers per player.
	 */
	public abstract SortedSet<Integer> getPossiblesZoneNumber (int playerNumber);
}