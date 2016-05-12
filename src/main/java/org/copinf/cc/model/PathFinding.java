package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to suggest a player how to complete a given movement.
 */
public class PathFinding {

	private final BoardMap board;
	private final Set<Coordinates> shortReachableSquares;
	private final Set<Coordinates> longReachableSquares;

	/**
	 * Constructs a PathFinding object.
	 * @param board a board
	 */
	public PathFinding(final BoardMap board) {
		this.board = board;
		this.shortReachableSquares = new HashSet<>();
		this.longReachableSquares = new HashSet<>();
	}

	/**
	 * Returns the adjacent and reachable squares.
	 * @return set of adjacent and reachable squares
	 */
	public Set<Coordinates> getShortReachableSquares() {
		return shortReachableSquares;
	}

	/**
	 * Returns the squares reachable after at least one jump.
	 * @return set of squares reachable after at least one jump
	 */
	public Set<Coordinates> getLongReachableSquares() {
		return longReachableSquares;
	}

	private void addShortReachableSquares(final Coordinates c, final boolean allowAdjacent) {
		if (board.getSquare(c) != null) {
			final Set<Coordinates> adjacentSquares = c.getAdjacentSquares();
			for (final Coordinates d : adjacentSquares) {
				if (board.getSquare(d) != null) {
					if (board.getSquare(d).isFree()) {
						if (allowAdjacent) {
							shortReachableSquares.add(d);
						}
					} else {
						final Coordinates e = d.mul(2).sub(c);
						if (board.getSquare(e) != null && board.getSquare(e).isFree()) {
							shortReachableSquares.add(e);
						}
					}
				}
			}
		}
	}

	private void addLongReachableSquare(final Coordinates c, final Set<Coordinates> seenSquares) {
		final Set<Coordinates> adjacentSquares = c.getAdjacentSquares();
		seenSquares.add(c);
		for (final Coordinates d : adjacentSquares) {
			if (board.getSquare(d) != null && !board.getSquare(d).isFree()) {
				final Coordinates e = d.mul(2).sub(c);
				if (board.getSquare(e) != null && board.getSquare(e).isFree() && !seenSquares.contains(e)) {
					if (!shortReachableSquares.contains(e)) {
						longReachableSquares.add(e);
					}
					addLongReachableSquare(e, seenSquares);
				}
			}
		}
	}

	/**
	 * Sets the directly and the indirectly reachable squares sets.
	 * @param m A valid movement
	 */
	public void setReachableSquares(final Movement m) {
		shortReachableSquares.clear();
		longReachableSquares.clear();
		final Set<Coordinates> seenSquares = new HashSet<Coordinates>();

		if (m.size() == 1) {
			addShortReachableSquares(m.getOrigin(), true);
			seenSquares.add(m.getOrigin());
			addLongReachableSquare(m.getOrigin(), seenSquares);
		} else if (m.size() == 2) {
			if (m.getOrigin().isAdjacentTo(m.getDestination())) {
				//	On ne fait rien : on a déjà fait un mouvement adjacent
			} else {
				addShortReachableSquares(m.getDestination(), false);
				seenSquares.add(m.getDestination());
				addLongReachableSquare(m.getDestination(), seenSquares);
			}
		} else if (m .size() != 0) {
			addShortReachableSquares(m.getDestination(), false);
			seenSquares.add(m.getDestination());
			addLongReachableSquare(m.getDestination(), seenSquares);
		}
	}
}
