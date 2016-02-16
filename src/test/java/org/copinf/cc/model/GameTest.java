package org.copinf.cc.model;

import static org.junit.Assert.assertTrue;

import org.junit.Assume;
import org.junit.Test;

public class GameTest {
	private Game makeGame(){
		Game game = null;
		try{
			game = new Game (new DefaultBoard ());
		}catch (Exception e){
			Assume.assumeNoException(e);
		}
		return game;
	}
	
	@Test
	public void gameConstructionTest (){
		AbstractBoard board = null;
		try{
			board = new DefaultBoard ();
		}catch (Exception e){
			Assume.assumeNoException(e);
		}
		try{
			new Game(board);
		}catch (Exception e){
			org.junit.Assert.fail ("Failed construction of the board.");
		}
	}
	
	@Test
	public void gameAddTeamsTest (){
		Game game = makeGame ();
		
		Team team1 = new Team (), team2 = new Team ();
		team1.addPlayer(new Player ("Pierre"));
		team1.addPlayer(new Player ("Clara"));
		team2.addPlayer(new Player ("Antonin"));
		team2.addPlayer(new Player ("Louis"));
		
		assertTrue (game.addTeam(team1));
		assertTrue (game.addTeam(team2));
	}
	
	@Test
	public void gameFirstTurnTest (){
		Game game = makeGame ();
		
		Team[] teams = new Team []{new Team(), new Team()};
		teams[0].addPlayer(new Player ("Pierre"));
		teams[0].addPlayer(new Player ("Clara"));
		teams[1].addPlayer(new Player ("Antonin"));
		teams[1].addPlayer(new Player ("Louis"));
		
		for (Team t : teams){
			try{
				game.nextTurn();
				org.junit.Assert.fail("Calling nextTurn on a game with only " + game.getTeams().size() + " team(s) should throw an exception.");
			}catch (Exception e){}
			game.addTeam(t);
		}
	}
}
