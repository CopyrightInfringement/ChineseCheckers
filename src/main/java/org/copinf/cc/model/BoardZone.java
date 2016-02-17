package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * The class representing a zone of the board.
 * It represents the starting area of a player, and is associated with the BoardZone he must fill with his pawns in order to win.
 * @author pierre
 */
public class BoardZone {
	private final Set<Square> squares;
	/** The zone which must be filled with the owner's pawns */
	private BoardZone opponentZone;

	/**
	 * Constructs a new BoardZone
	 */
	public BoardZone() {
		squares = new HashSet<>();
	}

	/**
	 * Adds a square to the area.
	 * @param square The square to add.
	 * @return true if the square has been successfully added.
	 */
	public boolean addSquare(final Square square) {
		return squares.add(square);
	}

	/**
	 * Returns the BoardZone in which the owner must move his pawns.
	 * @return The BoardZone opposed.
	 */
	public BoardZone getOpponentZone() {
		return opponentZone;
	}

	/**
	 * Sets the BoardZone in which the owner must move his pawns.
	 */	
	public void setOpponentZone(final BoardZone zone) {
		this.opponentZone = zone;
	}

	/**
	 * Indicates whether or not this zone is filled with the pawns of a given player.
	 * @param player The player whose pawns we want to check the presence.
	 * @return
	 */
	public boolean isFull(final Player player) {
		for (Square s : squares)
			if (s.getPawn() == null || s.getPawn().getOwner() != player)
				return false;
		return true;
	}
}
