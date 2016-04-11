package org.copinf.cc.net.server;

import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
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

public class GameThread extends Thread {

	private final GameInfo gameInfo;
	private final Game game;
	private final Set<ClientThread> clients;
	private List<List<String>> teams;
	
	public GameThread(final GameInfo gameInfo) {
		super();
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
		} catch (InterruptedException ex) {}
	}

	@SuppressWarnings("unchecked")
	public void processRequest(final ClientThread client, final Request req) {
		final String sub2 = req.getSubRequest(2);
		final String sub3 = req.getSubRequest(3);
		if ("players".equals(sub2)) {
			if ("refresh".equals(sub3)) {
				processPlayersRefresh(client);
			}
		}else if("teams".equals(sub2)){
			if("refresh".equals(sub3)){
				broadcast(new Request("server.game.teams.refresh", (Serializable) teams));
			}else if("leader".equals(sub3)){
				teams.add((List<String>) req.getContent());
				broadcast(new Request("server.game.teams.refresh", (Serializable) teams));
			}
			onPlayersFull();
		}
	}

	public void processPlayersRefresh(final ClientThread client) {
		client.send(new Request("server.game.players.refresh", getPlayersName()));
	}

	private ArrayList<String> getPlayersName() {
		final ArrayList<String> players = new ArrayList<>();
		for (final ClientThread cl : clients) {
			players.add(cl.getUsername());
		}
		return players;
	}

	public void broadcast(final Request req) {
		for (final ClientThread client : clients) {
			client.send(req);
		}
	}

	public void addClient(final ClientThread client) {
		if (clients.add(client)) {
			gameInfo.nbPlayersCurrent++;
			sendPlayersRefresh();
			if(!gameInfo.teams){
				teams.add(Arrays.asList(client.getUsername()));
			}
		}
		if (gameInfo.nbPlayersCurrent == gameInfo.nbPlayersMax) {
			onPlayersFull();
		}
	}
	
	private void sendPlayersRefresh(){
		List<String> playerList = new ArrayList<String>();
		for(ClientThread ct : clients){
			playerList.add(ct.getUsername());
		}
		for(ClientThread ct : clients) {
			ct.send(new Request("server.game.players.refresh", (Serializable) playerList));
		}
	}
	
	private void onPlayersFull() {
		if(gameInfo.teams && 2 * teams.size() != gameInfo.nbPlayersMax) {
			for(ClientThread ct : clients) {
				if(!isInTeam(ct.getUsername())) {
					ct.send(new Request("server.game.teams.leader"));
					break;
				}
			}
		}else{
			broadcast(new Request("server.game.start", (Serializable) teams));
			initTeams();
		}
	}
	
	private void initTeams(){
		for(List<String> teamMates : teams){
			Team team = new Team();
			for(String username : teamMates){
				team.addPlayer(new Player(username));
			}
			game.addTeam(team);
		}
	}
	
	private boolean isInTeam(String name){
		for(List<String> team : teams)
			if(team.contains(name))
				return true;
		return false;
	}
	
	public void removeClient(final ClientThread client) {
		if (clients.remove(client)) {
			gameInfo.nbPlayersCurrent--;
		}
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	@Override
	public int hashCode() {
		return gameInfo.name.hashCode();
	}
}
