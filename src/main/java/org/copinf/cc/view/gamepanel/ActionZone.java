package org.copinf.cc.view.gamepanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The ActionZone at the bottom of the window, providing widgets such as a text field for the chat,
 * and a "next" button to end a turn.
 */
@SuppressWarnings("serial")
public class ActionZone extends JPanel {

	private final JButton resetButton;
	private final JButton nextButton;

	/**
	 * Constructs a new ActionZone.
	*/
	public ActionZone() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
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

	public void setTime(final int time) {
		if (time == 0) {
			nextButton.setText(Integer.toString(time));
			toggle(false);
		}
	}

	public void toggle(final boolean visible) {
		nextButton.setVisible(visible);
		resetButton.setVisible(visible);
	}
}
