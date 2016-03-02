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
	 * Gets a square at specified coordinates.
	 * @param coordinates the square's location
	 * @return the square
	 */
	public abstract Square getSquare(final Coordinates coordinates);

	/**
	 * Gets a pawn at specified coordinates.
	 * @param coordinates the pawn's location
	 * @return the pawn
	 */
	public Pawn getPawn(final Coordinates coordinates) {
		final Square square = getSquare(coordinates);
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
		getSquare(coordinates).setPawn(pawn);
	}

	/**
	 * Moves a pawn from a square to another square, referenced by their coordinates.
	 * This method does not perform any checks on the movement validity.
	 * @param orig the origin coordinates
	 * @param dest the destination coordinates
	 */
	public void move(final Coordinates orig, final Coordinates dest) {
		getSquare(dest).setPawn(getSquare(orig).popPawn());
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
		if (path.size() < 2) {
			return false;
		}
		Coordinates orig = path.get(0);
		final Pawn pawn = getPawn(orig);
		if (pawn == null || pawn.getOwner() != player) {
			return false;
		}
		Coordinates dest;
		if (path.size() == 2) {
			dest = path.get(1);
			return orig.isAdjacentTo(dest) && getPawn(dest) == null;
		}
		Coordinates middle;
		for (final Iterator<Coordinates> it = path.subList(1, path.size()).iterator(); it.hasNext();) {
			dest = it.next();
			if (orig.isAdjacentTo(dest)) {
				return false;
			}
			middle = orig.getMiddleCoordinates(dest);
			if (middle == null || getPawn(middle) == null) {
				return false;
			}
			orig = dest;
		}
		return true;
	}

	/**
	 * Gets this board width.
	 * @return board width
	 */
	public abstract int getWidth();

	/**
	 * Gets this board height.
	 * @return board height
	 */
	public abstract int getHeight();

	/**
	 * Gets the list of BoardZone of this board.
	 * @return list of BoardZone
	 */
	public abstract List<BoardZone> getZones();

	/**
	 * Gets the set of Coordinates pointing to the Squares of this board.
	 * @return set of Coordinates
	 */
	public abstract Set<Coordinates> coordinates();

	/**
	 * Assigns to each player a zone on the board, taking in account the teams.
	 * @param teams the set of teams.
	 * @param numberOfZones the number of zones per player
	 */
	public abstract void dispatchZones(final Set<Team> teams, final int numberOfZones);

	/**
	 * Gets the possible number of players on the board.
	 * @return an ascending sorted set of the possible player numbers.
	 */
	public abstract SortedSet<Integer> getPossiblePlayerNumbers();

	/**
	 * Gets the possible number of zones per player.
	 * @param playerNumber The number of players.
	 * @return an ascending sorted set of the possible zones numbers per player.
	 */
	public abstract SortedSet<Integer> getPossibleZoneNumbers(final int playerNumber);
}
