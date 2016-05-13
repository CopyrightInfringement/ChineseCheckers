package org.copinf.cc.view.gamepanel;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

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

	private static final int OFFSET_X = 10;

	private static final String DEFAULT_TEXT = "Write your message here";

	/**
	 * Constructs a new ActionZone.
	 */
	public ActionZone() {
		super();

		chatField = new JTextField(DEFAULT_TEXT);
		getChatField().addFocusListener(this);
		resetButton = new JButton("Reset");
		nextButton = new JButton("Next turn");

		final SpringLayout layout = new SpringLayout();
		setLayout(layout);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, chatField, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, chatField, OFFSET_X, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, chatField, -OFFSET_X, SpringLayout.WEST, nextButton);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, nextButton, 0, SpringLayout.VERTICAL_CENTER, chatField);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nextButton, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, resetButton, 0, SpringLayout.VERTICAL_CENTER, nextButton);
		layout.putConstraint(SpringLayout.WEST, resetButton, OFFSET_X, SpringLayout.EAST, nextButton);

		add(getChatField());
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

	/**
	 * Returns the "Reset" button.
	 * @return the reset button
	 */
	public JButton getResetButton() {
		return resetButton;
	}

	/**
	 * Returns the "Next turn" button.
	 * @return the next button
	 */
	public JButton getNextButton() {
		return nextButton;
	}

	/**
	 * Returns the message field.
	 * @return the message field
	 */
	public JTextField getChatField() {
		return chatField;
	}
}
