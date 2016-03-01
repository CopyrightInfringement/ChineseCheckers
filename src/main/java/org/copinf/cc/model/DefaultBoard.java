package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DefaultBoard extends AbstractBoard {

	/*
	private final static int[] BOARD_MAP = {
		-1, -1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, -1, 14, 14, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, 14, 14, 14, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, 14, 14, 14, 14, -1, -1, -1, -1, -1,
		63, 63, 63, 63,  0,  0,  0,  0,  0, 25, 25, 25, 25,
		  63, 63, 63,  0,  0,  0,  0,  0,  0, 25, 25, 25, -1,
		-1, 63, 63,  0,  0,  0,  0,  0,  0,  0, 25, 25, -1,
		  -1, 63,  0,  0,  0,  0,  0,  0,  0,  0, 25, -1, -1,
		-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1,
		  -1, 52,  0,  0,  0,  0,  0,  0,  0,  0, 36, -1, -1,
		-1, 52, 52,  0,  0,  0,  0,  0,  0,  0, 36, 36, -1,
		  52, 52, 52,  0,  0,  0,  0,  0,  0, 36, 36, 36, -1,
		52, 52, 52, 52,  0,  0,  0,  0,  0, 36, 36, 36, 36,
		  -1, -1, -1, -1, 41, 41, 41, 41, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, 41, 41, 41, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, -1, 41, 41, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1
	};
	*/

	private static final int[][] BOARD_MAP = {
		{-1, -1, -1, -1, 3, 3, -1, -1, -1, -1, -1, 2, 2, -1, -1, -1, -1},
		{-1, -1, -1, -1, 3, 3, 3, 3, -1, 2, 2, 2, 2, -1, -1, -1, -1},
		{-1, -1, -1, -1, 3, 3, 3, 0, 0, 0, 2, 2, 2, -1, -1, -1, -1},
		{-1, -1, -1, -1, 3, 0, 0, 0, 0, 0, 0, 0, 2, -1, -1, -1, -1},
		{-1, -1, -1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, -1, -1},
		{-1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, -1},
		{4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
		{-1, -1, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, -1, -1},
		{-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
		{-1, -1, -1, -1, 5, 5, 0, 0, 0, 0, 0, 6, 6, -1, -1, -1, -1},
		{-1, -1, -1, -1, 5, 5, 5, 5, 0, 6, 6, 6, 6, -1, -1, -1, -1},
		{-1, -1, -1, -1, 5, 5, 5, -1, -1, -1, 6, 6, 6, -1, -1, -1, -1},
		{-1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, -1, -1}
	};

	private List<BoardZone> zones;

	/**
	 * Constructs a new DefaultBoard. It's the standard ChineseCheckers star board.
	 */
	public DefaultBoard() {
		super(13, 17);
		Map<Integer, BoardZone> map = new TreeMap<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int v = BOARD_MAP[i][j];
				if (v == 0) {
					board[i][j] = new Square(new Coordinates(i, j));
				} else if (v != -1) {
					if (!map.containsKey(v)) {
						map.put(v, new BoardZone());
					}
					map.get(v).addSquare(new Square(new Coordinates(i, j)));
				}
			}
		}

		this.zones = new ArrayList<>();
		for (Integer i : new TreeSet<Integer>(map.keySet())) {
			zones.add(map.get(i));
			map.get(i).setOpponentZone(map.get((i + 2) % 6 + 1));
		}
	}

	@Override
	public SortedSet<Integer> getPossiblePlayerNumbers() {
		final TreeSet<Integer> ts = new TreeSet<>();
		ts.add(2);
		ts.add(3);
		ts.add(4);
		ts.add(6);
		return ts;
	}

	@Override
	public void dispatchZones(final Set<Team> teams, final int nbOfZones) {
		List<Player> players = new ArrayList<>();
		for (Team t : teams) {
			for (Player p : t.getPlayers()) {
				players.add(p);
			}
		}
		int nbOfPlayers = players.size();
		BoardZone zone;
		Player player;

		if (nbOfPlayers % 2 == 0) {
			Player nextPlayer;
			for (int i = 0; i < nbOfPlayers; i += 2) {
				player = players.get(i);
				nextPlayer = players.get(i + 1);
				for (int j = 0; j < nbOfZones; j++) {
					zone = zones.get(i * nbOfZones + j);
					player.addInitialZone(zone);
					nextPlayer.addInitialZone(zone.getOpponentZone());
					addZone(zone);
					addZone(zone.getOpponentZone());
				}
			}
		} else if (nbOfPlayers == 3) {
			for (int i = 0; i < nbOfPlayers; i++) {
				player = players.get(i);
				for (int j = 0; j < nbOfZones; j++) {
					zone = zones.get(i * nbOfZones + j);
					player.addInitialZone(zone);
					addZone(zone);
					addZone(zone.getOpponentZone());
				}
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Adds the Square of a BoardZone to this board.
	 * @param zone a BoardZone
	 */
	private void addZone(final BoardZone zone) {
		for (Square s : zone) {
			board[s.getCoordinates().x][s.getCoordinates().y] = s;
		}
	}

	@Override
	public SortedSet<Integer> getPossiblesZoneNumber(final int playerNumber) {
		if (!getPossiblePlayerNumbers().contains(playerNumber)) {
			return null;
		}
		int nbMax = 6 / playerNumber;
		SortedSet<Integer> ts = new TreeSet<>();
		for (int i = 1; i <= nbMax; i++) {
			ts.add(i);
		}
		return ts;
	}
}
