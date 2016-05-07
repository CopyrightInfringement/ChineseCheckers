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

	/** The "Reset" button. */
	private final JButton resetButton;
	/** The "Next" button. */
	private final JButton nextButton;
	/** The chat message field. */
	private final JTextField chatField;
	/** The "Send" button to send a message. */
	private final JButton sendButton;

	private static final String DEFAULT_TEXT = "Write your message here";

	/**
	 * Constructs a new ActionZone.
	 */
	public ActionZone() {
		super();

		chatField = new JTextField(DEFAULT_TEXT);
		getChatField().addFocusListener(this);
		sendButton = new JButton("Send");
		resetButton = new JButton("Reset");
		nextButton = new JButton("Next turn");

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		setBorder(new EmptyBorder(0, 2, 2, 2));

		add(getChatField());
		add(getSendButton());
		add(getResetButton());
		add(getNextButton());
	}

	/**
	 * Sets the visibility of the buttons and locks/unlocks them.
	 * @param movementSize The size of the current movement.
	 * @param playing Whether the current payer is playing or not.
	 */
	public void setVisibility(final int movementSize, final boolean playing) {
		getNextButton().setVisible(playing);
		getResetButton().setVisible(playing);
		getNextButton().setEnabled(movementSize > 1 && playing);
		getResetButton().setEnabled(movementSize > 0);
	}

	/**
	 * Clears the message field.
	 */
	public void clearField() {
		getChatField().setText("");
	}

	/**
	 * Gets the content of the chat message field.
	 * @return the message
	 */
	public String getMessage() {
		return getChatField().getText();
	}

	@Override
	public void focusGained(final FocusEvent ev) {
		if (getChatField().getText().equals(DEFAULT_TEXT)) {
			clearField();
		}
	}

	@Override
	public void focusLost(final FocusEvent ev) {
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JTextField getChatField() {
		return chatField;
	}

	public JButton getSendButton() {
		return sendButton;
	}
}
