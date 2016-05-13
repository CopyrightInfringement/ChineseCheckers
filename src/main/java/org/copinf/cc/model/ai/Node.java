package org.copinf.cc.model.ai;

import org.copinf.cc.model.BoardMap;
import org.copinf.cc.model.BoardZone;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.PathFinding;
import org.copinf.cc.model.Pawn;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node {

	private static class RankedMovement {
		public final int rank;
		public final Set<Coordinates> ends;

		public RankedMovement(final int rank) {
			this(rank, new HashSet<>());
		}

		public RankedMovement(final int rank, final Set<Coordinates> ends) {
			this.rank = rank;
			this.ends = ends;
		}
	}

	private final BoardMap board;
	private final List<Node> children;
	private Node parent;
	private Movement movement;
	private int distance;
	private int nodeRank;

	public Node(final BoardMap board) {
		this(board, null, -1);
	}

	public Node(final BoardMap board, final Movement movement, final int nodeRank) {
		this.board = board;
		this.children = new ArrayList<>();
		this.parent = null;
		this.movement = movement;
		this.distance = -1;
		this.nodeRank = nodeRank;
	}

	public void genPlayerMovements(final Player player) {
		final Set<Coordinates> pawns = board.getPlayersPawns(player);
		final Set<Coordinates> goals = findGoals(player);
		final PathFinding pf = new PathFinding(board);
		final Map<Coordinates, Set<Coordinates>> allMoves = new HashMap<>();

		for (final Coordinates coord : pawns) {
			final Set<Coordinates> moves = new HashSet<>();
			pf.setReachableSquares(new Movement(coord));
			moves.addAll(pf.getShortReachableSquares());
			moves.addAll(pf.getLongReachableSquares());
			allMoves.put(coord, moves);
		}

		int rank = Integer.MIN_VALUE;
		final Map<Coordinates, Set<Coordinates>> bestMoves = new HashMap<>();
		for (final Coordinates coord : pawns) {
			final RankedMovement bestEnds = findBestEnds(allMoves.get(coord), goals);
			if (bestEnds.rank > rank) {
				bestMoves.clear();
				bestMoves.put(coord, bestEnds.ends);
				rank = bestEnds.rank;
			} else if (bestEnds.rank == rank) {
				bestMoves.put(coord, bestEnds.ends);
			}
		}

		for (final Map.Entry<Coordinates, Set<Coordinates>> entry : bestMoves.entrySet()) {
			for (final Coordinates end : entry.getValue()) {
				final BoardMap map = new BoardMap(board);
				final Pawn pawn = map.get(entry.getKey()).getPawn();
				map.put(entry.getKey(), new Square());
				map.put(end, new Square(pawn));
				children.add(new Node(map, new Movement(entry.getKey(), end), rank));
			}
		}
	}

	private Set<Coordinates> findGoals(final Player player) {
		final Set<Coordinates> set = new HashSet<>();
		for (final BoardZone zone : player.getInitialZones()) {
			if (!zone.getOpponentZone().isFull(player)) {
				set.add(zone.getOpponentZone().getGoal());
			}
		}
		return set;
	}

	private int rankEnd(final Coordinates end, final Set<Coordinates> goals) {
		int rank = Integer.MAX_VALUE;
		Set<Coordinates> bestGoals = new HashSet<>();
		for (final Coordinates goal : goals) {
			final int currentRank = Coordinates.distance(end, goal);
			if (currentRank < rank) {
				bestGoals.clear();
				bestGoals.add(goal);
				rank = currentRank;
			} else if (currentRank == rank) {
				bestGoals.add(goal);
			}
		}
		return rank;
	}

	private RankedMovement findBestEnds(final Set<Coordinates> ends,
			final Set<Coordinates> goals) {
		int rank = Integer.MIN_VALUE;
		Set<Coordinates> bestEnds = new HashSet<>();
		for (final Coordinates end : ends) {
			final int currentRank = rankEnd(end, goals);
			if (currentRank > rank) {
				bestEnds.clear();
				bestEnds.add(end);
				rank = currentRank;
			} else if (currentRank == rank) {
				bestEnds.add(end);
			}
		}
		return new RankedMovement(rank, bestEnds);
	}

	public BoardMap getBoardMap() {
		return board;
	}

	public List<Node> getChildren() {
		return children;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(final Node parent) {
		this.parent = parent;
	}

	public Movement getMovement() {
		return movement;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(final int distance) {
		this.distance = distance;
	}

	public int getRank() {
		return nodeRank;
	}

	public void setRank(final int rank) {
		if (rank > nodeRank) {
			nodeRank = rank;
			if (parent != null) {
				parent.setRank(rank);
			}
		}
	}
}
