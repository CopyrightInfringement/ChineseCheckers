package org.copinf.cc.net.server;

import org.copinf.cc.model.Player;
import org.copinf.cc.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	private String username;
	private final Socket client;
	private Player player;
	private final Server server;
	private GameThread game;

	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ClientThread(final Socket client, final Server server) {
		super();
		this.client = client;
		this.server = server;
		in = null;
		out = null;
	}

	private static ObjectOutputStream getObjectOutputStream(final OutputStream os)
			throws IOException {
		final ObjectOutputStream out = new ObjectOutputStream(os);
		out.flush();
		return out;
	}

	@Override
	public void run() {
		try (
			ObjectOutputStream out = getObjectOutputStream(client.getOutputStream());
			ObjectInputStream  in  = new ObjectInputStream(client.getInputStream());
		) {
			this.in = in;
			this.out = out;
			Request req;
			while ((req = receive()) != null) {
				server.processRequest(this, req);
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}

	public void send(final Request req) {
		try {
			out.writeObject(req);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}

	private Request receive() {
		try {
			return (Request) in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
			return null;
		}
	}
}
