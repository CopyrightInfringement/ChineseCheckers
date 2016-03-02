package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	private Map<Player, PlayerView> playerViews;

	/**
	 * Constructs a new GamePanel.
	 * @param game the current game
	 * @param player the playing player
	 */
	public GamePanel(final Game game, final Player player) {
		super();
		this.game = game;
		this.player = player;

		this.playerViews = new HashMap<>();
		int i = 0;
		for (Player p : game.getPlayers()) {
			playerViews.put(p, new PlayerView(p, i));
			i++;
		}

		// UI
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		infoBar = new InfoBar(game, player);
		infoBar.setPreferredSize(new Dimension(800, 50));
		infoBar.setBorder(BorderFactory.createLineBorder(Color.black));
		add(infoBar);

		drawZone = new DrawZone(game, player, playerViews);
		drawZone.setPreferredSize(new Dimension(800, 500));
		drawZone.setBorder(BorderFactory.createLineBorder(Color.black));
		add(drawZone);

		actionZone = new ActionZone();
		actionZone.setPreferredSize(new Dimension(800, 50));
		actionZone.setBorder(BorderFactory.createLineBorder(Color.black));
		add(actionZone);
	}
}
