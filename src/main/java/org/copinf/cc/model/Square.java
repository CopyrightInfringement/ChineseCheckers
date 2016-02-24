package org.copinf.cc.model;

public class Square {

	private Pawn pawn;
	private Coordinates coordinates;

	public Square(final Coordinates coordinates) {
		this(null, coordinates);
	}

	public Square(final Pawn pawn, final Coordinates coordiates) {
		this.pawn = pawn;
		this.coordinates = coordinates;
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
	
	public Coordinates getCoordinates (){
		return this.coordinates;
	}
}
