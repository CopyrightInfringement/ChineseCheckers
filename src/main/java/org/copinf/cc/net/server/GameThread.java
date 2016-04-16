package org.copinf.cc.net.server;

import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The thread handling a game.
 */
public class GameThread extends Thread {

	private final GameInfo gameInfo;
	private final Game game;
	private final Set<ClientThread> clients;
	private List<List<String>> teams;
	private Server server;
	
	/**
	 * @param gameInfo The information describing the game.
	 * @param server The server hosting this game.
	 */
	public GameThread(final GameInfo gameInfo, Server server) {
		super("Server GT[" + gameInfo.name + "]");
		this.server = server;
		this.gameInfo = gameInfo;
		this.clients = Collections.synchronizedSet(new HashSet<>());
		teams = new ArrayList<>();
		game = new Game(new DefaultBoard(gameInfo.size));
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException expected) {
		}
	}

	/**
	 * End this game.
	 */
	public void endGame() {
		server.gameSet.remove(this);
	}
	
	/**
	 * Process a request relayed by a ClientThread.
	 * @param client The thread handling the connection with the client.
	 * @param req The request relayed.
	 */
	@SuppressWarnings("unchecked")
	public void processRequest(final ClientThread client, final Request req) {
		final String sub2 = req.getSubRequest(2);
		final String sub3 = req.getSubRequest(3);
		if ("players".equals(sub2)) {
			if ("refresh".equals(sub3)) {
				processPlayersRefresh(client);
			}
		} else if ("teams".equals(sub2)) {
			if ("refresh".equals(sub3)) {
				broadcast(new Request("server.game.teams.refresh", (Serializable) teams));

			} else if ("leader".equals(sub3)) {
				teams.add((List<String>) req.content);
				broadcast(new Request("server.game.teams.refresh", (Serializable) teams));
			}
			onPlayersFull();
		} else if ("move".equals(sub2)) {
			processMoveRequest(client, (Movement) req.content);
		} else if ("message".equals(sub2)) {
			broadcast(req);
		}
	}
	
	/**
	 * Get the player from its username.
	 * @param name The name of the player.
	 * @return The player.
	 */
	private Player getPlayer(String name) {
		for (Player player : game.getPlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Process a move request.
	 * @param ct The thread handling the connection with client who has move his pawn
	 * @param movement The movement submitted.
	 */
	private void processMoveRequest(ClientThread ct, Movement movement) {
		boolean accepted = game.getBoard().checkMove(movement, getPlayer(ct.getUsername()));
		ct.send(new Request("server.game.move.request", (Serializable) accepted));
		if (accepted) {
			game.getBoard().move(movement);
			broadcast(new Request("server.game.move", (Serializable) movement));
			broadcast(new Request("server.game.next"));

			if (game.isGameOver()) {
				Team team = game.getWinner();
				int n = game.getTeams().indexOf(team);
				endGame();
				broadcast(new Request("server.game.end", (Serializable) n));
			}
		}
	}

	/**
	 * Process a refresh request of the list of the players in this game.
	 * @param client The client thread.
	 */
	public void processPlayersRefresh(final ClientThread client) {
		client.send(new Request("server.game.players.refresh", getPlayersName()));
	}

	/**
	 * Get a list of the players in this game.
	 * @return the list of players.
	 */
	private ArrayList<String> getPlayersName() {
		final ArrayList<String> players = new ArrayList<>();
		for (final ClientThread cl : clients) {
			players.add(cl.getUsername());
		}
		return players;
	}

	/**
	 * Send a request to all the players in this game.
	 * @param req The request to send.
	 */
	public void broadcast(final Request req) {
		for (final ClientThread client : clients) {
			client.send(req);
		}
	}

	/**
	 * Add a player to this game.
	 * @param client The client thread handling the connection with the playeer to add.
	 */
	public void addClient(final ClientThread client) {
		if (clients.add(client)) {
			client.play(this);
			gameInfo.currentPlayers.add(client.getUsername());
			sendPlayersRefresh();
			if (!gameInfo.teams) {
				teams.add(Arrays.asList(client.getUsername()));
			}
		}
		if (gameInfo.getCurrentPlayersNumber() == gameInfo.nbPlayersMax) {
			onPlayersFull();
		}
	}

	/**
	 * Send everyone a list of the registered teams.
	 */
	private void sendPlayersRefresh() {
		List<String> playerList = new ArrayList<String>();
		for (ClientThread ct : clients) {
			playerList.add(ct.getUsername());
		}
		for (ClientThread ct : clients) {
			ct.send(new Request("server.game.players.refresh", (Serializable) playerList));
		}
	}
	
	/**
	 * Call when enough players has joined the game.
	 */
	private void onPlayersFull() {
		if (gameInfo.teams && 2 * teams.size() != gameInfo.nbPlayersMax) {
			for (ClientThread ct : clients) {
				if (!isInTeam(ct.getUsername())) {
					ct.send(new Request("server.game.teams.leader"));
					break;
				}
			}
		} else {
			broadcast(new Request("server.game.start", (Serializable) teams));
			initTeams();
			game.setNumberOfZones(gameInfo.nbZones);
			game.nextTurn();
		}
	}
	
	/**
	 * Initialize the team list and create the Players object.
	 */
	private void initTeams() {
		for (List<String> teamMates : teams) {
			Team team = new Team();
			for (String username : teamMates) {
				team.addPlayer(new Player(username));
			}
			game.addTeam(team);
		}
	}
	
	/**
	 * Indicates whether or not a player has been registered in a team.
	 * @param name The player
	 * @return true if this player is in a team.
	 */
	private boolean isInTeam(String name) {
		for (List<String> team : teams) {
			if (team.contains(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove this client from the game.
	 * @param client The client to remove
	 */
	public void removeClient(final ClientThread client) {
		if (clients.remove(client)) {
			//TODO: Replace the player with an AI :
			endGame();
			broadcast(new Request("server.game.end", -1));
		}
	}

	/**
	 * Returns the game information of this game.
	 * @return the GameInfo
	 */
	public GameInfo getGameInfo() {
		return gameInfo;
	}

	@Override
	public int hashCode() {
		return gameInfo.name.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof GameThread && this.hashCode() == obj.hashCode();
	}
}
