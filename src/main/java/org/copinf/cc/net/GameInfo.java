package org.copinf.cc.net;

import java.io.Serializable;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 42L;

	public int nbPlayersMax;
	public int nbPlayersCurrent;
	public int nbZones;
	public boolean teams;
}
