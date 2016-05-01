package org.copinf.cc.view.waitingroompanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WaitingRoomPanel extends JPanel {

	public final TeamBuildingPanel teamBuildingPanel;
	public final ChatPanel chatPanel;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int CHAT_WIDTH = 350;

	public WaitingRoomPanel() {
		super();
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		SpringLayout layout = new SpringLayout();
		
		setLayout(layout);
		
		chatPanel = new ChatPanel();
		teamBuildingPanel = new TeamBuildingPanel();

		layout.getConstraints(chatPanel).setWidth(Spring.constant(CHAT_WIDTH));
		
		layout.putConstraint(SpringLayout.WEST, chatPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, chatPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, chatPanel, 0, SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.EAST, chatPanel);
		layout.putConstraint(SpringLayout.NORTH, separator, 2, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 2, SpringLayout.SOUTH, separator);

		layout.putConstraint(SpringLayout.WEST, teamBuildingPanel, 0, SpringLayout.EAST, separator);
		layout.putConstraint(SpringLayout.EAST, teamBuildingPanel, 0, SpringLayout.EAST, this);		
		layout.putConstraint(SpringLayout.NORTH, teamBuildingPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, teamBuildingPanel, 0, SpringLayout.SOUTH, this);
				
		add(chatPanel);
		add(separator);
		add(teamBuildingPanel);
	}
}
