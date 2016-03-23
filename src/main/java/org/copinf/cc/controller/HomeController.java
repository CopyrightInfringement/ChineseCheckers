package org.copinf.cc.controller;

import org.copinf.cc.view.homepanel.HomePanel;
import org.copinf.cc.net.client.Client;
import org.copinf.cc.net.server.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * Controls the home panel.
 */
public class HomeController extends AbstractController implements ActionListener {

	private final static Logger LOGGER = Logger.getLogger(HomeController.class.getName());

	private final HomePanel panel;

	/**
	 * Constructs a new HomeController.
	 */
	public HomeController(final MainController mainController) {
		super(mainController);
		panel = new HomePanel();
		panel.getHostButton().addActionListener(this);
		panel.getJoinButton().addActionListener(this);
	}

	@Override
	public JPanel start() {
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.entering("HomeController", "actionPerformed");
		if (e.getSource().equals(panel.getHostButton())) {
			LOGGER.info("Host button clicked");
			new Thread(new Server(panel.getPort())).start();
			new Thread(new Client(panel.getHost(), panel.getPort())).start();
		} else if (e.getSource().equals(panel.getJoinButton())) {
			LOGGER.info("Join button clicked");
			panel.resetErrorMessage();
			new Thread(new Client(panel.getHost(), panel.getPort())).start();
		}
		LOGGER.exiting("HomeController", "actionPerformed");
	}
}
