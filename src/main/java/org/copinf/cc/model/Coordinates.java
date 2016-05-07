package org.copinf.cc.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Provides storage for coordinates in a 3D cube.
 */
public class Coordinates implements Serializable {

	private static final long serialVersionUID = 42L;

	/** x-axis coordinate. */
	public final int x;

	/** y-axis coordinate. */
	public final int y;

	/** z-axis coordinate. */
	public final int z;

	/**
	 * The coordinates of an eastern move.
	 */
	public static final Coordinates EAST = new Coordinates(-1, 0, 1);
	/**
	 * The coordinates of a north-eastern move.
	 */
	public static final Coordinates NORTH_EAST = new Coordinates(0, -1, 1);
	/**
	 * The coordinates of a north-western move.
	 */
	public static final Coordinates NORTH_WEST = new Coordinates(1, -1, 0);
	/**
	 * The coordinates of a western move.
	 */
	public static final Coordinates WEST = new Coordinates(1, 0, -1);
	/**
	 * The coordinates of a south-eastern move.
	 */
	public static final Coordinates SOUTH_EAST = new Coordinates(0, 1, -1);
	/**
	 * The coordinates of a south-western move.
	 */
	public static final Coordinates SOUTH_WEST = new Coordinates(-1, 1, 0);

	/** Pre-defined directions around a Coordinates. */
	private static final Coordinates[] DIRECTIONS = new Coordinates[] { EAST, NORTH_EAST, NORTH_WEST, WEST, SOUTH_WEST,
			SOUTH_EAST };

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

	public Set<Coordinates> getAdjacentSquares() {
		final Set<Coordinates> adjacentSquares = new HashSet<>();
		for (final Coordinates c : DIRECTIONS) {
			adjacentSquares.add(add(this, c));
		}
		return adjacentSquares;
	}

	/**
	 * Checks if this Coordinates and a second Coordinates have Coordinates
	 * inbetween and returns these Coordinates.
	 * @param coord a second Coordinates
	 * @return the Coordinates inbetween or an empty set
	 */
	public Coordinates getMiddleCoordinates(final Coordinates coord) {
		Coordinates result = null;
		for (int i = 0; i < DIRECTIONS.length; i++) {
			final Coordinates middle = add(this, DIRECTIONS[i]);
			if (middle.isAdjacentTo(coord)) {
				if (result == null) {
					result = middle;
				} else {
					return null;
				}
			}
		}
		return result;
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
	 * Makes the addition of the current Coordinates with another one.
	 * @param c Coordinates
	 * @return resulting Coordinates
	 */
	public Coordinates add(final Coordinates c) {
		return new Coordinates(x + c.x, y + c.y, z + c.z);
	}

	/**
	 * Makes the subtraction of two Coordinates.
	 * @param a first Coordinates
	 * @param b second Coordinates
	 * @return resulting Coordinates
	 */
	public static Coordinates sub(final Coordinates a, final Coordinates b) {
		return new Coordinates(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	/**
	 * Makes the subtraction of the current Coordinates with another one.
	 * @param c Coordinates
	 * @return resulting Coordinates
	 */
	public Coordinates sub(final Coordinates c) {
		return new Coordinates(x - c.x, y - c.y, z - c.z);
	}

	/**
	 * Makes the multiplication of a Coordinates and an integer.
	 * @param a a Coordinates
	 * @param k an integer
	 * @return resulting Coordinates
	 */
	public static Coordinates mul(final Coordinates a, final int k) {
		return new Coordinates(a.x * k, a.y * k, a.z * k);
	}

	/**
	 * Makes the multiplication of the current coordinates with an integer.
	 * @param k an integer
	 * @return resulting Coordinates
	 */
	public Coordinates mul(final int k) {
		return new Coordinates(k * x, k * y, k * z);
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}
}
