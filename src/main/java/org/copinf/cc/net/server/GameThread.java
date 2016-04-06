package org.copinf.cc.net.server;

import org.copinf.cc.model.Game;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	public void run() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException ex) {}
	}

	public void processRequest(final ClientThread client, final Request req) {
		System.out.println("GAMETHREAD: received " + req);
		final String sub2 = req.getSubRequest(2);
		final String sub3 = req.getSubRequest(3);
		if ("players".equals(sub2)) {
			if ("refresh".equals(sub3)) {
				processPlayersRefresh(client);
			}
		}
	}

	public void processPlayersRefresh(final ClientThread client) {
		client.send(new Request("server.game.players.refresh", getPlayersName()));
	}

	private ArrayList<String> getPlayersName() {
		final ArrayList<String> players = new ArrayList<>();
		for (final ClientThread cl : clients) {
			players.add(cl.getUsername());
		}
		return players;
	}

	public void broadcast(final Request req) {
		for (final ClientThread client : clients) {
			client.send(req);
		}
	}

	public void addClient(final ClientThread client) {
		if (clients.add(client)) {
			gameInfo.nbPlayersCurrent++;
		}
		if (gameInfo.nbPlayersCurrent == gameInfo.nbPlayersMax) {
			broadcast(new Request("server.game.start"));
		}
	}

	public void removeClient(final ClientThread client) {
		if (clients.remove(client)) {
			gameInfo.nbPlayersCurrent--;
		}
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	@Override
	public int hashCode() {
		return gameInfo.name.hashCode();
	}
}
