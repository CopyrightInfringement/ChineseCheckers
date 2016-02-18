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
			(Math.abs(this.y - coord.y) == 1 && this.x == coord.x) ||
			(this.y % 2 == 0 && this.x == coord.x + 1 && Math.abs(this.y - coord.y) == 1) ||
			(this.y % 2 == 1 && this.x == coord.x - 1 && Math.abs(this.y - coord.y) == 1);
	}

	/**
	 * Checks if this Coordinates and a second Coordinates have one Coordinates inbetween and are
	 * aligned then returns that Coordinates.
	 * @param coord a second Coordinates
	 * @return the Coordinates inbetween or null
	 */
	public Coordinates getMiddleCoordinates(final Coordinates coord) {
		Coordinates found1 = null;
		Coordinates found2 = null;
		for (int x = this.x - 1; x < this.x + 1; x++) {
			for (int y = this.y - 1; y < this.y + 1; y++) {
				if (found1 == null) {
					found1 = new Coordinates(x, y);
					if (!found1.isAdjacentTo(this) && !found1.isAdjacentTo(coord)) {
						found1 = null;
					}
				} else {
					found2 = new Coordinates(x, y);
					if (found2.isAdjacentTo(this) && found2.isAdjacentTo(coord)) {
						return null;
					}
				}
			}
		}
		return found1;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}
