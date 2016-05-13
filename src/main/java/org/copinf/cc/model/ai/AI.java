package org.copinf.cc.model.ai;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.BoardMap;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class AI {

	public static final int DEPTH = 3;

	public static Movement nextMove(final AbstractBoard defaultBoard, final Player player,
			final int depth) {
		final List<Movement> moves = nextMoves(defaultBoard, player, depth);
		return moves.get(new Random().nextInt(moves.size()));
	}

	public static List<Movement> nextMoves(final AbstractBoard defaultBoard, final Player player,
			final int depth) {
		final BoardMap board = new BoardMap(defaultBoard.getBoardMap());
		final Node root = buildMovementTree(board, player, depth);

		final List<Movement> nextMovesList = new ArrayList<>();
		int rank = Integer.MIN_VALUE;
		for (final Node child : root.getChildren()) {
			if (child.getRank() > rank) {
				nextMovesList.clear();
				nextMovesList.add(child.getMovement());
				rank = child.getRank();
			} else if (child.getRank() == rank) {
				nextMovesList.add(child.getMovement());
			}
		}
		return nextMovesList;
	}

	private static Node buildMovementTree(final BoardMap board, final Player player, final int depth) {
		final Node root = new Node(board);
		final Queue<Node> vertQueue = new ArrayDeque<>();
		vertQueue.add(root);

		while (!vertQueue.isEmpty()) {
			final Node current = vertQueue.remove();
			if (current.getDistance() >= 3) continue;
			current.genPlayerMovements(player);
			for (final Node node : current.getChildren()) {
				if (node.getDistance() == -1) {
					node.setDistance(current.getDistance() + 1);
					node.setParent(current);
					current.setRank(node.getRank());
					vertQueue.add(node);
				}
			}
		}

		return root;
	}
}
