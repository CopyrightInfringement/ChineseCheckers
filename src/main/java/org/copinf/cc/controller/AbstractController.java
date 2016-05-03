package org.copinf.cc.controller;

import javax.swing.JPanel;

import org.copinf.cc.net.Request;

/**
 * An abstract class for controllers.
 */
public abstract class AbstractController {

	/** An identifier for this controller, used to dispatch network messages. */
	public final String identifier;
	/**
	 * The controller controlling all the ther controller.
	 */
	protected final MainController mainController;

	/**
	 * Constructs a new controller.
	 * 
	 * @param mainController the main controller
	 * @param identifier Controller's identifer to be used when receiving a
	 *            request
	 */
	public AbstractController(final MainController mainController, final String identifier) {
		this.mainController = mainController;
		this.identifier = identifier;
	}

	/**
	 * Starts this controller. A subclass of AbstractController redefining this
	 * method should call the superclass start method at the beginning of the
	 * method.
	 */
	public void start() {
	}

	/**
	 * Ends this controller. A subclass of AbstractController redefining this
	 * method should call the superclass start method at the end of the method.
	 */
	public void end() {
		mainController.pop(this);
	}

	/**
	 * Go back to the home controller.
	 */
	public void home() {
		mainController.home();
	}

	/**
	 * A controller returns a JPanel that will be used for the contentPane of
	 * the main window. Before returning, a controller should link itself to the
	 * panels components if it needs to listen for events.
	 * 
	 * @return the contentPane
	 */
	public abstract JPanel getContentPane();

	/**
	 * Switches to another controller.
	 * 
	 * @param controller a controller
	 */
	public void switchController(final AbstractController controller) {
		mainController.push(controller);
	}

	/**
	 * Reacts to a request.
	 * 
	 * @param request The request to process
	 */
	public abstract void processRequest(final Request request);

	/**
	 * Sends a request to the server.
	 * 
	 * @param request The request to send
	 */
	public void sendRequest(final Request request) {
		mainController.getClient().send(request);
	}
}
