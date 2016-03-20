package org.copinf.cc.net.server;

import org.copinf.cc.model.Player;
import org.copinf.cc.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	@Override
	public void run() {
		try (
			ObjectInputStream  in  = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
		) {
			this.in = in;
			this.out = out;
			Request req;
			while ((req = receive()) != null) {
				// do something
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public void send(final Request req) {
		try {
			out.writeObject(req);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public Request receive() {
		try {
			return (Request) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
			return null;
		}
	}
}
