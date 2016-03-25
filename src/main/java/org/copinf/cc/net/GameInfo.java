package org.copinf.cc.net;

import java.io.Serializable;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public final String name;
	public final int nbPlayersMax;
	public int nbPlayersCurrent;
	public final int nbZones;
	public final boolean teams;
	public final int timer;	// -1 if no timer

	public GameInfo(final String name, final int nbPlayersMax, final int nbZones,
			final boolean teams) {
		this(name, nbPlayersMax, nbZones, teams, -1);
	}

	public GameInfo(final String name, final int nbPlayersMax, final int nbZones, final boolean teams,
			final int timer) {
		this.name = name;
		this.nbPlayersMax = nbPlayersMax;
		this.nbPlayersCurrent = 0;
		this.nbZones = nbZones;
		this.teams = teams;
		this.timer = timer;
	}
}
