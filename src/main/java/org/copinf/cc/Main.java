package org.copinf.cc;

import org.copinf.cc.controller.AbstractController;
import org.copinf.cc.controller.GameController;
import org.copinf.cc.controller.HomeController;
import org.copinf.cc.model.*;
import org.copinf.cc.view.Window;

import javax.swing.SwingUtilities;

/**
 * Main class.
 */
public final class Main {

	private Main() {
	}

	/**
	 * Program entry point.
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Controller homeController = new HomeController();
				// homeController.start();
				DefaultBoard board = new DefaultBoard();
				Game game = new Game(board);
				Window window = new Window();
				Player player = new Player("Name");
				AbstractController gameController = new GameController(game, player, window);
				gameController.start();
				window.setVisible(true);
			}
		});
	}
}
