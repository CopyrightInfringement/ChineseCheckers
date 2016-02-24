package org.copinf.cc.view.gamepanel;

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
	}

	/**
	 * Toggle the state of the "next" button.
	 */
	public void toggleNextButton() {

	}
}
