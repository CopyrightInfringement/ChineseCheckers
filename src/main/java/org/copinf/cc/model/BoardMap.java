package org.copinf.cc.model;

import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Pawn;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Square;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class BoardMap extends HashMap<Coordinates, Square> {
	public BoardMap() {
		super();
	}

	public BoardMap(final Map<Coordinates, Square> map) {
		super(map);
	}

	public Square getSquare(final Coordinates coordinates) {
		return get(coordinates);
	}

	public Set<Coordinates> getPlayersPawns(final Player player) {
		final Set<Coordinates> set = new HashSet<>();
		for (final Map.Entry<Coordinates, Square> entry : entrySet()) {
			final Pawn pawn = entry.getValue().getPawn();
			if (pawn != null && pawn.getOwner() == player) {
				set.add(entry.getKey());
			}
		}
		return set;
	}
}
