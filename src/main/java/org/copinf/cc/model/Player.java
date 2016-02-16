package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Player {

	private final Set<BoardZone> initialZones;
	private final String name;

	public Player(final String name) {
		this.name = name;
		initialZones = new HashSet<>();
	}

	public boolean hasWon() {
		Set<BoardZone> opponentZones = new HashSet<BoardZone> ();
		for (BoardZone bz : initialZones)
			opponentZones.add(bz.getOpponentZone());
		for (BoardZone bz : opponentZones) {
			if (!bz.isFull(this))
				return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public boolean addInitialZone(final BoardZone zone) {
		return initialZones.add(zone);
	}

	public Set<BoardZone> getInitialZones() {
		return initialZones;
	}
}
