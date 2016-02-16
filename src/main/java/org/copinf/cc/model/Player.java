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
		throw new UnsupportedOperationException();
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
