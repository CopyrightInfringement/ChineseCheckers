package org.copinf.cc.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A blueprint of a game, containing all of the informations needed to create
 * it.
 */

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	/** The name of the game. */
	private final String name;
	/** The maximum number of players. */
	private final int nbPlayersMax;
	/** The list of players in this game. */
	private final List<String> currentPlayers;
	/** The number of zones per player. */
	private final int nbZones;
	/** Whether this game uses teams or not. */
	private final boolean teams;
	/**
	 * The maximum time in minutes for a turn. Its value is -1 if there is no
	 * timer.
	 */
	private final double timer;
	/** The size of the heart of the board. */
	private final int size;

	private int aInumber = 0;

	/**
	 * Constructs a new GameInfo.
	 * @param name The name of the game.
	 * @param nbPlayersMax The maximum amount of players.
	 * @param nbZones The number of zones per player.
	 * @param teams If teams are enabled.
	 * @param size The radius (in squares) of the central part of the board.
	 */
	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams, final int size) {
		this(name, nbPlayersMax, nbZones, teams, size, -1);
	}

	/**
	 * Constructs a new GameInfo.
	 * @param name The name of the game.
	 * @param nbPlayersMax The maximum amount of players.
	 * @param nbZones The number of zones per player.
	 * @param teams If teams are enabled.
	 * @param size The radius (in squares) of the central part of the board.
	 * @param timer The time limit in seconds for a turn
	 */
	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams, final int size,
			final double timer) {
		this.name = name;
		this.nbPlayersMax = nbPlayersMax;
		this.currentPlayers = new ArrayList<>();
		this.nbZones = nbZones;
		this.teams = teams;
		this.size = size;
		this.timer = timer;
	}

	/**
	 * Constructs a new GameInfo.
	 * @param name The name of the game.
	 * @param nbPlayersMax The maximum amount of players.
	 * @param nbZones The number of zones per player.
	 * @param teams If teams are enabled.
	 * @param size The radius (in squares) of the central part of the board.
	 * @param timer The time limit in seconds for a turn
	 */
	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams,
			final int size, final double timer, final int aInumber) {
		this(name, nbPlayersMax, nbZones, teams, size, timer);
		this.aInumber = aInumber;
	}

	/**
	 * Get the number of players currently in-game or waiting for the game to
	 * start.
	 * @return the current number of players in this game.
	 */
	public int getCurrentPlayersNumber() {
		return getCurrentPlayers().size();
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		return getName() + " [" + getCurrentPlayersNumber() + "/" + getNbPlayersMax() + "]" + (isTeams() ? " teams " : "")
				+ (getTimer() >= 0 ? " " + getTimer() + "s " : "");
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof GameInfo && ((GameInfo) obj).getName().equals(getName());
	}

	public String getName() {
		return name;
	}

	public int getNbPlayersMax() {
		return nbPlayersMax;
	}

	public int getNbZones() {
		return nbZones;
	}

	public List<String> getCurrentPlayers() {
		return currentPlayers;
	}

	public boolean isTeams() {
		return teams;
	}

	public double getTimer() {
		return timer;
	}

	public int getSize() {
		return size;
	}

	public int getAInumber() {
		return aInumber;
	}
}
