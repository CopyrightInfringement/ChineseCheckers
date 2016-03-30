package org.copinf.cc.controller;

import org.copinf.cc.model.DefaultBoard;
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

	/**
	 * Constructs a new LobbyController.
	 * @param mainController the main controller
	 */
	public LobbyController(final MainController mainController) {
		super(mainController, "lobby");
		this.lobbyPanel = new LobbyPanel();

		lobbyPanel.getRefreshGameInfoListBtn().addActionListener(this);
		lobbyPanel.getUsernamePanel().getSubmitBtn().addActionListener(this);
		lobbyPanel.getGameCreationPanel().addBoard(new DefaultBoard(0));
		lobbyPanel.getGameCreationPanel().getCreateGameBtn().addActionListener(this);
	}

	@Override
	public JPanel start() {
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
		}
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		final Object source = ev.getSource();
		if (source.equals(lobbyPanel.getRefreshGameInfoListBtn())) {
			actionRefreshGameInfoList();
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
		Set<GameInfo> waitingGames = (Set<GameInfo>) request.getContent();
		System.out.println(waitingGames.size());
		lobbyPanel.getGamesList().setListData(waitingGames.toArray(new GameInfo[waitingGames.size()]));
		for (final GameInfo gi : waitingGames) {
			System.out.println("CLIENT " + gi.name);
		}
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
			sendRequest(new Request("client.lobby.create", gameInfo));
		}
	}

	private void processCreateGame(final Request request) {
		if (!(Boolean) request.getContent()) {
			lobbyPanel.getGameCreationPanel().resetGameName();
		}
	}
}
