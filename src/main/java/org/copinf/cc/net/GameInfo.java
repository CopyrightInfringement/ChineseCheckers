package org.copinf.cc.net;

import java.io.Serializable;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public final String name;
	public final int nbPlayersMax;
	public int nbPlayersCurrent;
	public final int nbZones;
	public final boolean teams;
	public final double timer;	// -1 if no timer, in minutes
	public final int size;

	public GameInfo(final String name, final int nbPlayersMax, final int nbZones,
			final boolean teams, final int size) {
		this(name, nbPlayersMax, nbZones, teams, size, -1);
	}

	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams,
			final int size, final double timer) {
		this.name = name;
		this.nbPlayersMax = nbPlayersMax;
		this.nbPlayersCurrent = 0;
		this.nbZones = nbZones;
		this.teams = teams;
		this.size = size;
		this.timer = timer;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString(){
		return name + " [" + nbPlayersCurrent + "/" + nbPlayersMax + "]" + (teams ? " teams " : "") + (timer >= 0 ? " " + timer + "s " : "");
	}
}
