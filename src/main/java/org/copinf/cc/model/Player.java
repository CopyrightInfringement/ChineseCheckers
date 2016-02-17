package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class Player {
	
	/** The zones where the pawns of the current player are at the beginning of the game */
	private final Set<BoardZone> initialZones;
	/** The name of the current player */
	private final String name;
	
	/**
	 * Constructs the player
	 * @param name The name of the player
	 */
	public Player(final String name) {
		this.name = name;
		initialZones = new HashSet<>();
	}
	
	/**
	 * Returns if the current player has won
	 * @return true if the current player has won
	 */
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
	
	/**
	 * Returns the name of the current player
	 * @return the name of the current player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a zone to the initial zones of the current player
	 * @param zone The new zone
	 * @return false if the adding process failed
	 */
	public boolean addInitialZone(final BoardZone zone) {
		return initialZones.add(zone);
	}
	
	/**
	 * Returns the initial zones of the current player
	 * @return the initial zones of the current player
	 */
	public Set<BoardZone> getInitialZones() {
		return initialZones;
	}
}