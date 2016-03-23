package org.copinf.cc.view.lobbypanel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UsernamePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public UsernamePanel() {
		super();

		JLabel infoLabel = new JLabel("Username: ");
		add(infoLabel);

		JTextField usernameTextField = new JTextField();
		add(usernameTextField);

		JButton submitBtn = new JButton("Submit");
		add(submitBtn);
	}
}
