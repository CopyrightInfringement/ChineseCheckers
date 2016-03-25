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

	private Stack<AbstractController> controllers;

	public MainController() {
		window = new Window();
		controllers = new Stack<>();
	}

	public void start() {
		HomeController controller = new HomeController(this);
		controllers.push(controller);
		setContentPane(controller.start());
	}

	public void push(final AbstractController controller) {
		controllers.peek().end();
		controllers.push(controller);
		setContentPane(controller.start());
	}

	private void setContentPane(final JPanel panel) {
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
	}

	public void startServer(final int port) {
		server = new Server(port);
		new Thread(server).start();
	}

	public void startClient(final String host, final int port) {
		new Client(host, port).start();
	}

	public Server getServer() {
		return server;
	}

	public Client getClient() {
		return client;
	}
}
