package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;

import java.awt.Dimension;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * GamePanel is what is displayed during a game.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/** The InfoBar of this panel.*/
	public final InfoBar infoBar;
	/** The DrawZone of this panel. */
	public final DrawZone drawZone;
	/** The ActionZone of this panel. */
	public final ActionZone actionZone;

	/**
	 * Constructs a new GamePanel.
	 *
	 * @param game the current game
	 * @param player the playing player
	 * @param currentMovement the current movement
	 */
	public GamePanel(final Game game, final Player player, final Movement currentMovement) {
		super();

		final Map<Player, PlayerView> playerViews = new ConcurrentHashMap<>();
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

		drawZone = new DrawZone(game, player, playerViews, currentMovement);
		add(drawZone);
		drawZone.setPreferredSize(new Dimension(800, 500));

		actionZone = new ActionZone();
		add(actionZone);
		actionZone.setPreferredSize(new Dimension(800, 50));
	}
}
