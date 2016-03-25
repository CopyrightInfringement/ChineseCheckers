package org.copinf.cc.controller;

import org.copinf.cc.net.Request;
import org.copinf.cc.view.lobbypanel.LobbyPanel;

import javax.swing.JPanel;

/**
 * Controls the game creation panel.
 */
public class LobbyController extends AbstractController {

	private final LobbyPanel lobbyPanel;

	/**
	 * Constructs a new LobbyController.
	 */
	public LobbyController(final MainController mainController) {
		super(mainController, "lobby");
		this.lobbyPanel = new LobbyPanel();
	}

	@Override
	public JPanel start() {
		return lobbyPanel;
	}

	@Override
	public void processRequest(final Request request) {}
}
