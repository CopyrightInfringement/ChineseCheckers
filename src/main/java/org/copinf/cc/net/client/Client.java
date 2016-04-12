package org.copinf.cc.net.client;

import org.copinf.cc.controller.AbstractController;
import org.copinf.cc.net.Request;
import org.copinf.cc.net.server.ClientThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Client extends Thread {

	private final String host;
	private final int port;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private AbstractController controller;

	private static final Logger LOGGER = Logger.getLogger(ClientThread.class.getName());
	
	public Client(final String host, final int port) {
		super();
		setName("Client thread");
		this.host = host;
		this.port = port;
		in = null;
		out = null;
		controller = null;
	}

	@Override
	public void run() {
		try (
			Socket client = new Socket(host, port);
			ObjectInputStream  in  = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
		) {
			this.in = in;
			this.out = out;
			Request req;
			while ((req = receive()) != null) {
				if (req.getSubRequest(1).equals(controller.identifier)) {
					controller.processRequest(req);
				}
			}
			javax.swing.JOptionPane.showMessageDialog(null, "The server closed unexpectedly", "Server error", javax.swing.JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

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

	private Request receive() {
		try {
			Request req = (Request) in.readObject();
			LOGGER.info("Client : receiving from server " + req);
			
			return req;
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void setController(final AbstractController controller) {
		this.controller = controller;
	}
}
