package org.copinf.cc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.copinf.cc.controller.GameController;
import org.copinf.cc.controller.MainController;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;

public class ClientTest {
	static ObjectOutputStream out;
	static ObjectInputStream in;
	public static void main(String[] args) throws Exception{
		Game game = new Game(new DefaultBoard(5));
		Team team1 = new Team();
		team1.addPlayer(new Player("François"));
		Team team2 = new Team();
		team2.addPlayer(new Player("Mathieu"));
		Team team3 = new Team();
		Player player = new Player("Philipe");
		team3.addPlayer(player);
		game.addTeam(team1);
		game.addTeam(team2);
		game.addTeam(team3);
		game.nextTurn();
		MainController mainController = new MainController();
		mainController.startClient("localhost", 25565);
		
		GameController gameController = new GameController(mainController, game, player);
		
		mainController.debug_push(gameController);
	}

}
