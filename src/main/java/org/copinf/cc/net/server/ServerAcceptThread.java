package org.copinf.cc.net.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerAcceptThread extends Thread {

	private final ServerSocket serverSocket;
	private final Server server;
	private final boolean listening = true;

	public ServerAcceptThread(final ServerSocket serverSocket, final Server server) {
		super();
		this.serverSocket = serverSocket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			while (listening) {
				server.addClient(serverSocket.accept());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
