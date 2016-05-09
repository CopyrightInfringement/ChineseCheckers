package org.copinf.cc.view.lobbypanel;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The panel where users specify their username.
 */
@SuppressWarnings("serial")
public class UsernamePanel extends JPanel {

	private final JTextField usernameTextField;
	private final JButton submitBtn;

	private final JLabel usernameLbl;

	/**
	 * Create the panel.
	 */
	public UsernamePanel() {
		super(new CardLayout());

		// formPanel
		final JPanel formPanel = new JPanel();
		add(formPanel, "formPanel");

		final JLabel infoLabel = new JLabel("Username :");
		formPanel.add(infoLabel);

		usernameTextField = new JTextField();
		usernameTextField.setColumns(15);
		formPanel.add(usernameTextField);

		submitBtn = new JButton("Submit");
		formPanel.add(submitBtn);

		// userPanel
		final JPanel userPanel = new JPanel();
		add(userPanel, "userPanel");

		usernameLbl = new JLabel("");
		userPanel.add(usernameLbl);
	}

	/**
	 * Switches to a panel with the player's username.
	 * @param username the player's username.
	 */
	public void switchToUsernamePanel(final String username) {
		final CardLayout cl = (CardLayout) getLayout();
		usernameLbl.setText(username);
		cl.show(this, "userPanel");
	}

	public String getUsername() {
		return usernameTextField.getText();
	}

	public void setUsername(final String username) {
		usernameTextField.setText(username);
	}

	public JButton getSubmitBtn() {
		return submitBtn;
	}
}
