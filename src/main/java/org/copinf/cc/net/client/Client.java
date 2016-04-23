package org.copinf.cc.net.client;

import org.copinf.cc.controller.AbstractController;
import org.copinf.cc.net.Request;
import org.copinf.cc.net.server.ClientThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Client extends Thread {
	
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private Socket client;
	
	private AbstractController controller;

	private static final Logger LOGGER = Logger.getLogger(ClientThread.class.getName());

	/**
	 * Constructs a new Client. Call run() to connect it to a server.
	 * @param host the host IP address / domain name
	 * @param port the host port
	 */
	public Client(final String host, final int port) {
		super();
		setName("Client thread");

		try {
			client = new Socket(host, port);
			in  = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		controller = null;
	}

	@Override
	public void run() {
		Request req;
		while ((req = receive()) != null) {
			if (req.getSubRequest(1).equals(controller.identifier)) {
				controller.processRequest(req);
			}
		}
		JOptionPane.showMessageDialog(null,
			"The server closed unexpectedly", "Server error",
			JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

	/**
	 * Send a request to the server.
	 * @param req The request to send.
	 */
	public void send(final Request req) {
		try {
			out.reset();
			LOGGER.info("Client : sending to server " + req);
			out.writeObject(req);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Read the next request received from the server.
	 * @return the next request, or null if an error occurred or the connection ended.
	 */
	private Request receive() {
		try {
			final Request req = (Request) in.readObject();
			LOGGER.info("Client : receiving from server " + req);
			return req;
		} catch (IOException | ClassNotFoundException ex) {
			System.out.print(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	/**
	 * Set a controller as the active controller.
	 * @param controller the controller.
	 */
	public void setController(final AbstractController controller) {
		this.controller = controller;
	}
}
