package org.copinf.cc.view.gamepanel;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The ActionZone at the bottom of the window, providing widgets such as a text field for the chat,
 * and a "next" button to end a turn.
 */
@SuppressWarnings("serial")
public class ActionZone extends JPanel implements FocusListener {

	private final JButton resetButton;
	private final JButton nextButton;
	private final JTextField chatField;
	private final JButton sendButton;

	private static final String DEFAULT_TEXT = "Write your message here";

	/**
	 * Constructs a new ActionZone.
	*/
	public ActionZone() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		chatField = new JTextField(DEFAULT_TEXT);
		chatField.addFocusListener(this);
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
		nextButton.setEnabled(visible);
		resetButton.setEnabled(visible);
	}

	@Override
	public void focusGained(final FocusEvent ev) {
		if (chatField.getText().equals(DEFAULT_TEXT)) {
			chatField.setText("");
		}
	}

	@Override
	public void focusLost(final FocusEvent ev) {
	}
}
