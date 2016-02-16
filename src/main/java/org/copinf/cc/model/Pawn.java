package org.copinf.cc.model;

public class Pawn {

	private final Player owner;

	public Pawn(final Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}
}
