package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * GamePanel is what is displayed during a game.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private Game game;
	private Player player;

	private InfoBar infoBar;
	private DrawZone drawZone;
	private ActionZone actionZone;

	/**
	 * Constructs a new GamePanel.
	 * @param game the current game
	 * @param player the playing player
	 */
	public GamePanel(final Game game, final Player player) {
		super();
		this.game = game;
		this.player = player;

		// UI
		//setPreferredSize(new Dimension(800, 800));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		/*
		infoBar = new InfoBar(game, player);
		infoBar.setPreferredSize(new Dimension(800, 50));
		infoBar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(infoBar);
		*/

		drawZone = new DrawZone(game, player);
		drawZone.setPreferredSize(new Dimension(800, 700));
		drawZone.setBorder(BorderFactory.createLineBorder(Color.black));
		add(drawZone);

		/*
		actionZone = new ActionZone();
		actionZone.setPreferredSize(new Dimension(800, 50));
		actionZone.setBorder(BorderFactory.createLineBorder(Color.black));
		add(actionZone);
		*/
	}
}
