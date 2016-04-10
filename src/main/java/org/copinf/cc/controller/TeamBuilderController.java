package org.copinf.cc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import org.copinf.cc.model.Player;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.gamepanel.TeamBuilderPanel;

public class TeamBuilderController extends AbstractController implements ActionListener{

	private MainController mainController;
	private TeamBuilderPanel tbPanel;
	private List<String> playerList;
	private List<List<String>> teamList;
	private GameInfo gameInfo;
	private Player mainPlayer;
	
	public TeamBuilderController(MainController mainController, GameInfo gameInfo, Player mainPlayer) {
		super(mainController, "game");
		tbPanel = new TeamBuilderPanel(this);
		this.gameInfo = gameInfo;
		this.mainController = mainController;
		this.mainPlayer = mainPlayer;
	}

	@Override
	public JPanel getContentPane() {
		return tbPanel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(Request request) {
		String sub = request.getSubRequest(2);
		if("players".equals(sub)){
			String subSub = request.getSubRequest(3);
			if("refresh".equals(subSub)){
				playerList = (List<String>) request.getContent();
				playerList.remove(mainPlayer.getName());
				tbPanel.setAvailablePlayers(playerList);
			}
		}else if("teams".equals(sub)){
			String subSub = request.getSubRequest(3);
			if("refresh".equals(subSub)){
				teamList = (List<List<String>>) request.getContent();
				tbPanel.setAvailablePlayers(getAvailablePlayers());
			}else if("leader".equals(subSub)){
				tbPanel.enableTeamBuiding(true);
			}
		}else if("begin".equals(sub)){
			switchController(new GameController(mainController, gameInfo, mainPlayer));
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
		if(e.getSource() == tbPanel.confirmButton){
			List<String> team = new ArrayList<>();
			team.add(mainPlayer.getName());
			team.add(tbPanel.getTeamMate());
			sendRequest(new Request("client.game.teams.leader", (Serializable) team));
		}
	}
}
