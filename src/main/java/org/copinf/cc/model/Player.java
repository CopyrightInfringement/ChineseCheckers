package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Player {

	/** Zones where the pawns of this  player are at the beginning of the game. */
	private final Set<BoardZone> initialZones;

	/** Name of this player. */
	private final String name;

	/**
	 * Constructs a player.
	 * @param name name of this player
	 */
	public Player(final String name) {
		this.name = name;
		initialZones = new HashSet<>();
	}

	/**
	 * Checks if this player has won.
	 * @return true if this player has won
	 */
	public boolean hasWon() {
		final Set<BoardZone> opponentZones = new HashSet<>();
		for (final BoardZone bz : initialZones)
			opponentZones.add(bz.getOpponentZone());
		for (final BoardZone bz : opponentZones) {
			if (!bz.isFull(this))
				return false;
		}
		return true;
	}

	/**
	 * Returns the name of this player.
	 * @return name of this player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds a zone to the initial zones of this player.
	 * @param zone a new zone
	 * @return false if the adding process failed
	 */
	public boolean addInitialZone(final BoardZone zone) {
		return initialZones.add(zone);
	}

	/**
	 * Returns the initial zones of this player.
	 * @return the initial zones of this player
	 */
	public Set<BoardZone> getInitialZones() {
		return initialZones;
	}
}
