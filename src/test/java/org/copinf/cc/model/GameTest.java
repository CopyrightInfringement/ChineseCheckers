package org.copinf.cc.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assume;
import org.junit.Test;
import org.junit.Ignore;

public class GameTest {

	private Game makeGame() {
		Game game = null;
		try {
			game = new Game(new DefaultBoard(4));
		} catch (Exception e) {
			Assume.assumeNoException(e);
		}
		return game;
	}

	@Test
	public void gameConstructionTest() {
		AbstractBoard board = null;
		try {
			board = new DefaultBoard(4);
		} catch (Exception e) {
			Assume.assumeNoException(e);
		}
		try {
			new Game(board);
		} catch (Exception e) {
			fail("Failed construction of the board.");
		}
	}

	@Test
	public void gameAddTeamsTest() {
		Team team1 = new Team();
		Team team2 = new Team();
		team1.addPlayer(new Player("Pierre"));
		team1.addPlayer(new Player("Clara"));
		team2.addPlayer(new Player("Antonin"));
		team2.addPlayer(new Player("Louis"));

		// probably not the best design ever
		Game game = makeGame();
		assertTrue(game.addTeam(team1) && game.addTeam(team2));
	}

	@Ignore
	@Test
	public void gameFirstTurnTest() {
		Game game = makeGame();
		Team team = new Team();
		team.addPlayer(new Player("Pierre"));
		team.addPlayer(new Player("Clara"));

		try {
			game.addTeam(team);
			game.nextTurn();
			fail("Calling nextTurn on a game with only " + game.getTeams().size()
				+ " team(s) should throw an exception.");
		} catch (Exception expected) {}
	}
}
