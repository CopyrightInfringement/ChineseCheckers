package org.copinf.cc.view.homepanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * The first panel the player sees, offering him to host a server or to join
 * one.
 */
public class HomePanel extends JPanel {

	/**
	 * The "Join" button
	 */
	public final JButton joinButton;
	/**
	 * The "Host" button
	 */
	public final JButton hostButton;
	private final JTextField hostTextField;
	private final JTextField portTextField;
	private final JLabel lblErrors;

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	private static final String DEBUG_HOST = "localhost";
	private static final String DEBUG_PORT = "25565";

	/**
	 * Constructs a HomePanel.
	 */
	public HomePanel() {
		super();
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		final JLabel lblTitle = new JLabel(
				"<html><p style='font-size:20px;text-align:center;'>Chinese Checkers</p></html>");
		springLayout.putConstraint(SpringLayout.NORTH, lblTitle, OFFSET_Y, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblTitle);

		final JLabel lblHost = new JLabel("Host :");
		springLayout.putConstraint(SpringLayout.NORTH, lblHost, OFFSET_Y * 2, SpringLayout.SOUTH, lblTitle);
		springLayout.putConstraint(SpringLayout.WEST, lblHost, OFFSET_X, SpringLayout.WEST, this);
		add(lblHost);
		hostTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, hostTextField, 0, SpringLayout.VERTICAL_CENTER,
				lblHost);
		springLayout.putConstraint(SpringLayout.WEST, hostTextField, OFFSET_X, SpringLayout.EAST, lblHost);
		add(hostTextField);
		hostTextField.setColumns(20);
		hostTextField.setText(DEBUG_HOST);

		portTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, portTextField, OFFSET_Y, SpringLayout.SOUTH, hostTextField);
		springLayout.putConstraint(SpringLayout.WEST, portTextField, 0, SpringLayout.WEST, hostTextField);
		springLayout.putConstraint(SpringLayout.EAST, portTextField, 0, SpringLayout.EAST, hostTextField);
		add(portTextField);
		portTextField.setColumns(20);
		portTextField.setText(DEBUG_PORT);
		final JLabel lblPort = new JLabel("Port :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, lblPort, 0, SpringLayout.VERTICAL_CENTER,
				portTextField);
		springLayout.putConstraint(SpringLayout.WEST, lblPort, OFFSET_X, SpringLayout.WEST, this);
		add(lblPort);

		joinButton = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, joinButton, OFFSET_Y, SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.EAST, joinButton, -OFFSET_X / 2, SpringLayout.HORIZONTAL_CENTER, this);
		add(joinButton);

		hostButton = new JButton("Host");
		springLayout.putConstraint(SpringLayout.NORTH, hostButton, 0, SpringLayout.NORTH, joinButton);
		springLayout.putConstraint(SpringLayout.WEST, hostButton, OFFSET_X / 2, SpringLayout.HORIZONTAL_CENTER, this);
		add(hostButton);

		joinButton.setPreferredSize(hostButton.getPreferredSize());

		lblErrors = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, lblErrors, OFFSET_Y, SpringLayout.SOUTH, joinButton);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblErrors, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblErrors);
		springLayout.putConstraint(SpringLayout.WEST, lblErrors, OFFSET_X, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblErrors, -OFFSET_X, SpringLayout.EAST, this);

		final JLabel lblCredits = new JLabel(
				"<html><p style='text-align: center;'>Louis Bal dit Sollier<br>Clara Bringer<br>Antonin Décimo<br>Pierre Gervais</p></html>");
		springLayout.putConstraint(SpringLayout.NORTH, lblCredits, OFFSET_Y, SpringLayout.SOUTH, lblErrors);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblCredits, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblCredits);

		springLayout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, hostTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, lblCredits);
	}

	/**
	 * Returns the host name of the server the user wants to join.
	 */
	public String getHost() {
		return hostTextField.getText();
	}

	/**
	 * Returns the port of the server the user wants to join.
	 */
	public int getPort() {
		return Integer.parseInt(portTextField.getText());
	}

	/**
	 * Display an error message.
	 */
	public void displayErrorMessage(final String msg) {
		lblErrors.setText("<html><p style='color:red;'>" + msg + "</p></html>");
	}

	/**
	 * Reset the error message.
	 */
	public void resetErrorMessage() {
		lblErrors.setText("");
	}
}
