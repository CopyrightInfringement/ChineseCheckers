package org.copinf.cc.net.server;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.io.IOException;
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
			ServerAcceptThread acceptThread = new ServerAcceptThread(serverSocket, this);
			acceptThread.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	//	If the request is about a game, this method transmits it to
	//	the corresponding GameThread gt through gt.processRequest(client, r)
	private void processRequest(final ClientThread client, final Request req) {}
}
