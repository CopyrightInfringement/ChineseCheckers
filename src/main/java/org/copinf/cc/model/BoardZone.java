package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * The class representing a zone of the board.
 * It represents the starting area of a player, and is associated with the BoardZone he must fill
 * with his pawns in order to win.
 */
public class BoardZone {

	private final Set<Square> squares;

	/** Zone which must be filled with the owner's pawns. */
	private BoardZone opponentZone;

	/**
	 * Constructs a new empty BoardZone.
	 */
	public BoardZone() {
		squares = new HashSet<>();
	}

	/**
	 * Adds a square to this BoardZone.
	 * @param square square to add.
	 * @return true if the square has been successfully added.
	 */
	public boolean addSquare(final Square square) {
		return squares.add(square);
	}

	/**
	 * Returns the BoardZone in which the owner must move its pawns.
	 * @return the opposed BoardZone
	 */
	public BoardZone getOpponentZone() {
		return opponentZone;
	}

	/**
	 * Sets the BoardZone in which the owner must move its pawns, and reciprocally.
	 * @param zone the opposed BoardZone
	 */
	public void setOpponentZone(final BoardZone zone) {
		this.opponentZone = zone;
		if (zone == null)
			return;
		zone.opponentZone = this;
	}

	/**
	 * Indicates whether or not this zone is filled with the pawns of a given player.
	 * @param player player whose pawns we want to check the presence.
	 * @return true if the zone is full
	 */
	public boolean isFull(final Player player) {
		for (final Square s : squares)
			if (s.getPawn() == null || s.getPawn().getOwner() != player)
				return false;
		return true;
	}
}
