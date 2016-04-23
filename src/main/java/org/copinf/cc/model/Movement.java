package org.copinf.cc.model;

import java.util.Stack;

/**
 * Describe a movement as a stack of coordinates. The bottom one being the starting point,
 * and the top one the destination.
 */

public class Movement extends Stack<Coordinates> {

	private static final long serialVersionUID = 42L;

	/**
	 * Constructs a new Movement.
	 */
	public Movement() {
		super();
	}

	/**
	 * Creates a movemet with only its starting point.
	 * @param coord The starting point.
	 */
	public Movement(final Coordinates coord) {
		super();
		push(coord);
	}

	/**
	 * Creates a movement with only its starting point and destination.
	 * @param orig The starting point.
	 * @param dest The destination.
	 */
	public Movement(final Coordinates orig, final Coordinates dest) {
		super();
		push(orig);
		push(dest);
	}

	@Override
	public Coordinates push(final Coordinates item) {
		final int index = search(item);
		if (index != -1) {
			removeRange(index - 1, size());
		}
		return !empty() && peek().equals(item) ? null : super.push(item);
	}

	/**
	 * Returns the origin of the movement.
	 * @return the origin.
	 */
	public Coordinates getOrigin() {
		return get(0);
	}

	/**
	 * Returns the destination of the movement.
	 * @return the destination.
	 */
	public Coordinates getDestination() {
		return peek();
	}

	/**
	 * Returns a movement composed of the two last coordinates through which this movement goes.
	 * @return The last sub-movement of this movement.
	 */
	public Movement getLastUnit() {
		if (size() == 1) {
			return new Movement(peek(), peek());
		}
		return new Movement(get(size() - 2), peek());
	}

	/**
	 * Returns a new movement with only the origin and the destination of this movement.
	 * @return the condensed movement.
	 */
	public Movement getCondensed() {
		return new Movement(getOrigin(), getDestination());
	}

	/**
	 * Returns a new movement with only the destination and the origin of this movement.
	 * @return the condensed movement.
	 */
	public Movement getReversedCondensed() {
		return new Movement(getDestination(), getOrigin());
	}
}
