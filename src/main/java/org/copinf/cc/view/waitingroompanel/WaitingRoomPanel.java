package org.copinf.cc.view.waitingroompanel;

import org.copinf.cc.controller.WaitingRoomController;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WaitingRoomPanel extends JPanel {

	public final TeamBuildingPanel teamBuildingPanel;
	public final ChatPanel chatPanel;
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int CHAT_WIDTH = 350;
	
	public WaitingRoomPanel(WaitingRoomController controller) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		chatPanel = new ChatPanel(CHAT_WIDTH, HEIGHT);
		teamBuildingPanel = new TeamBuildingPanel(WIDTH - CHAT_WIDTH, HEIGHT);
		
		add(chatPanel);
		add(new javax.swing.JSeparator(SwingConstants.VERTICAL));
		add(teamBuildingPanel);
	}
}
