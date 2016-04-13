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

	private final JButton resetButton;
	private final JButton nextButton;
	private final JTextField chatField;
	private final JButton sendButton;

	/**
	 * Constructs a new ActionZone.
	*/
	public ActionZone() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		chatField = new JTextField("Write your message here");
		add(chatField);
		sendButton = new JButton("Send");
		add(sendButton);
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
	
	public JButton getSendButton() {
		return sendButton;
	}
	
	public String getMessage() {
		return chatField.getText();
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
