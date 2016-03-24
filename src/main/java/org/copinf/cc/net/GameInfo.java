package org.copinf.cc.net;

import java.io.Serializable;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public final int nbPlayersMax;
	public int nbPlayersCurrent;
	public final int nbZones;
	public final boolean teams;
	//	-1 if no timer
	public final int timer;
	
	public GameInfo(int nbPlayersMax, int nbZones, boolean teams, int timer){
		this.nbPlayersMax = nbPlayersMax;
		this.nbZones = nbZones;
		this.teams = teams;
		this.timer = timer;
	}
	
	public GameInfo(int nbPlayersMax, int nbZones, boolean teams){
		this(nbPlayersMax, nbZones, teams, -1);
	}
}
