package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * GamePanel is what is displayed during a game.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private final InfoBar infoBar;
	private final DrawZone drawZone;
	private final ActionZone actionZone;
	
	private final Map<Player, PlayerView> playerViews;

	/**
	 * Constructs a new GamePanel.
	 * @param game the current game
	 * @param player the playing player
	 */
	public GamePanel(final Game game, final Player player) {
		super();

		this.playerViews = new HashMap<>();
		int i = 0;
		for (final Player p : game.getPlayers()) {
			playerViews.put(p, new PlayerView(p, i));
			i++;
		}

		// UI
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		infoBar = new InfoBar(game, player, playerViews.get(player).color);
		infoBar.setPreferredSize(new Dimension(800, 50));
		add(infoBar);

		drawZone = new DrawZone(game, player, playerViews);
		add(drawZone);
		drawZone.setPreferredSize(new Dimension(800, 520));

		actionZone = new ActionZone();
		add(actionZone);
		actionZone.setPreferredSize(new Dimension(800, 30));
	}

	public InfoBar getInfoBar() {
		return infoBar;
	}

	public DrawZone getDrawZone() {
		return drawZone;
	}

	public ActionZone getActionZone() {
		return actionZone;
	}

	public Map<Player, PlayerView> getPlayerViews() {
		return this.playerViews;
	}
}
