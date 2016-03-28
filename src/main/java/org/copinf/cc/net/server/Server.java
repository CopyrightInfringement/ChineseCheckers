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

	private final Set<GameInfo> waitingGames;
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
			client.send(new Request("server.lobby.refresh", (Serializable) waitingGames));
		} else if ("client.lobby.username".equals(identifier)) {
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
	}

	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}
		new Server(Integer.parseInt(args[0])).run();
	}
}
