package org.copinf.cc;

import org.copinf.cc.controller.MainController;

import javax.swing.SwingUtilities;

/**
 * Main class.
 */
public final class Main {

	private Main() {
	}

	/**
	 * Program entry point.
	 * @param args arguments
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainController().start();
			}
		});
	}
}
