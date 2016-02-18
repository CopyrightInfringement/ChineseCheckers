package org.copinf.cc.model;

/**
 * Provides storage for coordinates on a two-dimensionnal grid.
 */
public class Coordinates {

	/** x-axis coordinate */
	public final int x;

	/** y-axis coordinate */
	public final int y;

	/**
	 * Constructs a new Coordinate.
	 * @param x x-axis coordinate
	 * @param y y-axis coordinate
	 */
	public Coordinates(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the hashcode for this Coordinates.
	 * @return a hash code for this Coordinates.
	 */
	@Override
	public int hashCode() {
		long bits = (long) x;
		bits ^= (long) y * 31;
		return ((int) bits) ^ ((int) (bits >> 32));
	}

	/**
	 * Determines whether or not two coordinates are equal. Two instances of Coordinates are equal if
	 * the values of their x and y member fields, representing their position in the coordinate space,
	 * are the same.
	 * @param obj an object to be compared with this Point2D
	 * @return true if the object to be compared is an instance of Point2D and has the same values;
	 * false otherwise.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Coordinates) {
			final Coordinates coord = (Coordinates) obj;
			return x == coord.x && y == coord.y;
		}
		return false;
	}

	/**
	 * Checks if this Coordinates is adjacent to another Coordinates.
	 * @param coord a second Coordinates
	 * @return true if they are adjacent
	 */
	public boolean isAdjacentTo(final Coordinates coord) {
		return (Math.abs(this.x - coord.x) == 1 && this.y == coord.y) ||
			(Math.abs(this.y - coord.y) == 1 && this.x == coord.x);
	}

	/**
	 * Checks if this Coordinates and a second Coordinates have one Coordinates inbetween and returns
	 * that Coordinates.
	 * @param coord a second Coordinates
	 * @return the Coordinates inbetween or null
	 */
	public Coordinates getMiddleCoordinate(final Coordinates coord) {
		if (x == coord.x + 2 && y == coord.y) {
			return new Coordinates(x - 1, y);
		} else if (x == coord.x - 2 && y == coord.y) {
			return new Coordinates(x + 1, y);
		} else if (x == coord.x && y == coord.y + 2) {
			return new Coordinates(x, y - 1);
		} else if (x == coord.x && y == coord.y - 2) {
			return new Coordinates(x, y + 1);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}
