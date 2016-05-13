package org.copinf.cc.view.lobbypanel;

import org.copinf.cc.net.GameInfo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

/**
 * LobbyPanel is what is displayed when the player chooses a game on a server or
 * creates one.
 */
@SuppressWarnings("serial")
public class LobbyPanel extends JPanel {

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	private final JList<GameInfo> gamesList;
	private final JButton refreshGameInfoListBtn;
	private final JButton joinGameBtn;

	private final UsernamePanel usernamePanel;
	private final GameCreationPanel gameCreationPanel;

	/**
	 * Constructs a new LobbyPanel.
	 */
	public LobbyPanel() {
		super();
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		gamesList = new JList<>();
		gamesList.setCellRenderer(new GameInfoRenderer());
		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final JScrollPane gameScrollPane = new JScrollPane(gamesList);
		springLayout.putConstraint(SpringLayout.NORTH, gameScrollPane, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameScrollPane, OFFSET_X, SpringLayout.WEST, this);
		add(gameScrollPane);

		refreshGameInfoListBtn = new JButton(new ImageIcon(LobbyPanel.class.getResource("/fa-refresh-16.png")));
		springLayout.putConstraint(SpringLayout.SOUTH, refreshGameInfoListBtn, -OFFSET_Y, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, refreshGameInfoListBtn, -OFFSET_X / 2,
				SpringLayout.HORIZONTAL_CENTER, gameScrollPane);
		add(refreshGameInfoListBtn);

		springLayout.putConstraint(SpringLayout.SOUTH, gameScrollPane, -OFFSET_Y, SpringLayout.NORTH,
				refreshGameInfoListBtn);

		joinGameBtn = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, joinGameBtn, 0, SpringLayout.NORTH, refreshGameInfoListBtn);
		springLayout.putConstraint(SpringLayout.WEST, joinGameBtn, OFFSET_X, SpringLayout.EAST, refreshGameInfoListBtn);
		add(joinGameBtn);
		joinGameBtn.setVisible(false);

		refreshGameInfoListBtn.setPreferredSize(joinGameBtn.getPreferredSize());

		usernamePanel = new UsernamePanel();
		springLayout.putConstraint(SpringLayout.NORTH, usernamePanel, OFFSET_Y, SpringLayout.NORTH, gameScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, usernamePanel, OFFSET_X, SpringLayout.EAST, gameScrollPane);
		add(usernamePanel);

		gameCreationPanel = new GameCreationPanel();
		springLayout.putConstraint(SpringLayout.NORTH, gameCreationPanel, OFFSET_Y, SpringLayout.SOUTH, usernamePanel);
		springLayout.putConstraint(SpringLayout.WEST, gameCreationPanel, 0, SpringLayout.WEST, usernamePanel);
		add(gameCreationPanel);
		gameCreationPanel.setVisible(false);

		springLayout.putConstraint(SpringLayout.EAST, this, OFFSET_X, SpringLayout.EAST, gameCreationPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, this, OFFSET_Y, SpringLayout.SOUTH, gameCreationPanel);
	}

	/**
	 * Returns the list of games hosted by the server.
	 * @return the list
	 */
	public JList<GameInfo> getGamesList() {
		return gamesList;
	}

	/**
	 * Returns the "Refresh" button.
	 * @return the button
	 */
	public JButton getRefreshGameInfoListBtn() {
		return refreshGameInfoListBtn;
	}

	/**
	 * Returns the "Join" button.
	 * @return the button
	 */
	public JButton getJoinGameBtn() {
		return joinGameBtn;
	}

	/**
	 * Returns the username panel.
	 * @return the panel
	 */
	public UsernamePanel getUsernamePanel() {
		return usernamePanel;
	}

	/**
	 * Returns the game creation panel.
	 * @return the panel
	 */
	public GameCreationPanel getGameCreationPanel() {
		return gameCreationPanel;
	}
}
