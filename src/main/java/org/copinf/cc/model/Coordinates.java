package org.copinf.cc.model;

import java.util.Objects;

/**
 * Provides storage for coordinates in a 3D cube.
 */
public class Coordinates {

	/** x-axis coordinate. */
	public final int x;

	/** y-axis coordinate. */
	public final int y;

	/** z-axis coordinate. */
	public final int z;

	public static final Coordinates EAST = new Coordinates(-1, 0, 1);
	public static final Coordinates NORTH_EAST = new Coordinates(0, -1, 1);
	public static final Coordinates NORTH_WEST = new Coordinates(1, -1, 0);
	public static final Coordinates WEST = new Coordinates(1, 0, -1);
	public static final Coordinates SOUTH_EAST = new Coordinates(0, 1, -1);
	public static final Coordinates SOUTH_WEST = new Coordinates(-1, 1, 0);

	/** Pre-defined directions around a Coordinates. */
	private static final Coordinates[] DIRECTIONS = new Coordinates[] {
		EAST, NORTH_EAST, NORTH_WEST, WEST, SOUTH_WEST, SOUTH_EAST
	};

	/**
	 * Constructs a new Coordinates. Calculates z such as x + y + z = 0.
	 * @param x x-axis coordinate
	 * @param y y-axis coordinate
	 */
	public Coordinates(final int x, final int y) {
		this(x, y, -x - y);
	}

	/**
	 * Constructs a new Coordinates.
	 * @param x x-axis coordinate
	 * @param y y-axis coordinate
	 * @param z z-axis coordinate
	 */
	public Coordinates(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Coordinates) {
			final Coordinates coord = (Coordinates) obj;
			return x == coord.x && y == coord.y && z == coord.z;
		}
		return false;
	}

	/**
	 * Checks if this Coordinates is adjacent to another Coordinates.
	 * @param coord a second Coordinates
	 * @return true if they are adjacent
	 */
	public boolean isAdjacentTo(final Coordinates coord) {
		final Coordinates sum = Coordinates.sub(this, coord);
		for (int i = 0; i < DIRECTIONS.length; i++) {
			if (sum.equals(DIRECTIONS[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if this Coordinates and a second Coordinates have one Coordinates inbetween
	 * then returns that Coordinates.
	 * @param coord a second Coordinates
	 * @return the Coordinates inbetween or null
	 */
	public Coordinates getMiddleCoordinates(final Coordinates coord) {
		Coordinates added;
		for (int i = 0; i < DIRECTIONS.length; i++) {
			added = add(this, DIRECTIONS[i]);
			if (added.isAdjacentTo(coord)) {
				return added;
			}
		}
		return null;
	}

	/**
	 * Makes the addition of two Coordinates.
	 * @param a first Coordinates
	 * @param b second Coordinates
	 * @return resulting Coordinates
	 */
	public static Coordinates add(final Coordinates a, final Coordinates b) {
		return new Coordinates(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	/**
	 * Makes the substraction of two Coordinates.
	 * @param a first Coordinates
	 * @param b second Coordinates
	 * @return resulting Coordinates
	 */
	public static Coordinates sub(final Coordinates a, final Coordinates b) {
		return new Coordinates(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	/**
	 * Makes the multiplication of a Coordinates and an integer.
	 * @param a a Coordinates
	 * @param k and integer
	 * @return resulting Coordinates
	 */
	public static Coordinates mul(final Coordinates a, final int k) {
		return new Coordinates(a.x * k, a.y * k, a.z * k);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}
}
