package org.copinf.cc.model;

import java.util.HashSet;
import java.util.Set;

public class PathFinding {
	
	private AbstractBoard board;
	private Player player;
	private Set<Coordinates> shortReachableSquares;
	private Set<Coordinates> longReachableSquares;
	
	public PathFinding(DefaultBoard b, Player p){
		this.board = b;
		this.player = p;
		this.shortReachableSquares = new HashSet<>();
		this.longReachableSquares = new HashSet<>();
	}
	
	public Set<Coordinates> getShortReachableSquares() {
		return shortReachableSquares;
	}
	
	public Set<Coordinates> getLongReachableSquares() {
		return longReachableSquares;
	}
	
	private void addShortReachableSquares(Coordinates c) {
		if (board.getSquare(c) != null) {
			Set<Coordinates> adjacentSquares = c.getAdjacentSquares();
			for (Coordinates d : adjacentSquares) {
				if (board.getSquare(d) != null && board.checkMove(new Movement(c, d), player)) {
					shortReachableSquares.add(d);
				}
			}
		}
	}
	
	private void addLongReachableSquare(Coordinates c, Set<Coordinates> seenSquares) {
		Set<Coordinates> adjacentSquares = c.getAdjacentSquares();
		seenSquares.add(c);
		for (Coordinates d : adjacentSquares) {
			if (board.getSquare(d) != null && !board.getSquare(d).isFree()) {
				//Coordinates e = d.sub(c.mul(2));
				for (Coordinates e : d.getAdjacentSquares()) {
					if (board.getSquare(e) != null && !seenSquares.contains(e) && board.checkMove(new Movement(c, e), player)) {
						longReachableSquares.add(e);
						addLongReachableSquare(e, seenSquares);
					}
				}
			}
		}
	}
	
	public void setReachableSquares(Movement m) {
		shortReachableSquares.clear();
		longReachableSquares.clear();
		Set<Coordinates> seenSquares = new HashSet<Coordinates> ();
		if (m.size() == 1) {
			addShortReachableSquares(m.getOrigin());
			seenSquares.add(m.getOrigin());
			addLongReachableSquare(m.getOrigin(), seenSquares);
		} else if (m.size() == 2) {
			if (!m.getOrigin().isAdjacentTo(m.getDestination())) {
				seenSquares.addAll(m);
				addLongReachableSquare(m.getOrigin(), seenSquares);
			}
		}
	}
}
