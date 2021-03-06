package org.copinf.cc.view.lobbypanel;

import org.copinf.cc.net.GameInfo;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * Renders a cell in a list of GameInfo.
 */
@SuppressWarnings("serial")
public class GameInfoRenderer extends JPanel implements ListCellRenderer<GameInfo> {

	/**
	 * Constructs a GameInfoRenderer.
	 */
	public GameInfoRenderer() {
		super();
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(final JList<? extends GameInfo> list, final GameInfo value,
			final int index, final boolean isSelected, final boolean cellHasFocus) {

		removeAll();
		add(new JLabel(value.getName()));
		add(new JLabel(value.getCurrentPlayersNumber() + "/" + value.getNbPlayersMax() + " players"));
		add(new JLabel(value.getNbZones() + " zones"));
		if (value.isTeams()) {
			add(new JLabel("teams"));
		}
		if (value.getTimer() != -1) {
			add(new JLabel("timer " + value.getTimer() + "min"));
		}

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setToolTipText(value.getCurrentPlayers().toString());

		return this;
	}
}
