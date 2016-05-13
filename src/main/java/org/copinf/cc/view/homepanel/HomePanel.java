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
@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	/** The "Join" button. */
	private final JButton joinButton;
	/** The "Host" button. */
	private final JButton hostButton;
	private final JTextField hostTextField;
	private final JTextField portTextField;
	private final JLabel lblErrors;

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	/**
	 * Constructs a HomePanel.
	 * @param host the default host server
	 * @param port the default port
	 */
	public HomePanel(final String host, final int port) {
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
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, getHostTextField(), 0, SpringLayout.VERTICAL_CENTER,
				lblHost);
		springLayout.putConstraint(SpringLayout.WEST, getHostTextField(), OFFSET_X, SpringLayout.EAST, lblHost);
		add(getHostTextField());
		getHostTextField().setColumns(20);
		getHostTextField().setText(host);

		portTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, getPortTextField(), OFFSET_Y, SpringLayout.SOUTH,
				getHostTextField());
		springLayout.putConstraint(SpringLayout.WEST, getPortTextField(), 0, SpringLayout.WEST, getHostTextField());
		springLayout.putConstraint(SpringLayout.EAST, getPortTextField(), 0, SpringLayout.EAST, getHostTextField());
		add(getPortTextField());
		getPortTextField().setColumns(20);
		getPortTextField().setText(Integer.toString(port));
		final JLabel lblPort = new JLabel("Port :");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, lblPort, 0, SpringLayout.VERTICAL_CENTER,
				getPortTextField());
		springLayout.putConstraint(SpringLayout.WEST, lblPort, OFFSET_X, SpringLayout.WEST, this);
		add(lblPort);

		joinButton = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, getJoinButton(), OFFSET_Y, SpringLayout.SOUTH,
				getPortTextField());
		springLayout.putConstraint(SpringLayout.EAST, getJoinButton(), -OFFSET_X / 2, SpringLayout.HORIZONTAL_CENTER,
				this);
		add(getJoinButton());

		hostButton = new JButton("Host");
		springLayout.putConstraint(SpringLayout.NORTH, getHostButton(), 0, SpringLayout.NORTH, getJoinButton());
		springLayout.putConstraint(SpringLayout.WEST, getHostButton(), OFFSET_X / 2, SpringLayout.HORIZONTAL_CENTER,
				this);
		add(getHostButton());

		getJoinButton().setPreferredSize(getHostButton().getPreferredSize());

		lblErrors = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, lblErrors, OFFSET_Y, SpringLayout.SOUTH, getJoinButton());
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblErrors, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblErrors);
		springLayout.putConstraint(SpringLayout.WEST, lblErrors, OFFSET_X, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblErrors, -OFFSET_X, SpringLayout.EAST, this);

		final JLabel lblCredits = new JLabel(
				"<html><p style='text-align: center;'>Louis Bal dit Sollier<br>Clara Bringer<br>Antonin Décimo<br>Pierre Gervais</p></html>");
		springLayout.putConstraint(SpringLayout.NORTH, lblCredits, OFFSET_Y, SpringLayout.SOUTH, lblErrors);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblCredits, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblCredits);

		springLayout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, getHostTextField());
		springLayout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, lblCredits);
	}

	/**
	 * Returns the host name of the server the user wants to join.
	 * @return the server hostname
	 */
	public String getHost() {
		return getHostTextField().getText();
	}

	/**
	 * Returns the port of the server the user wants to join.
	 * @return the server port
	 */
	public int getPort() {
		return Integer.parseInt(getPortTextField().getText());
	}

	/**
	 * Display an error message.
	 * @param msg the message
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

	/**
	 * Returns the "join" button.
	 * @return the button
	 */
	public JButton getJoinButton() {
		return joinButton;
	}

	/**
	 * Returns the "host" button.
	 * @return the button
	 */
	public JButton getHostButton() {
		return hostButton;
	}

	/**
	 * Returns the host text field.
	 * @return the text field
	 */
	private JTextField getHostTextField() {
		return hostTextField;
	}

	/**
	 * Returns the port text field.
	 * @return the text field
	 */
	private JTextField getPortTextField() {
		return portTextField;
	}
}
