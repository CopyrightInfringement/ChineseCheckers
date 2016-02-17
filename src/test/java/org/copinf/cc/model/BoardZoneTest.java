package org.copinf.cc.model;

import static org.junit.Assert.assertTrue;

import org.junit.Assume;
import org.junit.Test;

public class BoardZoneTest {
	@Test
	public void addSquaresTest (){
		BoardZone zone = new BoardZone ();
		for (int i = 0; i < 200; i++)
			assertTrue (zone.addSquare(new Square ()));
	}
	
	private BoardZone makeZone (){
		BoardZone zone = new BoardZone ();
		for (int i = 0; i < 200; i++)
			zone.addSquare(new Square ());
		return zone;
	}
	
	@Test
	public void setOpponentZoneTest (){
		BoardZone zone1 = makeZone (), zone2 = makeZone ();
		try{
			zone1.setOpponentZone(zone2);
		}catch (Exception e){
			org.junit.Assert.fail("Setting the opponent zone has failed.");
		}
	}
	
	@Test
	public void isFullTest (){
		int n = 5;
		BoardZone zone = null;
		Player player = null;
		Pawn[] pawns = new Pawn[n];
		Square[] squares = new Square [n];
		
		try{
			player = new Player ("Jean-François");
			zone = new BoardZone ();
			for (int i = 0; i < n; i++){
				pawns[i] = new Pawn (player);
				squares[i] = new Square ();
				squares[i].setPawn(pawns[i]);
				zone.addSquare(squares[i]);
			}
		}catch (Exception e){
			Assume.assumeNoException(e);
		}
		
		assertTrue (zone.isFull(player));
	}
}
