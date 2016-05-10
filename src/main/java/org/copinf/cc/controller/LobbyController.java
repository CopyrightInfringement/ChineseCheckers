package org.copinf.cc.controller;

import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.lobbypanel.LobbyPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;
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
		super.start();
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

	/**
	 * Asks the server for an updated list of the games it currently hosts.
	 */
	private void actionRefreshGameInfoList() {
		sendRequest(new Request("client.lobby.refresh"));
	}

	/**
	 * Process a "server.game.refresh" request containing the list. of the
	 * GameInfos associated with the games it hosts.
	 * @param request a request
	 */
	@SuppressWarnings("unchecked")
	private void processRefreshGameInfoList(final Request request) {
		final Set<GameInfo> waitingGames = (Set<GameInfo>) request.getContent();
		lobbyPanel.getGamesList().setListData(waitingGames.toArray(new GameInfo[waitingGames.size()]));
	}

	/**
	 * Sets an username and asks the server if it is available and valid.
	 */
	private void actionSubmitUsername() {
		username = lobbyPanel.getUsernamePanel().getUsername();

		lobbyPanel.getUsernamePanel().getSubmitBtn().setEnabled(false);
		sendRequest(new Request("client.lobby.username", username));
	}

	/**
	 * Process the answer given by the server to the username request.
	 * @param request The request to process
	 */
	private void processSubmitUsername(final Request request) {
		final String msg = (String) request.getContent();
		if ("".equals(msg)) {
			lobbyPanel.getUsernamePanel().switchToUsernamePanel(username);
			lobbyPanel.getGameCreationPanel().setVisible(true);
			lobbyPanel.getJoinGameBtn().setVisible(true);
		} else {
			lobbyPanel.getUsernamePanel().setUsername("");
			lobbyPanel.getUsernamePanel().getSubmitBtn().setEnabled(true);
			JOptionPane.showMessageDialog(null, msg, "Username", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the game described in the creation panel then submit it to the
	 * server.
	 */
	private void actionCreateGame() {
		final GameInfo gameInfo = lobbyPanel.getGameCreationPanel().makeGameInfo();
		if (gameInfo != null) {
			selectedGame = gameInfo;
			sendRequest(new Request("client.lobby.create", gameInfo));
		}
	}

	/**
	 * Reset the game name in the creation panel if the submitted game wasn't
	 * accepted.
	 * @param request The request to process
	 */
	private void processCreateGame(final Request request) {
		if (!(Boolean) request.getContent()) {
			selectedGame = null;
			lobbyPanel.getGameCreationPanel().resetGameName();
			JOptionPane.showMessageDialog(null, "The server refused to create this game", "Game creation",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Asks the server for the permission to join the selected game.
	 */
	public void actionJoinGame() {
		selectedGame = lobbyPanel.getGamesList().getSelectedValue();
		if (selectedGame != null) {
			sendRequest(new Request("client.lobby.join", selectedGame));
		}
	}

	/**
	 * Goes to the waiting room if the server refused to let the client in in a
	 * game.
	 * @param request The request to process
	 */
	private void processJoinGame(final Request request) {
		if ((Boolean) request.getContent()) {
			switchController(new WaitingRoomController(mainController, selectedGame, username));
		} else {
			JOptionPane.showMessageDialog(null, "Impossible to join the game !", "Join a game",
					JOptionPane.ERROR_MESSAGE);
			selectedGame = null;
		}
	}
}
