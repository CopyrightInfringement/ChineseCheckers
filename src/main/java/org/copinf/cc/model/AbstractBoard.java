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
	 * Moves a pawn from a square to another square, referenced by their
	 * coordinates. This method does not perform any checks on the movement
	 * validity.
	 * @param movement a movement
	 */
	public void move(final Movement movement) {
		if (movement.size() >= 2) {
			getSquare(movement.getDestination()).setPawn(getSquare(movement.getOrigin()).popPawn());
		}
	}

	/**
	 * Checks if a movement is valid. A movement is represented by a list of
	 * coordinates representing the sequence of jumps performed. This method
	 * returns false if at any point in the coordinates list a movement is
	 * invalid. It returns true if the whole movement is valid.
	 * @param path list of movements
	 * @param player player performing the movements
	 * @return true if the whole movement is valid.
	 * @throws NullPointerException if the parameters are null or if the
	 *             Coordinates list contains a null
	 */
	public boolean checkMove(final Movement path, final Player player) {
		if (path.size() < 2) {
			return true;
		}

		final Coordinates orig = path.getOrigin();

		//	Si toutes les cases sont bien dans le plateau, existent et sont vides (à l'exception du départ)
		for (final Coordinates c : path) {
			if (c == null || getSquare(c) == null) {
				return false;
			} else if (!c.equals(orig) && !getSquare(c).isFree()) {
				return false;
			}
		}

		final Pawn pawn = getPawn(orig);

		//	Si on essaye de deplacer un pion qui nous appartient pas ou qui n'existe pas
		if (pawn == null || pawn.getOwner() != player) {
			return false;
		}

		final Coordinates dest = path.getDestination();

		//	S'il s'agit d'un mouvement adjacent
		if (path.size() == 2 && orig.isAdjacentTo(dest)) {
			return true;
		}

		final Iterator<Coordinates> it = path.iterator();
		Coordinates prev = it.next();
		while (it.hasNext()) {
			final Coordinates cur = it.next();

			final Coordinates middle = cur.getMiddleCoordinates(prev);
			if (middle == null || getSquare(middle) == null || getSquare(middle).isFree()
					|| !getSquare(cur).isFree()) {
				return false;
			}

			prev = cur;
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
	 * @param teams the list of teams.
	 * @param numberOfZones the number of zones per player
	 */
	public abstract void dispatchZones(final List<Team> teams, final int numberOfZones);

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

	/**
	 * Checks players can have teams with a certain player number. Teams are
	 * composed of 2 players.
	 * @param playerNumber The number of players.
	 * @return true if player can play in teams
	 */
	public abstract boolean getPossibleTeam(final int playerNumber);

	/**
	 * Gets the default size of this board.
	 * @param playerNumber The number of players.
	 * @return default size.
	 */
	public abstract int getDefaultSize(final int playerNumber);
}
