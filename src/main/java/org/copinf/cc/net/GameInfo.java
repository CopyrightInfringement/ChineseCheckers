package org.copinf.cc.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A blueprint of a game, containing all of the informations needed to create it.
 */

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public final String name;
	public final int nbPlayersMax;
	public List<String> currentPlayers;
	public final int nbZones;
	public final boolean teams;
	public final double timer;	// -1 if no timer, in minutes
	public final int size;

	/**
	 * @param name The name of the game.
	 * @param nbPlayersMax The maximum amount of players.
	 * @param nbZones The number of zones per player.
	 * @param teams If teams are enabled.
	 * @param size The radius (in squares) of the central part of the board.
	 */
	public GameInfo(final String name, final int nbPlayersMax, final int nbZones,
			final boolean teams, final int size) {
		this(name, nbPlayersMax, nbZones, teams, size, -1);
	}

	/**
	 * @param name The name of the game.
	 * @param nbPlayersMax The maximum amount of players.
	 * @param nbZones The number of zones per player.
	 * @param teams If teams are enabled.
	 * @param size The radius (in squares) of the central part of the board.
	 * @param timer The time limit in seconds for a turn
	 */
	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams,
			final int size, final double timer) {
		this.name = name;
		this.nbPlayersMax = nbPlayersMax;
		this.currentPlayers = new ArrayList<>();
		this.nbZones = nbZones;
		this.teams = teams;
		this.size = size;
		this.timer = timer;
	}

	/**
	 * Get the number of players currently in-game or waiting for the game to start.
	 * @return the current number of players in this game.
	 */
	public int getCurrentPlayersNumber() {
		return currentPlayers.size();
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name
			+ " [" + getCurrentPlayersNumber() + "/" + nbPlayersMax + "]"
			+ (teams ? " teams " : "")
			+ (timer >= 0 ? " " + timer + "s " : "");
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof GameInfo && ((GameInfo) obj).name.equals(name);
	}
}
