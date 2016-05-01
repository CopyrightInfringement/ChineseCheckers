package org.copinf.cc.view.gamepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

/**
 * The ActionZone at the bottom of the window, providing widgets such as a text field for the chat,
 * and a "next" button to end a turn.
 */
@SuppressWarnings("serial")
public class ActionZone extends JPanel implements FocusListener {

	public final JButton resetButton;
	public final JButton nextButton;
	public final JTextField chatField;
	public final JButton sendButton;
	
	private SpringLayout layout;

	private static final String DEFAULT_TEXT = "Write your message here";

	/**
	 * Constructs a new ActionZone.
	*/
	public ActionZone() {
		super();
		
		layout = new SpringLayout();
		
		setLayout(layout);

		chatField = new JTextField(DEFAULT_TEXT);
		chatField.addFocusListener(this);
		sendButton = new JButton("Send");
		resetButton = new JButton("Reset");
		nextButton = new JButton("Next turn");
		
		layout.putConstraint(SpringLayout.WEST, chatField, 2, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, chatField, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 2, SpringLayout.SOUTH, chatField);
		
		layout.putConstraint(SpringLayout.NORTH, sendButton, 0, SpringLayout.NORTH, chatField);
		layout.putConstraint(SpringLayout.SOUTH, sendButton, 0, SpringLayout.SOUTH, chatField);
		layout.putConstraint(SpringLayout.WEST, sendButton, 2, SpringLayout.EAST, chatField);
		
		add(chatField);
		add(sendButton);
		add(resetButton);
		add(nextButton);
	}
	
	public void setVisibility(int movementSize, boolean playing) {
		nextButton.setVisible(playing);
		resetButton.setVisible(playing);
		nextButton.setEnabled(movementSize > 1 && playing);
		resetButton.setEnabled(movementSize > 0);
		
		if(playing) {
			layout.putConstraint(SpringLayout.NORTH, resetButton, 0, SpringLayout.NORTH, sendButton);
			layout.putConstraint(SpringLayout.SOUTH, resetButton, 0, SpringLayout.SOUTH, sendButton);
			layout.putConstraint(SpringLayout.WEST, resetButton, 2, SpringLayout.EAST, sendButton);
			
			layout.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, resetButton);
			layout.putConstraint(SpringLayout.SOUTH, nextButton, 0, SpringLayout.SOUTH, resetButton);
			layout.putConstraint(SpringLayout.WEST, nextButton, 2, SpringLayout.EAST, resetButton);	
			
			layout.putConstraint(SpringLayout.EAST, this, 2, SpringLayout.EAST, nextButton);
		} else {
			layout.putConstraint(SpringLayout.EAST, this, 2, SpringLayout.EAST, sendButton);
		}
	}

	public void clearField() {
		chatField.setText("");
	}

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
