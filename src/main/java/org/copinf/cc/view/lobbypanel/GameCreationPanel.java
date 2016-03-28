package org.copinf.cc.view.lobbypanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class GameCreationPanel extends JPanel {

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	public GameCreationPanel() {
		super();
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JLabel gameNameLbl = new JLabel("Game name :");
		springLayout.putConstraint(SpringLayout.NORTH, gameNameLbl, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNameLbl, OFFSET_X, SpringLayout.WEST, this);
		add(gameNameLbl);
		final JTextField gameNameTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, gameNameTextField, 0, SpringLayout.VERTICAL_CENTER, gameNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextField, OFFSET_X, SpringLayout.EAST, gameNameLbl);
		add(gameNameTextField);
		gameNameTextField.setColumns(10);

		final JComboBox<String> boardChooser = new JComboBox<>(new String[]{"Default Board"});
		springLayout.putConstraint(SpringLayout.NORTH, boardChooser, OFFSET_Y, SpringLayout.SOUTH, gameNameTextField);
		add(boardChooser);
		final JLabel boardChooserLbl = new JLabel("Board :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardChooserLbl, 0, SpringLayout.VERTICAL_CENTER, boardChooser);
		springLayout.putConstraint(SpringLayout.WEST, boardChooserLbl, 0, SpringLayout.WEST, gameNameLbl);
		add(boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardChooser, OFFSET_X, SpringLayout.EAST, boardChooserLbl);

		final JLabel boardSizeLbl = new JLabel("Board size :");
		springLayout.putConstraint(SpringLayout.NORTH, boardSizeLbl, 0, SpringLayout.NORTH, boardChooserLbl);
		springLayout.putConstraint(SpringLayout.WEST, boardSizeLbl, OFFSET_X * 3, SpringLayout.EAST, boardChooser);
		add(boardSizeLbl);
		final JComboBox<Integer> boardSizeChooser = new JComboBox<>(new Integer[]{4});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, boardSizeChooser, 0, SpringLayout.VERTICAL_CENTER, boardSizeLbl);
		add(boardSizeChooser);

		final JComboBox<Integer> playerNumberChooser = new JComboBox<>(new Integer[]{2, 3, 4, 6});
		springLayout.putConstraint(SpringLayout.NORTH, playerNumberChooser, OFFSET_Y, SpringLayout.SOUTH, boardChooser);
		add(playerNumberChooser);
		final JLabel playerNumberLbl = new JLabel("Player number :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, playerNumberLbl, 0, SpringLayout.VERTICAL_CENTER, playerNumberChooser);
		springLayout.putConstraint(SpringLayout.WEST, playerNumberLbl, 0, SpringLayout.WEST, boardChooserLbl);
		add(playerNumberLbl);
		springLayout.putConstraint(SpringLayout.EAST, playerNumberChooser, 0, SpringLayout.EAST, boardChooser);

		final JLabel playerZonesLbl = new JLabel("Zones number :");
		springLayout.putConstraint(SpringLayout.NORTH, playerZonesLbl, 0, SpringLayout.NORTH, playerNumberLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerZonesLbl, 0, SpringLayout.WEST, boardSizeLbl);
		add(playerZonesLbl);
		final JComboBox<Integer> playerZonesChooser = new JComboBox<>(new Integer[]{1, 2, 3});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, playerZonesChooser, 0, SpringLayout.VERTICAL_CENTER, playerZonesLbl);
		springLayout.putConstraint(SpringLayout.WEST, playerZonesChooser, OFFSET_X, SpringLayout.EAST, playerZonesLbl);
		add(playerZonesChooser);
		springLayout.putConstraint(SpringLayout.EAST, boardSizeChooser, 0, SpringLayout.EAST, playerZonesChooser);

		final JCheckBox teamsCheckBox = new JCheckBox(": Teams");
		springLayout.putConstraint(SpringLayout.NORTH, teamsCheckBox, OFFSET_Y, SpringLayout.SOUTH, playerNumberLbl);
		springLayout.putConstraint(SpringLayout.WEST, teamsCheckBox, 0, SpringLayout.WEST, playerNumberLbl);
		add(teamsCheckBox);

		final JCheckBox timerCheckBox = new JCheckBox(": Timer [min]");
		springLayout.putConstraint(SpringLayout.NORTH, timerCheckBox, OFFSET_Y, SpringLayout.SOUTH, teamsCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, timerCheckBox, 0, SpringLayout.WEST, teamsCheckBox);
		add(timerCheckBox);
		final JSpinner timerSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.5, 5.0, 0.5));
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, timerSpinner, 0, SpringLayout.VERTICAL_CENTER, timerCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, timerSpinner, OFFSET_X, SpringLayout.EAST, timerCheckBox);
		add(timerSpinner);

		springLayout.putConstraint(SpringLayout.EAST, this, OFFSET_X, SpringLayout.EAST, playerZonesChooser);
		springLayout.putConstraint(SpringLayout.SOUTH, this, OFFSET_Y, SpringLayout.SOUTH, timerSpinner);
	}
}
