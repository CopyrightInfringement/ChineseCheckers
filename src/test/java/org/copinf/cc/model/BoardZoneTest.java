package org.copinf.cc.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assume;
import org.junit.Test;

public class BoardZoneTest {

	private BoardZone makeZone() {
		BoardZone zone = new BoardZone();
		for (int i = 0; i < 200; i++) {
			zone.addSquare(new Coordinates(i, 2 * i, -i), new Square());
		}
		return zone;
	}

	@Test
	public void setOpponentZoneTest() {
		BoardZone zone1 = makeZone();
		BoardZone zone2 = makeZone();
		try {
			zone1.setOpponentZone(zone2);
		} catch (Exception e) {
			fail("Setting the opponent zone has failed.");
		}
	}

	@Test
	public void isFullTest() {
		int n = 5;
		BoardZone zone = null;
		Player player = null;
		Pawn[] pawns = new Pawn[n];
		Square[] squares = new Square[n];

		try {
			player = new Player("Jean-François");
			zone = new BoardZone();
			for (int i = 0; i < n; i++) {
				pawns[i] = new Pawn(player);
				squares[i] = new Square();
				squares[i].setPawn(pawns[i]);
				zone.addSquare(new Coordinates(i, 2 * i, -i), squares[i]);
			}
		} catch (Exception e) {
			Assume.assumeNoException(e);
		}

		assertTrue(zone.isFull(player));
	}
}
