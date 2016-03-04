package org.copinf.cc.view.gamepanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The ActionZone at the bottom of the window, providing widgets such as a text field for the chat,
 * and a "next" button to end a turn.
 */
@SuppressWarnings("serial")
public class ActionZone extends JPanel {

	private JTextField messageBox;
	private JButton resetButton;
	private JButton nextButton;

	/**
	 * Constructs a new ActionZone.
	*/
	public ActionZone() {
		super();
		BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(layout);
		resetButton = new JButton("Reset");
		add(resetButton);
		nextButton = new JButton("Next turn");
		add(nextButton);
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}
}
