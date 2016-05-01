package org.copinf.cc.net.server;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The Server class.
 */
public class Server implements Runnable {

	public final Set<GameThread> gameSet;
	private final Set<ClientThread> clients;
	private final int port;
	private final ServerSocket serverSocket;

	/**
	 * The port on which the server will be open.
	 * @param port The port number on which the server will run
	 * @throws IOException 
	 */
	public Server(final int port) throws IOException {
		gameSet = Collections.synchronizedSet(new HashSet<>());
		clients = Collections.synchronizedSet(new HashSet<>());
		this.port = port;
		this.serverSocket = new ServerSocket(port);
	}

	/**
	 * Adds a client connection to the list of clients and starts a thread to listen to it.
	 * @param client The client socket to add
	 */
	public void addClient(final Socket client) {
		final ClientThread clientThread = new ClientThread(client, this);
		clients.add(clientThread);
		clientThread.start();
	}

	/**
	 * Removes a client connection.
	 * @param client The client socket to remove
	 */
	public void removeClient(final ClientThread client) {
		clients.remove(client);
	}

	@Override
	public void run() {
		final ServerAcceptThread acceptThread = new ServerAcceptThread(serverSocket, this);
		acceptThread.start();
	}

	/**
	 * Dispatches the requests amongst the different request-processing methods.
	 * @param client The client
	 * @param req The request
	 */
	public void processRequest(final ClientThread client, final Request req) {
		final String identifier = req.identifier;
		if ("client.lobby.refresh".equals(identifier)) {
			processLobbyRefresh(client);
		} else if ("client.lobby.username".equals(identifier)) {
			processLobbyUsername(client, req);
		} else if ("client.lobby.create".equals(identifier)) {
			processLobbyCreate(client, req);
		} else if ("client.lobby.join".equals(identifier)) {
			processJoinGame(client, req);
		}
	}

	/**
	 * Broadcasts a request to all the clients connected to this server.
	 * @param req The request to broadcast
	 */
	public void broadcast(final Request req) {
		for (final ClientThread client : clients) {
			client.send(req);
		}
	}

	/**
	 * Returns a set of the GameInfos corresponding to the games hosted by this server.
	 * @return The set of GameInfos
	 */
	private HashSet<GameInfo> getGameInfos() {
		final HashSet<GameInfo> gameInfos = new HashSet<>();
		for (final GameThread game : gameSet) {
			gameInfos.add(game.getGameInfo());
		}
		return gameInfos;
	}

	/**
	 * Process a "client.lobby.refresh" request
	 * The server sends the client a set of the GameInfos associated with each game it hosts.
	 * @param client The client to which send the GameInfos
	 */
	private void processLobbyRefresh(final ClientThread client) {
		client.send(new Request("server.lobby.refresh", getGameInfos()));
	}

	/**
	 * Process a "client.lobby.username" request.
	 * The server indicates the client whether the username he asked for is available / valid or not.
	 * @param client The client
	 * @param req The request
	 */
	private void processLobbyUsername(final ClientThread client, final Request req) {
		final String username = (String) req.content;
		if (username.length() > 15 || username.length() == 0) {
			client.send(new Request("server.lobby.username", "This username is invalid !"));
		} else {
			for (final ClientThread ct : clients) {
				if (username.equals(ct.getUsername())) {
					client.send(new Request("server.lobby.username", "This username is unavailable"));
					return;
				}
			}
			client.setUsername(username);
			client.send(new Request("server.lobby.username", ""));
		}
	}

	/**
	 * Process a "client.lobby.create" request.
	 * If the game name is valid and available, it tells the client so and adds him to the list of
	 * players.
	 * @param client The client
	 * @param req The request
	 */
	private void processLobbyCreate(final ClientThread client, final Request req) {
		final GameInfo gameInfo = (GameInfo) req.content;
		final String gameName = gameInfo.name;
		boolean validName = true;
		for (final GameThread game : gameSet) {
			if (game.getGameInfo().name.equals(gameName)) {
				validName = false;
				break;
			}
		}
		if (validName) {
			final GameThread game = new GameThread(gameInfo, this);
			gameSet.add(game);
			game.start();
			client.send(new Request("server.lobby.create", true));
			game.addClient(client);
			client.send(new Request("server.lobby.join", true));
		} else {
			client.send(new Request("server.lobby.create", false));
		}
		broadcast(new Request("server.lobby.refresh", getGameInfos()));
	}

	/**
	 * Process a "client.lobby.join" request.
	 * Adds the client to the requested game then indicates the client so.
	 * @param client The client
	 * @param req The request
	 */
	private void processJoinGame(final ClientThread client, final Request req) {
		final GameInfo gameInfo = (GameInfo) req.content;
		for (final GameThread game : gameSet) {
			if (game.hashCode() == gameInfo.hashCode() && gameInfo.currentPlayers.size() < gameInfo.nbPlayersMax) {
				client.send(new Request("server.lobby.join", true));
				game.addClient(client);
				client.play(game);
				break;
			}
		}
		broadcast(new Request("server.lobby.refresh", getGameInfos()));
	}
	
	/**
	 * Remove a game from the server.
	 */
	public void removeGame(final GameThread game) {
		gameSet.remove(game);
		broadcast(new Request("server.lobby.refresh", getGameInfos()));
	}
	
	public void end() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
