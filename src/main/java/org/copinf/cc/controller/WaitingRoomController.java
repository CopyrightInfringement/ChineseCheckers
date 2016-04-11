package org.copinf.cc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.WaitingRoomPanel;

public class WaitingRoomController extends AbstractController implements ActionListener{

	private MainController mainController;
	private WaitingRoomPanel roomPanel;
	private List<String> playerList;
	private List<List<String>> teamList;
	private GameInfo gameInfo;
	private String username;
	
	public WaitingRoomController(MainController mainController, GameInfo gameInfo, String username) {
		super(mainController, "game");
		roomPanel = new WaitingRoomPanel(this, gameInfo);
		this.gameInfo = gameInfo;
		this.mainController = mainController;
		this.username = username;
	}

	@Override
	public JPanel getContentPane() {
		return roomPanel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(Request request) {
		String sub = request.getSubRequest(2);
		if("players".equals(sub)){
			String subSub = request.getSubRequest(3);
			if("refresh".equals(subSub)){
				playerList = (List<String>) request.getContent();
				playerList.remove(username);
				roomPanel.setAvailablePlayers(playerList);
			}
		}else if("teams".equals(sub)){
			String subSub = request.getSubRequest(3);
			if("refresh".equals(subSub)){
				teamList = (List<List<String>>) request.getContent();
				roomPanel.setAvailablePlayers(getAvailablePlayers());
				for(List<String> team : teamList){
					if(team.contains(username)){
						roomPanel.hasBeenPaired(team.get(0).equals(username) ? team.get(1) : team.get(0));
					}
				}
			}else if("leader".equals(subSub)){
				roomPanel.enableTeamBuiding(true);
			}
		}else if("start".equals(sub)){
			List<List<String>> teamList = (List<List<String>>) request.getContent();
			switchController(new GameController(mainController, gameInfo, username, teamList));
		}
	}
	
	private Collection<String> getAvailablePlayers() {
		Collection<String> c = new ArrayList<String>();
		c.addAll(playerList);
		
		for(List<String> team : teamList){
			c.removeAll(team);
		}
		
		return c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == roomPanel.confirmButton){
			List<String> team = new ArrayList<>();
			team.add(username);
			team.add(roomPanel.getTeamMate());
			sendRequest(new Request("client.game.teams.leader", (Serializable) team));
			roomPanel.enableTeamBuiding(false);
		}
	}
}
