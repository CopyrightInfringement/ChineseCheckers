package org.copinf.cc.controller;

import org.copinf.cc.net.client.Client;
import org.copinf.cc.net.server.Server;
import org.copinf.cc.view.Window;

import java.util.Stack;

import javax.swing.JPanel;

public class MainController {

	private final Window window;

	private Server server;
	private Client client;

	private final Stack<AbstractController> controllers;

	public MainController() {
		window = new Window();
		controllers = new Stack<>();
	}

	public void start() {
		final HomeController controller = new HomeController(this);
		controllers.push(controller);
		setContentPane(controller.getContentPane());
		controller.start();
	}

	public void push(final AbstractController controller) {
		controllers.peek().end();
		controllers.push(controller);
		setController(controller);
	}
	
	private void setController(final AbstractController controller){
		setContentPane(controller.getContentPane());
		if (client != null) {
			client.setController(controller);
		}
		controller.start();
	}
	
	public void pop(final AbstractController controller){
		if(controllers.peek() !=  controller)
			throw new RuntimeException("The top controller isn't the one that asked to end");
		controllers.pop();
		if(controllers.isEmpty())
			System.exit(0);
		else
			setController(controllers.peek());
	}

	private void setContentPane(final JPanel panel) {
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void startServer(final int port) {
		server = new Server(port);
		new Thread(server).start();
	}

	public void startClient(final String host, final int port) {
		client = new Client(host, port);
		client.start();
	}

	public Server getServer() {
		return server;
	}

	public Client getClient() {
		return client;
	}
}
