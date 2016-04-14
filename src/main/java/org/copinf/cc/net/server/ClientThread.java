package org.copinf.cc.net.server;

import org.copinf.cc.net.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * A thread reading from and writing to a client.
 */
public class ClientThread extends Thread {

	private static final Logger LOGGER = Logger.getLogger(ClientThread.class.getName());

	private String username;
	private final Socket client;
	private final Server server;
	private GameThread game;

	private ObjectInputStream in;
	private ObjectOutputStream out;

	/**
	 * @param client The socket allowing to communicate with the client.
	 * @param server The server.
	 */
	public ClientThread(final Socket client, final Server server) {
		super("Server client thread");
		this.client = client;
		this.server = server;
		in = null;
		out = null;
	}

	/**
	 * Create a new ObjectOutputStream from an OutputStream.
	 * @param os The output stream
	 * @return The ObjectOutPutSTream.
	 * @throws IOException if there is an IO error with the output stream
	 */
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

	/**
	 * Send a request to the client.
	 * @param req The request to send.
	 */
	public void send(final Request req) {
		try {
			out.reset();
			LOGGER.info("Server : sending to " + username + " " + req);
			out.writeObject(req);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Read the next message received from this client.
	 * @return The request.
	 */
	private Request receive() {
		try {
			final Request req = (Request) in.readObject();
			LOGGER.info("Server : receciving from " + username + " " + req);
			return req;
		} catch (IOException | ClassNotFoundException ex) {
			return null;
		}
	}

	/**
	 * Get the username chose by the client.
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username chose by the client.
	 * @param username The username of the client.
	 */
	public void setUsername(final String username) {
		this.username = username;
		setName(getName() + "[" + username + "]");
	}

	/**
	 * Set the game played by this client.
	 * @param game The game.
	 */
	public void play(final GameThread game) {
		this.game = game;
	}
}
