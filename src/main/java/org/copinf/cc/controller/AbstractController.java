package org.copinf.cc.controller;

import org.copinf.cc.net.Request;

import javax.swing.JPanel;

/**
 * An abstract class for controllers.
 */
public abstract class AbstractController {

	public final String identifier;
	protected final MainController mainController;

	/**
	 * Constructs a new controller.
	 * @param mainController the main controller
	 * @param identifier Controller's identifer to be used when receiving a request
	 */
	public AbstractController(final MainController mainController, final String identifier) {
		this.mainController = mainController;
		this.identifier = identifier;
	}

	/**
	 * Starts this controller. A controller returns a JPanel that will be used for the contentPane of
	 * the main window. Before returning, a controller should link itself to the panels components if
	 * it needs to listen for events.
	 * @return the contentPane
	 */
	public abstract JPanel start();

	public void end() {}

	/**
	 * Switch to another controller.
	 * @param controller a controller
	 */
	public void switchController(final AbstractController controller) {
		mainController.push(controller);
	}

	public abstract void processRequest(final Request request);

	public void sendRequest(final Request request) {
		mainController.getClient().send(request);
	}
}
