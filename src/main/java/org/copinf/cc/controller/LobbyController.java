package org.copinf.cc.controller;

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

	/**
	 * Constructs a new LobbyController.
	 * @param mainController the main controller
	 */
	public LobbyController(final MainController mainController) {
		super(mainController, "lobby");
		this.lobbyPanel = new LobbyPanel();

		lobbyPanel.getRefreshGameInfoListBtn().addActionListener(this);
	}

	@Override
	public JPanel start() {
		return lobbyPanel;
	}

	@Override
	public void processRequest(final Request request) {
		if (request.getSubRequest(2).equals("refresh")) {
			processRefreshGameInfoList(request);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		final Object source = ev.getSource();
		if (source.equals(lobbyPanel.getRefreshGameInfoListBtn())) {
			actionRefreshGameInfoList();
		}
	}

	private void actionRefreshGameInfoList() {
		sendRequest(new Request("client.lobby.refresh"));
	}

	@SuppressWarnings("unchecked")
	private void processRefreshGameInfoList(final Request request) {
		lobbyPanel.getGamesList().setListData(
			((Set<GameInfo>) request.getContent()).toArray(new GameInfo[]{}));
	}
}
