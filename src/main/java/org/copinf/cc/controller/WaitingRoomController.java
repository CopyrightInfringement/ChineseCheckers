package org.copinf.cc.controller;

import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Message;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.waitingroompanel.WaitingRoomPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

/**
 * The waiting room where the players wait for enough players to have joined the
 * game.
 */
public class WaitingRoomController extends AbstractController implements ActionListener, KeyListener {

	private static final int MAX_MESSAGE_LENGTH = 50;

	private final WaitingRoomPanel roomPanel;
	private List<String> playerList;
	private List<List<String>> teamList;
	private final GameInfo gameInfo;
	private final String username;
	private boolean started;

	/**
	 * Constructs a new WaitingRoomController.
	 * @param mainController The main controller
	 * @param gameInfo The game info of the current game
	 * @param username The name of the user
	 */
	public WaitingRoomController(final MainController mainController, final GameInfo gameInfo, final String username) {
		super(mainController, "game");
		this.roomPanel = new WaitingRoomPanel();
		this.playerList = null;
		this.teamList = null;
		this.gameInfo = gameInfo;
		this.username = username;
		this.started = false;

		roomPanel.getChatPanel().getMessageField().addKeyListener(this);
		roomPanel.getTeamBuildingPanel().getConfirmButton().addActionListener(this);
	}

	@Override
	public void start() {
		super.start();
		if (started) {
			end();
		}
		started = true;
	}

	@Override
	public JPanel getContentPane() {
		return roomPanel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processRequest(final Request request) {
		final String sub2 = request.getSubRequest(2);
		if ("players".equals(sub2)) {
			final String sub3 = request.getSubRequest(3);
			if ("refresh".equals(sub3)) {
				playerList = (List<String>) request.getContent();
				playerList.remove(username);
				roomPanel.getTeamBuildingPanel().setAvailablePlayers(playerList);
			}
		} else if ("teams".equals(sub2)) {
			final String sub3 = request.getSubRequest(3);
			if ("refresh".equals(sub3)) {
				teamList = (List<List<String>>) request.getContent();
				roomPanel.getTeamBuildingPanel().setAvailablePlayers(getAvailablePlayers());
				for (final List<String> team : teamList) {
					if (team.contains(username)) {
						roomPanel.getTeamBuildingPanel()
								.hasBeenPaired(team.get(0).equals(username) ? team.get(1) : team.get(0));
					}
				}
			} else if ("leader".equals(sub3)) {
				roomPanel.getTeamBuildingPanel().enableTeamBuiding(true);
			}
		} else if ("start".equals(sub2)) {
			final List<List<String>> teamList = (List<List<String>>) request.getContent();
			switchController(new GameController(mainController, gameInfo, username, teamList));
		} else if ("message".equals(sub2)) {
			roomPanel.getChatPanel().addMessage((Message) request.getContent());
		}
	}

	/**
	 * Returns a collection of the available players, if teams are enabled, or
	 * the players waiting for the game to begin.
	 * @return The players.
	 */
	private Collection<String> getAvailablePlayers() {
		final Collection<String> coll = new ArrayList<String>();
		coll.addAll(playerList);
		for (final List<String> team : teamList) {
			coll.removeAll(team);
		}
		return coll;
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		if (ev.getSource() == roomPanel.getTeamBuildingPanel().getConfirmButton()) {
			final List<String> team = new ArrayList<>();
			team.add(username);
			team.add(roomPanel.getTeamBuildingPanel().getTeamMate());
			sendRequest(new Request("client.game.teams.leader", (Serializable) team));
			roomPanel.getTeamBuildingPanel().enableTeamBuiding(false);
		}
	}

	private void sendMessageAction() {
		final String text = roomPanel.getChatPanel().getMessage().trim();
		boolean valid = true;
		if (text.equals("")) {
			valid = false;
		}
		if (text.length() > MAX_MESSAGE_LENGTH) {
			roomPanel.getChatPanel().addMessage(new Message("Maximum message length is " + MAX_MESSAGE_LENGTH, ""));
			valid = false;
		}
		if (valid) {
			final Message message = new Message(text, username);
			sendRequest(new Request("client.game.message", message));
		}
		roomPanel.getChatPanel().clearField();
	}

	@Override
	public void keyTyped(final KeyEvent ev) {
		if (ev.getSource() == roomPanel.getChatPanel().getMessageField() && ev.getKeyChar() == '\n') {
			sendMessageAction();
		}
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(final KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
