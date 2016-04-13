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

public class GameThread extends Thread {

	private final GameInfo gameInfo;
	private final Game game;
	private final Set<ClientThread> clients;
	private List<List<String>> teams;
	private Server server;
	
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
		} catch (InterruptedException ex) {}
	}

	public void endGame(){
		server.gameSet.remove(this);
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
		}else if("move".equals(sub2)){
			processMoveRequest(client, (Movement) req.getContent());
		} else if ("message".equals(sub2)) {
			broadcast(req);
		}
	}
	
	private Player getPlayer(String name){
		for(Player player : game.getPlayers()){
			if(player.getName().equals(name))
				return player;
		}
		
		return null;
	}
	
	private void processMoveRequest(ClientThread ct, Movement movement){
		boolean accepted = game.getBoard().checkMove(movement, getPlayer(ct.getUsername()));
		ct.send(new Request("server.game.move.request", (Serializable) accepted));
		if(accepted){
			game.getBoard().move(movement);
			broadcast(new Request("server.game.move", (Serializable) movement));
			broadcast(new Request("server.game.next"));
			
			if(game.isGameOver()){
				Team team = game.getWinner();
				int n = game.getTeams().indexOf(team);
				endGame();
				broadcast(new Request("server.game.end", (Serializable) n));
			}
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
			client.play(this);
			gameInfo.currentPlayers.add(client.getUsername());
			sendPlayersRefresh();
			if(!gameInfo.teams){
				teams.add(Arrays.asList(client.getUsername()));
			}
		}
		if (gameInfo.getCurrentPlayersNumber() == gameInfo.nbPlayersMax) {
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
			game.setNumberOfZones(gameInfo.nbZones);
			game.nextTurn();
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
			//TODO: Replace the player with an AI : 
			endGame();
			broadcast(new Request("server.game.end", (Serializable) (-1)));
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
