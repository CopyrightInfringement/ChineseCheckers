package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;

import java.awt.Dimension;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * GamePanel is what is displayed during a game.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/** The InfoBar of this panel. */
	private final InfoBar infoBar;
	/** The DrawZone of this panel. */
	private final DrawZone drawZone;
	/** The ActionZone of this panel. */
	private final ActionZone actionZone;

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
		final Dimension preferedSize = new Dimension(800, 600);
		setPreferredSize(preferedSize);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		infoBar = new InfoBar(game, player, playerViews.get(player).getColor());
		add(getInfoBar());

		drawZone = new DrawZone(game, player, playerViews, currentMovement);
		add(getDrawZone());

		actionZone = new ActionZone();
		getActionZone().setPreferredSize(new Dimension(preferedSize.width, 50));

		getDrawZone().setPreferredSize(new Dimension(preferedSize.width,
				preferedSize.height - infoBar.getPreferredSize().height - actionZone.getPreferredSize().height));

		add(getActionZone());
	}

	/**
	 * Returns the info bar.
	 */
	public InfoBar getInfoBar() {
		return infoBar;
	}

	/**
	 * returns the draw zone.
	 */
	public DrawZone getDrawZone() {
		return drawZone;
	}

	/**
	 * Returns the action zone.
	 */
	public ActionZone getActionZone() {
		return actionZone;
	}
}
