package org.copinf.cc.view.homepanel;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * HomePanel is what is displayed when the program is launched.
 */
@SuppressWarnings("serial")
public class HomePanel extends JPanel {

	/**
	 * Constructs a new HomePanel.
	 */
	public HomePanel() {
		super();

		// UI
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JPanel hostPanel = new JPanel();
		hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.PAGE_AXIS));
		add(hostPanel);
		hostPanel.add(new JLabel("Host"));

		JPanel joinPanel = new JPanel();
		joinPanel.setLayout(new BoxLayout(joinPanel, BoxLayout.PAGE_AXIS));
		add(joinPanel);
		joinPanel.add(new JLabel("Join"));
	}
}
