package org.copinf.cc;

import org.copinf.cc.controller.AbstractController;
import org.copinf.cc.controller.GameController;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;
import org.copinf.cc.view.Window;
import org.copinf.cc.controller.MainController;

import java.util.logging.Level;
import java.util.logging.Logger;

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

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainController().start();
			}
		});
	}
}
