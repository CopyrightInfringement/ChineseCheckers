package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides a model for players.
 */
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
		for (final BoardZone boardZone : initialZones) {
			if (!boardZone.getOpponentZone().isFull(this)) {
				return false;
			}
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
		zone.fill(this);
		return initialZones.add(zone);
	}

	/**
	 * Returns the initial zones of this player.
	 * @return the initial zones of this player
	 */
	public Set<BoardZone> getInitialZones() {
		return initialZones;
	}

	@Override
	public String toString() {
		return name;
	}
}
