package org.copinf.cc.view.waitingroompanel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class TeamBuildingPanel extends JPanel {
	private JList<String> availablePlayers;
	private JLabel label;
	public final JButton confirmButton;
	
	public TeamBuildingPanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		label = new JLabel();
		add(label);

		availablePlayers = new JList<>();
		add(availablePlayers, Component.LEFT_ALIGNMENT);

		confirmButton = new JButton("Confirm");
		add(confirmButton, Component.LEFT_ALIGNMENT);
		
		enableTeamBuiding(false);
	}
	
	/**
	 * Sets the list of all the players that can be picked as teammate.
	 * @param availablePlayers the players
	 */
	public void setAvailablePlayers(Collection<String> availablePlayers) {
		this.availablePlayers.setListData(availablePlayers.toArray(new String[availablePlayers.size()]));
		if (!availablePlayers.isEmpty()) {
			this.availablePlayers.setSelectedIndex(0);
			this.availablePlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}

	/**
	 * Returns the selected teammate.
	 * @return the selected teammate
	 */
	public String getTeamMate() {
		return availablePlayers.getSelectedValuesList().get(0);
	}

	/**
	 * Enable or disable team-buiding.
	 * @param b true if team(building should be enabled
	 */
	public void enableTeamBuiding(boolean b) {
		confirmButton.setVisible(b);
		if(b)
			label.setText("Select your teammate.");
		else
			label.setText("Please wait...");
	}

	/**
	 * Indicates that this player has been paired with a given teammate
	 * @param teamMate The teammate of this player
	 */
	public void hasBeenPaired(String teamMate) {
		confirmButton.setVisible(false);
		label.setText(teamMate + " is your teammate.");
	}
}
