package org.copinf.cc.net.client;

import org.copinf.cc.net.Request;
import org.copinf.cc.controller.AbstractController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

	private final String host;
	private final int port;
	private Socket clientSocket;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private AbstractController controller;

	public Client(final String host, final int port) {
		super();
		this.host = host;
		this.port = port;
		in = null;
		out = null;
	}

	@Override
	public void run() {
		try (
			Socket socket = new Socket(host, port);
			ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
		) {
			clientSocket = socket;
			this.in = in;
			this.out = out;
			Request req;
			while ((req = receive()) != null) {
				System.out.println("Received : " + req);
				if (req.getSubRequest(1).equals(controller.identifier)) {
					controller.processRequest(req);
				}
			}
			System.out.println("Received null");
		} catch (IOException e) {
			System.err.println("Exception while receiving : " + e);
			System.exit(1);
		} catch (Exception e){
			System.out.println("Caught another exception : " + e);
		}
	}

	public void send(final Request req) {
		try {
			out.writeObject(req);
		} catch (IOException e) {
			System.err.println("Exception while sending : " + e);
			System.exit(1);
		}
	}

	private Request receive() {
		try {
			return (Request) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Exception while receiving : " + e);
			System.err.println("Socket : " + clientSocket);
			System.exit(1);
			return null;
		}
	}
}
