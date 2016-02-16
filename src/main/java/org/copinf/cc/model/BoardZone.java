package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class BoardZone {

	private final Set<Square> squares;
	private BoardZone opponentZone;

	public BoardZone() {
		squares = new HashSet<>();
	}

	public boolean addSquare(final Square square) {
		return squares.add(square);
	}

	public BoardZone getOpponentZone() {
		return opponentZone;
	}

	public void setOpponentZone(final BoardZone zone) {
		this.opponentZone = zone;
	}

	public boolean isFull(final Player player) {
		throw new UnsupportedOperationException();
	}
}
