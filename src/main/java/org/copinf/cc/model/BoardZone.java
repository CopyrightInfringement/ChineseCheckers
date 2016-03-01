package org.copinf.cc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The class representing a zone of the board.
 * It represents the starting area of a player, and is associated with the BoardZone he must fill
 * with his pawns in order to win.
 */
public class BoardZone {

	private final Map<Coordinates, Square> squares;

	/** Zone which must be filled with the owner's pawns. */
	private BoardZone opponentZone;

	/**
	 * Constructs a new empty BoardZone.
	 */
	public BoardZone() {
		squares = new HashMap<>();
	}

	/**
	 * Adds a square to this BoardZone.
	 * @param coord Coordinates where to add a Square
	 * @param square Square to add
	 */
	public void addSquare(final Coordinates coord, final Square square) {
		squares.put(coord, square);
	}

	/**
	 * Retrieves the map of Coordinates and corresponding Square of this BoardZone.
	 * @return the map of Coordinates and Square
	 */
	public Map<Coordinates, Square> getSquares() {
		return squares;
	}

	/**
	 * Gets the set of Coordinates pointing to the Squares of this BoardZone.
	 * @return set of Coordinates
	 */
	public Set<Coordinates> coordinates() {
		return squares.keySet();
	}

	/**
	 * Returns the BoardZone in which the owner must move its pawns.
	 * @return the opposed BoardZone
	 */
	public BoardZone getOpponentZone() {
		return opponentZone;
	}

	/**
	 * Sets the BoardZone in which the owner must move its pawns.
	 * @param zone the opposed BoardZone
	 */
	public void setOpponentZone(final BoardZone zone) {
		this.opponentZone = zone;
	}

	/**
	 * Indicates whether or not this zone is filled with the pawns of a given player.
	 * @param player player whose pawns we want to check the presence.
	 * @return true if the zone is full
	 */
	public boolean isFull(final Player player) {
		for (Square s : squares.values()) {
			if (s.getPawn() == null || s.getPawn().getOwner() != player) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fill this BoardZone with pawns from a specified player. Creates the pawns.
	 * @param player a player
	 */
	public void fill(final Player player) {
		for (Square s : squares.values()) {
			s.setPawn(new Pawn(player));
		}
	}
}
