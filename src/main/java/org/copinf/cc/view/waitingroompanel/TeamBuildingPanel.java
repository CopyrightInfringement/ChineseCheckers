package org.copinf.cc.view.waitingroompanel;

import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class TeamBuildingPanel extends JPanel {

	private final JList<String> availablePlayers;
	private final JLabel label;
	public final JButton confirmButton;

	public TeamBuildingPanel() {
		super();

		final SpringLayout layout = new SpringLayout();
		setLayout(layout);

		label = new JLabel();

		availablePlayers = new JList<>();

		confirmButton = new JButton("Confirm");

		layout.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, availablePlayers, 10, SpringLayout.SOUTH, label);
		layout.putConstraint(SpringLayout.WEST, availablePlayers, 0, SpringLayout.WEST, label);

		layout.putConstraint(SpringLayout.WEST, confirmButton, 0, SpringLayout.WEST, label);
		layout.putConstraint(SpringLayout.NORTH, confirmButton, 10, SpringLayout.SOUTH, availablePlayers);

		add(label);
		add(availablePlayers);
		add(confirmButton);

		confirmButton.setEnabled(false);

		enableTeamBuiding(false);
	}

	/**
	 * Sets the list of all the players that can be picked as teammate.
	 * @param availablePlayers the players
	 */
	public void setAvailablePlayers(final Collection<String> availablePlayers) {
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
	 * @param b true if team building should be enabled
	 */
	public void enableTeamBuiding(final boolean b) {
		confirmButton.setEnabled(b);
		if (b) {
			label.setText("Select your teammate.");
		} else {
			label.setText("Please wait...");
		}
	}

	/**
	 * Indicates that this player has been paired with a given teammate.
	 * @param teamMate The teammate of this player
	 */
	public void hasBeenPaired(final String teamMate) {
		confirmButton.setVisible(false);
		label.setText(teamMate + " is your teammate.");
	}
}
