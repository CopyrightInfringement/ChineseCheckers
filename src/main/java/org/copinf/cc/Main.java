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
				Player player1 = new Player("François");
				Team team1 = new Team();
				team1.addPlayer(player1);
				game.addTeam(team1);
				Team team2 = new Team();
				team2.addPlayer(new Player("Mathieu"));
				game.addTeam(team2);
				Team team3 = new Team();
				team3.addPlayer(new Player("Paul"));
				game.addTeam(team3);
				game.getBoard().dispatchZones(game.getTeams(), 2);

				Window window = new Window();
				AbstractController gameController = new GameController(game, player1, window);
				gameController.start();
				window.setVisible(true);
			}
		});
	}
}
