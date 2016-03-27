package org.copinf.cc.view.lobbypanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UsernamePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public UsernamePanel() {
		super();

		final JLabel infoLabel = new JLabel("Username: ");
		add(infoLabel);

		final JTextField usernameTextField = new JTextField();
		usernameTextField.setColumns(15);
		add(usernameTextField);

		final JButton submitBtn = new JButton("Submit");
		add(submitBtn);
	}
}
