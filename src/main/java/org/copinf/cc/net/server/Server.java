package org.copinf.cc.net.server;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server implements Runnable {

	private final Set<GameThread> waitingGames;
	private final Set<GameThread> playingGames;
	private final Set<ClientThread> clients;
	private final int port;

	public Server(final int port) {
		waitingGames = Collections.synchronizedSet(new HashSet<>());
		playingGames = Collections.synchronizedSet(new HashSet<>());
		clients = Collections.synchronizedSet(new HashSet<>());
		this.port = port;
	}

	public void addClient(final Socket client) {
		final ClientThread clientThread = new ClientThread(client, this);
		clients.add(clientThread);
		clientThread.start();
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			final ServerAcceptThread acceptThread = new ServerAcceptThread(serverSocket, this);
			acceptThread.start();

			// Prevent the socket from closing. Do something later.
			synchronized (this) {
				wait();
			}
		} catch (IOException | InterruptedException ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
	}

	//	If the request is about a game, this method transmits it to
	//	the corresponding GameThread gt through gt.processRequest(client, r)
	public void processRequest(final ClientThread client, final Request req) {
		final String identifier = req.getIdentifier();
		if ("client.lobby.refresh".equals(identifier)) {
			processLobbyRefresh(client, req);
		} else if ("client.lobby.username".equals(identifier)) {
			processLobbyUsername(client, req);
		} else if ("client.lobby.create".equals(identifier)) {
			processLobbyCreate(client, req);
		} else if ("client.lobby.join".equals(identifier)) {
			processJoinGame(client, req);
		}
	}

	public void broadcast(final Request req) {
		for (final ClientThread client : clients) {
			client.send(req);
		}
	}

	private HashSet<GameInfo> getWaitingGameInfos() {
		final HashSet<GameInfo> gameInfos = new HashSet<>();
		for (final GameThread game : waitingGames) {
			gameInfos.add(game.getGameInfo());
		}
		return gameInfos;
	}

	private void processLobbyRefresh(final ClientThread client, final Request req) {
		client.send(new Request("server.lobby.refresh", getWaitingGameInfos()));
	}

	private void processLobbyUsername(final ClientThread client, final Request req) {
		final String username = (String) req.getContent();
		boolean validUsername = username.length() <= 15;
		if (validUsername) {
			for (final ClientThread ct : clients) {
				if (username.equals(ct.getUsername())) {
					validUsername = false;
				}
			}
			if (validUsername) {
				client.setUsername(username);
			}
		}
		client.send(new Request("server.lobby.username", validUsername));
	}

	private void processLobbyCreate(final ClientThread client, final Request req) {
		final GameInfo gameInfo = (GameInfo) req.getContent();
		final String gameName = gameInfo.name;
		boolean validName = true;
		for (final GameThread game : waitingGames) {
			if (game.getGameInfo().name.equals(gameName)) {
				validName = false;
				break;
			}
		}
		if (validName) {
			for (final GameThread game : playingGames) {
				if (game.getGameInfo().name.equals(gameName)) {
					validName = false;
					break;
				}
			}
		}
		if (validName) {
			final GameThread game = new GameThread(gameInfo);
			waitingGames.add(game);
			client.send(new Request("server.lobby.create", true));
			game.addClient(client);
			client.send(new Request("server.lobby.join", true));
		} else {
			client.send(new Request("server.lobby.create", false));
		}
		broadcast(new Request("server.lobby.refresh", getWaitingGameInfos()));
	}

	private void processJoinGame(final ClientThread client, final Request req) {
		final GameInfo gameInfo = (GameInfo) req.getContent();
		boolean joined = false;
		for (final GameThread game : waitingGames) {
			if (game.hashCode() == gameInfo.hashCode()) {
				game.addClient(client);
				joined = true;
				break;
			}
		}
		client.send(new Request("server.lobby.join", joined));
		broadcast(new Request("server.lobby.refresh", getWaitingGameInfos()));
	}

	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}
		new Server(Integer.parseInt(args[0])).run();
	}
}
