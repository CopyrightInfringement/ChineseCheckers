package org.copinf.cc.controller;

import org.copinf.cc.net.Request;
import org.copinf.cc.view.homepanel.HomePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * Controls the home panel.
 */
public class HomeController extends AbstractController implements ActionListener {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

	private final HomePanel panel;

	/**
	 * Constructs a new HomeController.
	 * @param mainController the main controller
	 */
	public HomeController(final MainController mainController) {
		super(mainController, "home");
		panel = new HomePanel();
		panel.getHostButton().addActionListener(this);
		panel.getJoinButton().addActionListener(this);
	}

	@Override
	public JPanel start() {
		return panel;
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		LOGGER.entering("HomeController", "actionPerformed");
		if (ev.getSource().equals(panel.getHostButton())) {
			LOGGER.info("Host button clicked");
			mainController.startServer(panel.getPort());
			mainController.startClient(panel.getHost(), panel.getPort());
			switchController(new LobbyController(mainController));
		} else if (ev.getSource().equals(panel.getJoinButton())) {
			LOGGER.info("Join button clicked");
			panel.resetErrorMessage();
			mainController.startClient(panel.getHost(), panel.getPort());
			switchController(new LobbyController(mainController));
		}
		LOGGER.exiting("HomeController", "actionPerformed");
	}

	@Override
	public void processRequest(final Request request) {}
}
