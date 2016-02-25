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
		{-1, -1, -1, -1, 63, 63, -1, -1, -1, -1, -1, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 63, 63, 63, -1, 52, 52, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 63, 63, 0, 0, 0, 52, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 0, 0, 0, 0, 0, 0, 0, 52, -1, -1, -1, -1},
		{-1, -1, -1, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, -1, -1, -1},
		{-1, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, 41, -1},
		{14, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, 41, 41},
		{-1, -1, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, -1, -1},
		{-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 0, 0, 0, 0, 0, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 25, 25, 0, 36, 36, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 25, -1, -1, -1, 36, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1}
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
				} else {
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
			map.get(i).setOpponentZone(map.get((i + 3) % 6));
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
	public void dispatchZones(final Set<Team> teams, int nbOfZones) {
		List<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayers()) {
				players.add(p);
			}
		}
		int nbOfPlayers = players.size();
		if (nbOfPlayers % 2 == 0) {
			for (int i = 0; i < nbOfPlayers; i += 2) {
				for (int j = 0; j < nbOfZones; j++) {
					BoardZone zone = zones.get(i * nbOfZones + j);
					players.get(i).addInitialZone(zone);
					players.get(i + 1).addInitialZone(zone.getOpponentZone());
				}
			}
		} else {	//	S'il y a 3 joueurs
			for (int i = 0; i < nbOfPlayers; i++) {
				for (int j = 0; j < nbOfZones; j++) {
					players.get(i).addInitialZone(zones.get(i * nbOfZones + j));
				}
			}
		}
	}

	@Override
	public SortedSet<Integer> getPossiblesZoneNumber(int playerNumber) {
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
