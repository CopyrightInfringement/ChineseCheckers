package org.copinf.cc.controller;

import org.copinf.cc.view.Window;
import org.copinf.cc.view.homepanel.HomePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Controls the home panel.
 */
public class HomeController extends AbstractController implements ActionListener {

	private final static Logger LOGGER = Logger.getLogger(HomeController.class.getName());

	private final Window window;
	private final HomePanel panel;

	/**
	 * Constructs a new HomeController.
	 */
	public HomeController() {
		super();
		window = new Window();
		panel = new HomePanel();
		panel.getHostButton().addActionListener(this);
		panel.getJoinButton().addActionListener(this);
	}

	@Override
	public void start() {
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.entering("HomeController", "actionPerformed");
		if (e.getSource().equals(panel.getHostButton())) {
			LOGGER.info("Host button clicked");
		} else if (e.getSource().equals(panel.getJoinButton())) {
			LOGGER.info("Join button clicked");
			panel.resetErrorMessage();
		}
		LOGGER.exiting("HomeController", "actionPerformed");
	}
}
