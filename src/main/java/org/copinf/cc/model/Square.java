package org.copinf.cc.model;

/**
 * Provides internal representation of a square on a board.
 */
public class Square {

	private Pawn pawn;

	/**
	 * Constructs a new empty Square.
	 */
	public Square() {
		this(null);
	}

	/**
	 * Constructs a new Square with a pawn.
	 * @param pawn a pawn
	 */
	public Square(final Pawn pawn) {
		this.pawn = pawn;
	}

	/**
	 * Gets the pawn in this square.
	 * @return the pawn
	 */
	public Pawn getPawn() {
		return pawn;
	}

	/**
	 * Sets a pawn in this square.
	 * @param pawn a pawn
	 */
	public void setPawn(final Pawn pawn) {
		this.pawn = pawn;
	}

	/**
	 * Pops the pawn in this square.
	 * @return the pawn that was located in this square or null if it was empty
	 */
	public Pawn popPawn() {
		Pawn prev = pawn;
		pawn = null;
		return prev;
	}

	/**
	 * Checks if this square is free (there's no pawn on it).
	 * @return true if this square is free
	 */
	public boolean isFree() {
		return pawn == null;
	}
}
