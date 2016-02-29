package org.copinf.cc;

import org.copinf.cc.controller.AbstractController;
import org.copinf.cc.controller.GameController;
import org.copinf.cc.controller.HomeController;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;
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
	 * @param args arguments
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Controller homeController = new HomeController();
				// homeController.start();

				DefaultBoard board = new DefaultBoard();
				Game game = new Game(board);
				Player player1 = new Player("Player 1");
				Player player2 = new Player("Player 2");
				Team team1 = new Team();
				Team team2 = new Team();
				team1.addPlayer(player1);
				team2.addPlayer(player2);
				game.addTeam(team1);
				game.addTeam(team2);
				game.getBoard().dispatchZones(game.getTeams(), 1);

				Window window = new Window();
				AbstractController gameController = new GameController(game, player1, window);
				gameController.start();
				window.setVisible(true);
			}
		});
	}
}
