package org.copinf.cc.controller;

import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Player;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.lobbypanel.LobbyPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JPanel;

/**
 * Controls the game creation panel.
 */
public class LobbyController extends AbstractController implements ActionListener {

	private final LobbyPanel lobbyPanel;

	private String username;
	private GameInfo selectedGame;

	/**
	 * Constructs a new LobbyController.
	 * @param mainController the main controller
	 */
	public LobbyController(final MainController mainController) {
		super(mainController, "lobby");
		this.lobbyPanel = new LobbyPanel();

		lobbyPanel.getRefreshGameInfoListBtn().addActionListener(this);
		lobbyPanel.getJoinGameBtn().addActionListener(this);
		lobbyPanel.getUsernamePanel().getSubmitBtn().addActionListener(this);
		lobbyPanel.getGameCreationPanel().addBoard(new DefaultBoard(0));
		lobbyPanel.getGameCreationPanel().getCreateGameBtn().addActionListener(this);
	}

	@Override
	public void start() {
		actionRefreshGameInfoList();
	}

	@Override
	public JPanel getContentPane() {
		return lobbyPanel;
	}

	@Override
	public void processRequest(final Request request) {
		final String sub2 = request.getSubRequest(2);
		if ("refresh".equals(sub2)) {
			processRefreshGameInfoList(request);
		} else if ("username".equals(sub2)) {
			processSubmitUsername(request);
		} else if ("create".equals(sub2)) {
			processCreateGame(request);
		} else if ("join".equals(sub2)) {
			processJoinGame(request);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		final Object source = ev.getSource();
		if (source.equals(lobbyPanel.getRefreshGameInfoListBtn())) {
			actionRefreshGameInfoList();
		} else if (source.equals(lobbyPanel.getJoinGameBtn())) {
			actionJoinGame();
		} else if (source.equals(lobbyPanel.getUsernamePanel().getSubmitBtn())) {
			actionSubmitUsername();
		} else if (source.equals(lobbyPanel.getGameCreationPanel().getCreateGameBtn())) {
			actionCreateGame();
		}
	}

	private void actionRefreshGameInfoList() {
		sendRequest(new Request("client.lobby.refresh"));
	}

	@SuppressWarnings("unchecked")
	private void processRefreshGameInfoList(final Request request) {
		final Set<GameInfo> waitingGames = (Set<GameInfo>) request.getContent();
		lobbyPanel.getGamesList().setListData(waitingGames.toArray(new GameInfo[waitingGames.size()]));
	}

	private void actionSubmitUsername() {
		username = lobbyPanel.getUsernamePanel().getUsername();
		if (username.length() > 15) {
			username = username.substring(0, 15);
		}
		lobbyPanel.getUsernamePanel().getSubmitBtn().setEnabled(false);
		sendRequest(new Request("client.lobby.username", username));
	}

	private void processSubmitUsername(final Request request) {
		if ((Boolean) request.getContent()) {
			lobbyPanel.getUsernamePanel().switchToUsernamePanel(username);
		} else {
			lobbyPanel.getUsernamePanel().setUsername("");
			lobbyPanel.getUsernamePanel().getSubmitBtn().setEnabled(true);
		}
	}

	private void actionCreateGame() {
		final GameInfo gameInfo = lobbyPanel.getGameCreationPanel().makeGameInfo();
		if (gameInfo != null) {
			selectedGame = gameInfo;
			sendRequest(new Request("client.lobby.create", gameInfo));
		}
	}

	private void processCreateGame(final Request request) {
		if (!(Boolean) request.getContent()) {
			selectedGame = null;
			lobbyPanel.getGameCreationPanel().resetGameName();
		}
	}

	public void actionJoinGame() {
		selectedGame = lobbyPanel.getGamesList().getSelectedValue();
		sendRequest(new Request("client.lobby.join", selectedGame));
	}

	private void processJoinGame(final Request request) {
		if ((Boolean) request.getContent()) {
			switchController(new WaitingRoomController(mainController, selectedGame, username));
		} else {
			selectedGame = null;
		}
	}
}
