package org.copinf.cc.controller;

import org.copinf.cc.view.Window;

import javax.swing.JPanel;

/**
 * An abstract class for controllers.
 */
public abstract class AbstractController {

	private final MainController mainController;

	/**
	 * Constructs a new controller.
	 * @param mainController the main controller
	 */
	public AbstractController(final MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Starts this controller. A controller returns a JPanel that will be used for the contentPane of
	 * the main window. Before returning, a controller should link itself to the panels components if
	 * it needs to listen for events.
	 * @return the contentPane
	 */
	public abstract JPanel start();

	/**
	 * Switch to another controller.
	 * @param controller a controller
	 */
	public void switchController(final AbstractController controller) {
		mainController.setController(controller);
	}
}
