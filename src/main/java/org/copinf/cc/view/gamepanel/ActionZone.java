package org.copinf.cc.view.gamepanel;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The ActionZone at the bottom of the window, providing widgets such as a text
 * field for the chat, and a "next" button to end a turn.
 */
@SuppressWarnings("serial")
public class ActionZone extends JPanel implements FocusListener {

	/**
	 * The "Reset" button.
	 */
	public final JButton resetButton;
	/**
	 * The "Next" button.
	 */
	public final JButton nextButton;
	/**
	 * The chat message field.
	 */
	public final JTextField chatField;
	/**
	 * The "Send" button to send a message.
	 */
	public final JButton sendButton;

	private static final String DEFAULT_TEXT = "Write your message here";

	/**
	 * Constructs a new ActionZone.
	 */
	public ActionZone() {
		super();

		chatField = new JTextField(DEFAULT_TEXT);
		chatField.addFocusListener(this);
		sendButton = new JButton("Send");
		resetButton = new JButton("Reset");
		nextButton = new JButton("Next turn");

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		setBorder(new EmptyBorder(0, 2, 2, 2));

		add(chatField);
		add(sendButton);
		add(resetButton);
		add(nextButton);
	}

	/**
	 * Sets the visibility of the buttons and locks/unlocks them.
	 * @param movementSize The size of the current movement.
	 * @param playing Whether the current payer is playing or not.
	 */
	public void setVisibility(int movementSize, boolean playing) {
		nextButton.setVisible(playing);
		resetButton.setVisible(playing);
		nextButton.setEnabled((movementSize > 1) && playing);
		resetButton.setEnabled(movementSize > 0);
	}

	/**
	 * Clears the message field.
	 */
	public void clearField() {
		chatField.setText("");
	}

	/**
	 * Gets the content of the chat message field.
	 */
	public String getMessage() {
		return chatField.getText();
	}

	@Override
	public void focusGained(final FocusEvent ev) {
		if (chatField.getText().equals(DEFAULT_TEXT)) {
			clearField();
		}
	}

	@Override
	public void focusLost(final FocusEvent ev) {
	}
}
