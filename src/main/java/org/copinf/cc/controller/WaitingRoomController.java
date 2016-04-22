package org.copinf.cc.controller;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.waitingroompanel.WaitingRoomPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

/**
 * The waiting room where the players wait for enough players to have joined the game.
 */
public class WaitingRoomController extends AbstractController implements ActionListener {

	private MainController mainController;
	private WaitingRoomPanel roomPanel;
	private List<String> playerList;
	private List<List<String>> teamList;
	private GameInfo gameInfo;
	private String username;
	private boolean started;

	/**
	 * @param mainController The main controller
	 * @param username The username of the user.
	 */
	public WaitingRoomController(MainController mainController, GameInfo gameInfo, String username) {
		super(mainController, "game");
		this.mainController = mainController;
		this.roomPanel = new WaitingRoomPanel(this);
		this.playerList = null;
		this.teamList = null;
		this.gameInfo = gameInfo;
		this.username = username;
		this.started = false;
		
		roomPanel.teamBuildingPanel.confirmButton.addActionListener(this);
	}

	@Override
	public void start() {
		if (started) {
			finish();
		}
		started = true;
	}

	@Override
	public JPanel getContentPane() {
		return roomPanel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(Request request) {
		String sub = request.getSubRequest(2);
		if ("players".equals(sub)) {
			String subSub = request.getSubRequest(3);
			if ("refresh".equals(subSub)) {
				playerList = (List<String>) request.content;
				playerList.remove(username);
				roomPanel.teamBuildingPanel.setAvailablePlayers(playerList);
			}
		} else if ("teams".equals(sub)) {
			String subSub = request.getSubRequest(3);
			if ("refresh".equals(subSub)) {
				teamList = (List<List<String>>) request.content;
				roomPanel.teamBuildingPanel.setAvailablePlayers(getAvailablePlayers());
				for (List<String> team : teamList) {
					if (team.contains(username)) {
						roomPanel.teamBuildingPanel.hasBeenPaired(team.get(0).equals(username) ? team.get(1) : team.get(0));
					}
				}
			} else if ("leader".equals(subSub)) {
				roomPanel.teamBuildingPanel.enableTeamBuiding(true);
			}
		} else if ("start".equals(sub)) {
			List<List<String>> teamList = (List<List<String>>) request.content;
			switchController(new GameController(mainController, gameInfo, username, teamList));
		}
	}

	/**
	 * Returns a collection of the available players, if teams are enabled,
	 * or the players waiting for the game to begin.
	 * @return The players.
	 */
	private Collection<String> getAvailablePlayers() {
		Collection<String> coll = new ArrayList<String>();
		coll.addAll(playerList);
		for (List<String> team : teamList) {
			coll.removeAll(team);
		}
		return coll;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == roomPanel.teamBuildingPanel.confirmButton) {
			List<String> team = new ArrayList<>();
			team.add(username);
			team.add(roomPanel.teamBuildingPanel.getTeamMate());
			sendRequest(new Request("client.game.teams.leader", (Serializable) team));
			roomPanel.teamBuildingPanel.enableTeamBuiding(false);
		}
	}
}
