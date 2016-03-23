package org.copinf.cc.controller;

import org.copinf.cc.view.Window;

import javax.swing.JPanel;

public class MainController {

	private AbstractController currentController;
	private final Window window;

	public MainController() {
		window = new Window();
	}

	public void start() {
		setController(new HomeController(this));
	}

	public void setController(final AbstractController controller) {
		currentController = controller;
		setContentPane(controller.start());
	}

	private void setContentPane(final JPanel panel) {
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
	}
}
