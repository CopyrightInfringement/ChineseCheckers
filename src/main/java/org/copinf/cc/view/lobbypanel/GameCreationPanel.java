package org.copinf.cc.view.lobbypanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.net.GameInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

/**
 * The panel used to create a game.
 */
@SuppressWarnings("serial")
public class GameCreationPanel extends JPanel {

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	private final JTextField gameNameTextField;
	/** The "Create" button. */
	private final JButton createGameBtn;
	private final JComboBox<String> boardChooser;
	private final JSpinner boardSizeChooser;
	private final JComboBox<Integer> playerNumberChooser;
	private final JComboBox<Integer> playerZonesChooser;
	private final JCheckBox teamsCheckBox;
	private final JCheckBox timerCheckBox;
	private final JSpinner timerSpinner;

	private final Map<String, AbstractBoard> boards;

	/**
	 * Constructs a GameCreationPanel.
	 */
	public GameCreationPanel() {
		super();

		boards = new HashMap<>();

		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JPanel gameNamePanel = new JPanel();
		add(gameNamePanel);
		springLayout.putConstraint(SpringLayout.NORTH, gameNamePanel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNamePanel, 0, SpringLayout.WEST, this);
		final JLabel gameNameLbl = new JLabel("Game name :");
		gameNamePanel.add(gameNameLbl);
		gameNameTextField = new JTextField();
		gameNamePanel.add(gameNameTextField);
		gameNameTextField.setColumns(10);
		createGameBtn = new JButton("Create");
		gameNamePanel.add(getCreateGameBtn());

		boardChooser = new JComboBox<>();
		springLayout.putConstraint(SpringLayout.NORTH, boardChooser, OFFSET_Y, SpringLayout.SOUTH, gameNamePanel);
		add(boardChooser);
		final JLabel boardChooserLbl = new JLabel("Board :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardChooserLbl, 0, SpringLayout.VERTICAL_CENTER,
				boardChooser);
		springLayout.putConstraint(SpringLayout.WEST, boardChooserLbl, OFFSET_X, SpringLayout.WEST, gameNamePanel);
		add(boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardChooser, OFFSET_X, SpringLayout.EAST, boardChooserLbl);
		boardChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ev) {
				boardChange();
			}
		});

		final JLabel boardSizeLbl = new JLabel("Board size :");
		springLayout.putConstraint(SpringLayout.NORTH, boardSizeLbl, 0, SpringLayout.NORTH, boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardSizeLbl, OFFSET_X * 3, SpringLayout.EAST, boardChooser);
		add(boardSizeLbl);
		boardSizeChooser = new JSpinner();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardSizeChooser, 0, SpringLayout.VERTICAL_CENTER,
				boardSizeLbl);
		add(boardSizeChooser);

		playerNumberChooser = new JComboBox<>();
		springLayout.putConstraint(SpringLayout.NORTH, playerNumberChooser, OFFSET_Y, SpringLayout.SOUTH, boardChooser);
		add(playerNumberChooser);
		final JLabel playerNumberLbl = new JLabel("Players number :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, playerNumberLbl, 0, SpringLayout.VERTICAL_CENTER,
				playerNumberChooser);
		springLayout.putConstraint(SpringLayout.WEST, playerNumberLbl, 0, SpringLayout.WEST, boardChooserLbl);
		add(playerNumberLbl);
		springLayout.putConstraint(SpringLayout.EAST, playerNumberChooser, 0, SpringLayout.EAST, boardChooser);
		playerNumberChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ev) {
				playerNumberChange();
			}
		});

		final JLabel playerZonesLbl = new JLabel("Zones number :");
		springLayout.putConstraint(SpringLayout.NORTH, playerZonesLbl, 0, SpringLayout.NORTH, playerNumberLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerZonesLbl, 0, SpringLayout.WEST, boardSizeLbl);
		add(playerZonesLbl);
		playerZonesChooser = new JComboBox<>();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, playerZonesChooser, 0, SpringLayout.VERTICAL_CENTER,
				playerZonesLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerZonesChooser, OFFSET_X, SpringLayout.EAST, playerZonesLbl);
		springLayout.putConstraint(SpringLayout.EAST, boardSizeChooser, 0, SpringLayout.EAST, playerZonesChooser);
		add(playerZonesChooser);

		timerSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1));
		springLayout.putConstraint(SpringLayout.NORTH, timerSpinner, OFFSET_Y, SpringLayout.SOUTH, playerZonesChooser);
		springLayout.putConstraint(SpringLayout.EAST, timerSpinner, 0, SpringLayout.EAST, playerZonesChooser);
		add(timerSpinner);
		timerCheckBox = new JCheckBox(": Timer [min]");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, timerCheckBox, 0, SpringLayout.VERTICAL_CENTER,
				timerSpinner);
		springLayout.putConstraint(SpringLayout.EAST, timerCheckBox, -OFFSET_X, SpringLayout.WEST, timerSpinner);
		add(timerCheckBox);
		timerCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ev) {
				timerSpinner.setEnabled(timerCheckBox.isSelected());
			}
		});
		timerSpinner.setEnabled(timerCheckBox.isSelected());

		teamsCheckBox = new JCheckBox(": Teams");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, teamsCheckBox, 0, SpringLayout.VERTICAL_CENTER,
				timerCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, teamsCheckBox, 0, SpringLayout.WEST, playerNumberLbl);
		add(teamsCheckBox);

		springLayout.putConstraint(SpringLayout.EAST, this, OFFSET_X, SpringLayout.EAST, playerZonesChooser);
		springLayout.putConstraint(SpringLayout.SOUTH, this, OFFSET_Y, SpringLayout.SOUTH, timerSpinner);
	}

	/**
	 * Adds a board to the list of possible boards.
	 * @param board a board
	 */
	public void addBoard(final AbstractBoard board) {
		boardChooser.addItem(board.getClass().getSimpleName());
		boards.put(board.getClass().getSimpleName(), board);
		boardChange();
	}

	private void boardChange() {
		final AbstractBoard board = boards.get(boardChooser.getSelectedItem());
		if (board != null) {
			final Integer[] playerNumbers = board.getPossiblePlayerNumbers().toArray(new Integer[] {});
			playerNumberChooser.setModel(new DefaultComboBoxModel<Integer>(playerNumbers));
			boardSizeChooser.setModel(new SpinnerNumberModel(
					board.getDefaultSize(((Integer) playerNumberChooser.getSelectedItem()).intValue()), 1, 10, 1));
			playerNumberChange();
		}
	}

	private void playerNumberChange() {
		final AbstractBoard board = boards.get(boardChooser.getSelectedItem());
		final int playerNumber = ((Integer) playerNumberChooser.getSelectedItem()).intValue();
		final Integer[] possibleZones = board.getPossibleZoneNumbers(playerNumber).toArray(new Integer[] {});
		playerZonesChooser.setModel(new DefaultComboBoxModel<Integer>(possibleZones));
		if (board.getPossibleTeam(playerNumber)) {
			teamsCheckBox.setEnabled(true);
		} else {
			teamsCheckBox.setEnabled(false);
			teamsCheckBox.setSelected(false);
		}
	}

	/**
	 * Creates the GameInfo from the description made in this panel.
	 * @return the new GameInfo
	 */
	public GameInfo makeGameInfo() {
		final String gameName = gameNameTextField.getText();
		if (!gameName.isEmpty()) {
			return new GameInfo(gameName, (Integer) playerNumberChooser.getSelectedItem(),
					(Integer) playerZonesChooser.getSelectedItem(), teamsCheckBox.isSelected(),
					(Integer) boardSizeChooser.getValue(),
					timerCheckBox.isSelected() ? (Double) timerSpinner.getValue() : -1);
		}
		return null;
	}

	/**
	 * Reset the game name.
	 */
	public void resetGameName() {
		gameNameTextField.setText("");
	}

	/**
	 * Returns the "Create" button.
	 * @return the button
	 */
	public JButton getCreateGameBtn() {
		return createGameBtn;
	}
}
