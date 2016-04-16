package org.copinf.cc.net.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerAcceptThread extends Thread {

	private final ServerSocket serverSocket;
	private final Server server;
	private final boolean listening = true;

	/**
	 * @param serverSocket The ServerSocket used to accept connections
	 * @param server The server to which the clients wish to connect
	 */
	public ServerAcceptThread(final ServerSocket serverSocket, final Server server) {
		super("Server accept thread");
		this.serverSocket = serverSocket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			while (listening) {
				server.addClient(serverSocket.accept());
			}
		} catch (IOException ex) {
			System.out.println("ServerAcceptThread.run");
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
	}
}
