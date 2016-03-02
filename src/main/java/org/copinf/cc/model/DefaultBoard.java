package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Provides an implementation of the standard Chinese Checkers game board.
 */
public class DefaultBoard extends AbstractBoard {

	private final Map<Coordinates, Square> board;
	private final List<BoardZone> zones;
	private final int radius;

	/**
	 * Constructs a new DefaultBoard.
	 * @param radius radius of this board
	 */
	public DefaultBoard(final int radius) {
		super();
		this.board = new HashMap<>();
		this.radius = radius;
		this.zones = new ArrayList<>();

		// Center of this board
		int r1;
		int r2;
		for (int q = -radius; q <= radius; q++) {
			r1 = Math.max(-radius, -q - radius);
			r2 = Math.min(radius, -q + radius);
			for (int r = r1; r <= r2; r++) {
				board.put(new Coordinates(q, r, -q - r), new Square());
			}
		}

		final Coordinates[] playerOrigin = {
			new Coordinates(1, -radius - 1, radius),
			new Coordinates(radius + 1, -radius, -1),
			new Coordinates(1, radius, -radius - 1),
			new Coordinates(-radius, radius + 1, -1),
			new Coordinates(-radius * 2, radius, radius),
			new Coordinates(-radius, -radius, radius * 2)
		};

		// Create and populate the BoardZone
		BoardZone zone;
		for (int i = 0; i < playerOrigin.length; i++) {
			zone = new BoardZone();
			zones.add(zone);
			int ox = playerOrigin[i].x;
			int oy = playerOrigin[i].y;
			if (i % 2 == 0) {
				for (int q = 0; q < radius; q++) {
					for (int r = -q; r <= 0; r++) {
						zone.addSquare(new Coordinates(q + ox, r + oy, -q - r - ox - oy), new Square());
					}
				}
			} else {
				for (int q = 0; q < radius; q++) {
					for (int r = 0; r < radius - q; r++) {
						zone.addSquare(new Coordinates(q + ox, r + oy, -q - r - ox - oy), new Square());
					}
				}
			}
		}

		BoardZone opponentZone;
		for (int i = 0; i < zones.size() / 2; i++) {
			zone = zones.get(i);
			opponentZone = zones.get(i + 3);
			zone.setOpponentZone(opponentZone);
			opponentZone.setOpponentZone(zone);
		}
	}

	@Override
	public Square getSquare(final Coordinates coordinates) {
		return board.get(coordinates);
	}

	@Override
	public int getWidth() {
		return radius * 3 + 1;
	}

	@Override
	public int getHeight() {
		return radius * 4 + 1;
	}

	@Override
	public List<BoardZone> getZones() {
		return zones;
	}

	@Override
	public Set<Coordinates> coordinates() {
		return board.keySet();
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
		for (Team team : teams) {
			for (int i = 0; i < team.size(); i++) {
				players.add(team.get(i));
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
		board.putAll(zone.getSquares());
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
