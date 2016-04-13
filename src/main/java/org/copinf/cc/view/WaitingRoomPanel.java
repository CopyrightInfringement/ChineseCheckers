package org.copinf.cc.view;

import org.copinf.cc.controller.WaitingRoomController;
import org.copinf.cc.net.GameInfo;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class WaitingRoomPanel extends JPanel {

	private JList<String> availablePlayers;
	private JLabel label;
	private final GameInfo gameInfo;
	public final JButton confirmButton;

	public WaitingRoomPanel(WaitingRoomController controller, GameInfo gameInfo) {
		super();
		this.gameInfo = gameInfo;

		label = new JLabel();
		add(label);

		this.availablePlayers = new JList<>();
		add(this.availablePlayers);

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(controller);
		confirmButton.setVisible(false);
		add(confirmButton);

		setPreferredSize(new Dimension(600, 400));

		label.setText("<html>Game " + gameInfo.name + "<br>" + "Please wait...</html>");
	}

	public void setAvailablePlayers(Collection<String> availablePlayers) {
		this.availablePlayers.setListData(availablePlayers.toArray(new String[availablePlayers.size()]));
		if (!availablePlayers.isEmpty()) {
			this.availablePlayers.setSelectedIndex(0);
			this.availablePlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}

	public String getTeamMate() {
		return availablePlayers.getSelectedValuesList().get(0);
	}

	public void enableTeamBuiding(boolean b) {
		confirmButton.setVisible(b);
		label.setText("<html>Game " + gameInfo.name + "<br>"
			+ (b ? "Select your teammate." : "Please wait...") + "</html>");
	}

	public void hasBeenPaired(String teamMate) {
		confirmButton.setVisible(false);
		label.setText("<html>Game " + gameInfo.name + "<br>" + teamMate + " is your teammate.</html>");
	}
}
