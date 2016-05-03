package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Give a set of possible movements.
 */
public class MovementSuggestion {
	private static Set<Coordinates> getAdjacent(final Coordinates coordinates, AbstractBoard board) {
		final Set<Coordinates> adjacent = new HashSet<>();

		final int x = coordinates.x;
		final int y = coordinates.y;

		for (int s = -1; s <= 1; s += 2) {
			adjacent.add(new Coordinates(x + s, y - s));
			adjacent.add(new Coordinates(x + s, y));
			adjacent.add(new Coordinates(x, y + s));
		}

		removeOutsideCoordinates(adjacent, board);

		return adjacent;
	}

	private static void removeOutsideCoordinates(Set<Coordinates> set, AbstractBoard board) {
		final Iterator<Coordinates> it = set.iterator();

		while (it.hasNext()) {
			final Coordinates c = it.next();
			final Square square = board.getSquare(c);
			if (square == null) {
				it.remove();
			}
		}
	}

	private static Coordinates getCoordinatesOver(Coordinates origin, Coordinates obstacle, AbstractBoard board) {
		final int x = obstacle.x - origin.x;
		final int y = obstacle.y - origin.y;
		final Coordinates over = new Coordinates(obstacle.x + x, obstacle.y + y);

		if (board.getSquare(over) != null) {
			return over;
		} else {
			return null;
		}
	}

	private static Set<Movement> getJumpMovement(Movement current, Set<Coordinates> visited, AbstractBoard board) {
		final Set<Movement> movements = new HashSet<>();
		final Coordinates origin = current.getDestination();

		visited.add(origin);
		movements.add(current);

		for (final Coordinates adjacent : getAdjacent(origin, board)) {
			//	Si cette case est occupée
			if (!board.getSquare(adjacent).isFree()) {
				final Coordinates over = getCoordinatesOver(origin, adjacent, board);
				//	Et que la case derrière est libre et n'a pas été visitée
				if (((over != null) && !visited.contains(over)) && board.getSquare(over).isFree()) {
					movements.addAll(getJumpMovement(lengthen(current, over), visited, board));
				}
			}
		}

		return movements;
	}

	/**
	 * Returns all possible movement from a square.
	 * 
	 * @param origin
	 *            The origin coordinates
	 */
	public static Set<Movement> getMovements(Coordinates origin, AbstractBoard board) {
		final Set<Movement> movements = new HashSet<>();
		final Set<Coordinates> visited = new HashSet<>();

		for (final Coordinates adjacent : getAdjacent(origin, board)) {
			//	Mouvements simples
			if (board.getSquare(adjacent).isFree()) {
				movements.add(new Movement(origin, adjacent));
			} else {
				//	Sauts
				final Coordinates over = getCoordinatesOver(origin, adjacent, board);
				if ((over != null) && !visited.contains(over) && board.getSquare(over).isFree()) {
					movements.addAll(getJumpMovement(new Movement(origin, over), visited, board));
				}
			}
		}

		return movements;
	}

	private static Movement lengthen(Movement movement, Coordinates destination) {
		final Movement m = new Movement();
		for (int i = 0; i < movement.size(); i++) {
			m.add(movement.get(i));
		}

		m.add(destination);

		return m;
	}
}
