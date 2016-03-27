package org.copinf.cc.view.lobbypanel;

import org.copinf.cc.net.GameInfo;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class GameInfoRenderer extends JPanel implements ListCellRenderer<GameInfo> {

	public GameInfoRenderer() {
		super();
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(final JList<? extends GameInfo> list,
			final GameInfo value, final int index, final boolean isSelected, final boolean cellHasFocus) {

		removeAll();
		add(new JLabel(value.name));
		add(new JLabel(value.nbPlayersCurrent + "/" + value.nbPlayersMax + " players"));
		add(new JLabel(value.nbZones + " zones"));
		if (value.teams) {
			add(new JLabel("teams"));
		}
		if (value.timer != -1) {
			add(new JLabel("timer " + value.timer + "s"));
		}

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
}
