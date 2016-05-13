package org.copinf.cc.model;

/**
 * Provides a model for the pawns on the board.
 */
public class Pawn {

	/** The owner of this pawn. */
	private final Player owner;

	/**
	 * @param owner The owner of this pawn.
	 */
	public Pawn(final Player owner) {
		this.owner = owner;
	}

	/**
	 * Returns the owner of this pawn.
	 */
	public Player getOwner() {
		return owner;
	}
}
