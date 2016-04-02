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
				if ("lobby".equals(req.getSubRequest(1))) {
					server.processRequest(this, req);
				} else if (game != null && "game".equals(req.getSubRequest(1))) {
					game.processRequest(this, req);
				}
			}
			if (game != null) {
				game.removeClient(this);
			}
			server.removeClient(this);
		} catch (IOException ex) {
			System.out.println("ClientThread.run");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}

	public boolean send(final Request req) {
		try {
			out.writeObject(req);
			return true;
		} catch (IOException ex) {
			System.out.println("ClientThread.send");
			System.err.println(ex.getMessage());
			return false;
		}
	}

	private Request receive() {
		try {
			return (Request) in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			return null;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void play(final GameThread game) {
		this.game = game;
	}
}
