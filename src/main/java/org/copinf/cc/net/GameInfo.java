package org.copinf.cc.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public final String name;
	public final int nbPlayersMax;
	public List<String> currentPlayers;
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
		this.currentPlayers = new ArrayList<>();
		this.nbZones = nbZones;
		this.teams = teams;
		this.size = size;
		this.timer = timer;
	}

	public int getCurrentPlayersNumber(){
		return currentPlayers.size();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString(){
		return name + " [" + getCurrentPlayersNumber() + "/" + nbPlayersMax + "]" + (teams ? " teams " : "") + (timer >= 0 ? " " + timer + "s " : "");
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GameInfo)
			return ((GameInfo) o).name.equals(name);
		else
			return false;
	}
}
