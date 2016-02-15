package org.copinf.cc.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {
	@Test
	public void gameInitializationTest (){
		Game game = new Game(new DefaultBoard ());
		Team team1 = new Team (), team2 = new Team ();
		team1.addPlayer(new Player ("Pierre"));
		team1.addPlayer(new Player ("Clara"));
		team2.addPlayer(new Player ("Antonin"));
		team2.addPlayer(new Player ("Louis"));
		
		try{
			game.nextTurn();
		}catch (Exception e){
			throw new Error ("The game can begin without any team registered.");
		}
		
		game.addTeam(team1);
		
		try{
			game.nextTurn();
		}catch (Exception e){
			throw new Error ("The game can begin with only one team registered.");
		}
		
		assertEquals (game.addTeam(team1), false);
		
		game.addTeam(team2);
		
		try{
			game.nextTurn();
		}catch (Exception e){
			throw new Error ("The game can begin without any team registered.");
		}
		
		if (game.getTurnCount() != 0)
			throw new Error ("The first turn of the game isn't the 0-th one.");
		if (game.getCurrentPlayer() == null)
			throw new Error ("Their isn't any current player.");
	}
}
