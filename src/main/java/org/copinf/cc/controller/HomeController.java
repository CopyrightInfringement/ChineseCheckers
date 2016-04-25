package org.copinf.cc.controller;

import org.copinf.cc.net.Request;
import org.copinf.cc.view.homepanel.HomePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Controls the home panel.
 */
public class HomeController extends AbstractController implements ActionListener {

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
	public JPanel getContentPane() {
		return panel;
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		if (ev.getSource().equals(panel.getHostButton())) {
			mainController.startServer(panel.getPort());
			mainController.startClient(panel.getHost(), panel.getPort());
			switchController(new LobbyController(mainController));
		} else if (ev.getSource().equals(panel.getJoinButton())) {
			panel.resetErrorMessage();
			if (mainController.startClient(panel.getHost(), panel.getPort())) {
				switchController(new LobbyController(mainController));
			} else {
				panel.displayErrorMessage("Unable to connect to the server.");
			}
		}
	}

	@Override
	public void processRequest(final Request request) {}
}
