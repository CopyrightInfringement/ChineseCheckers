package org.copinf.cc.view.lobbypanel;

import org.copinf.cc.net.GameInfo;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/**
 * RoomPanel is what is displayed when the player chooses a game on a server or creates one.
 */
@SuppressWarnings("serial")
public class LobbyPanel extends JPanel {

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	private final JList<GameInfo> gamesList;
	private final JButton refreshGameInfoListBtn;

	private final UsernamePanel usernamePanel;

	public LobbyPanel() {
		super();
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		gamesList = new JList<>();
		gamesList.setCellRenderer(new GameInfoRenderer());

		final JScrollPane gameScrollPane = new JScrollPane(gamesList);
		springLayout.putConstraint(SpringLayout.NORTH, gameScrollPane, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameScrollPane, OFFSET_X, SpringLayout.WEST, this);
		add(gameScrollPane);
		gameScrollPane.setPreferredSize(new Dimension(300, 200));

		refreshGameInfoListBtn = new JButton(
			new ImageIcon(LobbyPanel.class.getResource("/fa-refresh-16.png")));
		springLayout.putConstraint(SpringLayout.NORTH, refreshGameInfoListBtn, OFFSET_Y, SpringLayout.SOUTH, gameScrollPane);
		springLayout.putConstraint(SpringLayout.EAST, refreshGameInfoListBtn, -OFFSET_X / 2, SpringLayout.HORIZONTAL_CENTER, gameScrollPane);
		add(refreshGameInfoListBtn);

		final JButton joinGameBtn = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, joinGameBtn, 0, SpringLayout.NORTH, refreshGameInfoListBtn);
		springLayout.putConstraint(SpringLayout.WEST, joinGameBtn, OFFSET_X, SpringLayout.EAST, refreshGameInfoListBtn);
		add(joinGameBtn);

		usernamePanel = new UsernamePanel();
		springLayout.putConstraint(SpringLayout.NORTH, usernamePanel, OFFSET_Y, SpringLayout.NORTH, gameScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, usernamePanel, OFFSET_X, SpringLayout.EAST, gameScrollPane);
		add(usernamePanel);

		final GameCreationPanel gameCreationPanel = new GameCreationPanel();
		springLayout.putConstraint(SpringLayout.NORTH, gameCreationPanel, OFFSET_Y, SpringLayout.SOUTH, usernamePanel);
		springLayout.putConstraint(SpringLayout.WEST, gameCreationPanel, 0, SpringLayout.WEST, usernamePanel);
		add(gameCreationPanel);

		springLayout.putConstraint(SpringLayout.EAST, this, OFFSET_X, SpringLayout.EAST, gameCreationPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, this, OFFSET_Y, SpringLayout.SOUTH, refreshGameInfoListBtn);
	}

	public JList<GameInfo> getGamesList() {
		return gamesList;
	}

	public JButton getRefreshGameInfoListBtn() {
		return refreshGameInfoListBtn;
	}

	public UsernamePanel getUsernamePanel() {
		return usernamePanel;
	}
}
