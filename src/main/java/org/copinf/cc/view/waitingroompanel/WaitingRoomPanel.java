package org.copinf.cc.view.waitingroompanel;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/**
 * The waiting room panel where players go when there are not yet enough players to start a game.
 */
@SuppressWarnings("serial")
public class WaitingRoomPanel extends JPanel {

	private final TeamBuildingPanel teamBuildingPanel;
	private final ChatPanel chatPanel;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int CHAT_WIDTH = 350;

	/**
	 * Constructs a new WaitingRoomPanel.
	 */
	public WaitingRoomPanel() {
		super();

		final JSeparator separator = new JSeparator(SwingConstants.VERTICAL);

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		final SpringLayout layout = new SpringLayout();

		setLayout(layout);

		chatPanel = new ChatPanel();
		teamBuildingPanel = new TeamBuildingPanel();

		layout.getConstraints(getChatPanel()).setWidth(Spring.constant(CHAT_WIDTH));

		layout.putConstraint(SpringLayout.WEST, getChatPanel(), 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, getChatPanel(), 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, getChatPanel(), 0, SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.EAST, getChatPanel());
		layout.putConstraint(SpringLayout.NORTH, separator, 2, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 2, SpringLayout.SOUTH, separator);

		layout.putConstraint(SpringLayout.WEST, getTeamBuildingPanel(), 0, SpringLayout.EAST, separator);
		layout.putConstraint(SpringLayout.EAST, getTeamBuildingPanel(), 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, getTeamBuildingPanel(), 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, getTeamBuildingPanel(), 0, SpringLayout.SOUTH, this);

		add(getChatPanel());
		add(separator);
		add(getTeamBuildingPanel());
	}

	public TeamBuildingPanel getTeamBuildingPanel() {
		return teamBuildingPanel;
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}
}
