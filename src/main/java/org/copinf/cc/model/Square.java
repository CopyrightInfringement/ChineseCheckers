package org.copinf.cc.model;

/**
 * Provides internal representation of a square on a board.
 */
public class Square {

	private Pawn pawn;
	private final Coordinates coordinates;

	/**
	 * Constructs a new empty Square at specified coordinates.
	 * @param coordinates this square location
	 */
	public Square(final Coordinates coordinates) {
		this(null, coordinates);
	}

	/**
	 * Constructs a new Square with a pawn at specified coordinates.
	 * @param pawn a pawn
	 * @param coordinates this square location
	 */
	public Square(final Pawn pawn, final Coordinates coordinates) {
		this.pawn = pawn;
		this.coordinates = coordinates;
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
	 * Checks if this square is free (there's no pawn on it).
	 * @return true if this square is free
	 */
	public boolean isFree() {
		return pawn == null;
	}

	/**
	 * Gets this square coordinates.
	 * @return this square coordinates
	 */
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
}
