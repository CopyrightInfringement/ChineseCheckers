package org.copinf.cc.net.server;

import org.copinf.cc.model.Game;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameThread extends Thread {

	private final GameInfo gameInfo;
	private final Game game;
	private final Set<ClientThread> clients;

	public GameThread(final GameInfo gameInfo) {
		super();
		this.gameInfo = gameInfo;
		this.clients = Collections.synchronizedSet(new HashSet<>());
		game = null;
	}

	@Override
	public void run() {}

	public void processRequest(final ClientThread client, final Request req) {}

	public GameInfo getGameInfo() {
		return gameInfo;
	}
}
