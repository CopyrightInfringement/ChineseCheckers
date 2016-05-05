package org.copinf.cc.view.gamepanel;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;

/**
 * GamePanel is what is displayed during a game.
 */
public class GamePanel extends JPanel {

	/**
	 * The InfoBar of this panel
	 */
	public final InfoBar infoBar;
	/**
	 * The DrawZone of this panel
	 */
	public final DrawZone drawZone;
	/**
	 * The ActionZone of this panel
	 */
	public final ActionZone actionZone;

	private final Map<Player, PlayerView> playerViews;
	
	private final Movement currentMovement;

	/**
	 * Constructs a new GamePanel.
	 *
	 * @param game the current game
	 * @param player the playing player
	 */
	public GamePanel(final Game game, final Player player, final Movement currentMovement) {
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
		
		this.currentMovement = currentMovement;

		drawZone = new DrawZone(game, player, playerViews, this.currentMovement);
		add(drawZone);
		drawZone.setPreferredSize(new Dimension(800, 500));

		actionZone = new ActionZone();
		add(actionZone);
		actionZone.setPreferredSize(new Dimension(800, 50));
	}
}
