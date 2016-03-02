package org.copinf.cc.controller;

import org.copinf.cc.view.Window;
import org.copinf.cc.view.homepanel.HomePanel;

/**
 * Controls the home panel.
 */
public class HomeController extends AbstractController {

	private final Window view;
	private final HomePanel panel;

	/**
	 * Constructs a new HomeController.
	 */
	public HomeController() {
		super();
		view = new Window();
		panel = new HomePanel();
	}

	@Override
	public void start() {
		view.setContentPane(panel);
		view.pack();
		view.setVisible(true);
	}
}
