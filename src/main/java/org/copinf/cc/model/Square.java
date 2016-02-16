package org.copinf.cc.model;

public class Square {

	private Pawn pawn;

	public Square() {
		this(null);
	}

	public Square(final Pawn pawn) {
		this.pawn = pawn;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(final Pawn pawn) {
		this.pawn = pawn;
	}

	public boolean isFree() {
		return pawn == null;
	}
}
