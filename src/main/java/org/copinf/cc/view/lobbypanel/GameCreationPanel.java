package org.copinf.cc.view.lobbypanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class GameCreationPanel extends JPanel {

	private final int OFFSET_X = 10;
	private final int OFFSET_Y = 10;

	public GameCreationPanel() {
		super();
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JLabel gameNameLbl = new JLabel("Game name");
		springLayout.putConstraint(SpringLayout.NORTH, gameNameLbl, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNameLbl, OFFSET_X, SpringLayout.WEST, this);
		add(gameNameLbl);
		final JTextField gameNameTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, gameNameTextField, 0, SpringLayout.VERTICAL_CENTER, gameNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextField, OFFSET_X, SpringLayout.EAST, gameNameLbl);
		add(gameNameTextField);
		gameNameTextField.setColumns(10);

		final JLabel boardChooserLbl = new JLabel("Board :");
		springLayout.putConstraint(SpringLayout.NORTH, boardChooserLbl, OFFSET_Y, SpringLayout.SOUTH, gameNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardChooserLbl, 0, SpringLayout.WEST, gameNameLbl);
		add(boardChooserLbl);
		final JComboBox<String> boardChooser = new JComboBox<>(new String[]{"Default Board"});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardChooser, 0, SpringLayout.VERTICAL_CENTER, boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardChooser, OFFSET_X, SpringLayout.EAST, boardChooserLbl);
		add(boardChooser);

		final JLabel boardSizeLbl = new JLabel("Board size :");
		springLayout.putConstraint(SpringLayout.NORTH, boardSizeLbl, 0, SpringLayout.NORTH, boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardSizeLbl, OFFSET_X * 3, SpringLayout.EAST, boardChooser);
		add(boardSizeLbl);
		final JComboBox<Integer> boardSizeChooser = new JComboBox<>(new Integer[]{4});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardSizeChooser, 0, SpringLayout.VERTICAL_CENTER, boardSizeLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardSizeChooser, OFFSET_X, SpringLayout.EAST, boardSizeLbl);
		add(boardSizeChooser);

		final JLabel playerNumberLbl = new JLabel("Player number :");
		springLayout.putConstraint(SpringLayout.NORTH, playerNumberLbl, OFFSET_Y, SpringLayout.SOUTH, boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerNumberLbl, 0, SpringLayout.WEST, boardChooserLbl);
		add(playerNumberLbl);
		final JComboBox<Integer> playerNumberChooser = new JComboBox<>(new Integer[]{2, 3, 4, 6});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, playerNumberChooser, 0, SpringLayout.VERTICAL_CENTER, playerNumberLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerNumberChooser, OFFSET_X, SpringLayout.EAST, playerNumberLbl);
		add(playerNumberChooser);

		final JCheckBox teamsCheckBox = new JCheckBox(": Teams");
		springLayout.putConstraint(SpringLayout.NORTH, teamsCheckBox, OFFSET_Y, SpringLayout.SOUTH, playerNumberLbl);
		springLayout.putConstraint(SpringLayout.WEST, teamsCheckBox, 0, SpringLayout.WEST, playerNumberLbl);
		add(teamsCheckBox);

		final JCheckBox timerCheckBox = new JCheckBox(": Timer [s]");
		springLayout.putConstraint(SpringLayout.NORTH, timerCheckBox, OFFSET_Y, SpringLayout.SOUTH, teamsCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, timerCheckBox, 0, SpringLayout.WEST, teamsCheckBox);
		add(timerCheckBox);
		final JTextField timerTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, timerTextField, 0, SpringLayout.VERTICAL_CENTER, timerCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, timerTextField, OFFSET_X, SpringLayout.EAST, timerCheckBox);
		add(timerTextField);
		timerTextField.setColumns(3);

		springLayout.putConstraint(SpringLayout.EAST, this, OFFSET_X, SpringLayout.EAST, boardSizeChooser);
		springLayout.putConstraint(SpringLayout.SOUTH, this, OFFSET_Y, SpringLayout.SOUTH, timerCheckBox);

	}
}
