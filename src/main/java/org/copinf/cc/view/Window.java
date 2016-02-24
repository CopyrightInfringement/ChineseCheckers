package org.copinf.cc.view;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	/**
	 * Constructs a new window that is initially invisible.
	 */
	public Window() {
		super("Chinese Checkers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setFocusable(true);
	}
}
