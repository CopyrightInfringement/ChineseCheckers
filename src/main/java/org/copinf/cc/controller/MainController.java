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

	/**
	 * Start this controller
	 */
	public void start() {
		final HomeController controller = new HomeController(this);
		controllers.push(controller);
		setContentPane(controller.getContentPane());
		controller.start();
	}
	
	/**
	 * Put a controller on top of the other controllers.
	 * NOTE: By using a stack of controller we can navigate through them
	 * in a way similar to "previous" and "next" buttons on a web browser.
	 * @param controller The controller to put on top of the other controllers.
	 */
	public void push(final AbstractController controller) {
		controllers.peek().end();
		controllers.push(controller);
		setController(controller);
	}
	
	/**
	 * Sets a controller as the active controller and sets its corresponding JPanel
	 * as the content pane of the window.
	 * @param controller The controller to set.
	 */
	private void setController(final AbstractController controller){
		setContentPane(controller.getContentPane());
		if (client != null) {
			client.setController(controller);
		}
		controller.start();
	}
	
	/**
	 * Go back to the previous controller.
	 * @param controller The controller expected to be the active one.
	 */
	public void pop(final AbstractController controller){
		if(controllers.peek() !=  controller)
			throw new RuntimeException("The top controller isn't the one that asked to end");
		controllers.pop();
		if(controllers.isEmpty())
			System.exit(0);
		else
			setController(controllers.peek());
	}

	/**
	 * Sets the content pane of the window.
	 * @param panel The panel to set as the content pane
	 */
	private void setContentPane(final JPanel panel) {
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	/**
	 * Starts the server on a port.
	 * @param port The port on which to start the server.
	 */
	public void startServer(final int port) {
		server = new Server(port);
		new Thread(server).start();
	}

	/**
	 * Start a client after connecting it to a server.
	 * @param host The IP address of the server.
	 * @param port The port to connect on.
	 */
	public void startClient(final String host, final int port) {
		client = new Client(host, port);
		client.start();
	}

	/**
	 * Returns the server.
	 * @return the server.
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Returns the client.
	 * @return the client.
	 */
	public Client getClient() {
		return client;
	}
}
