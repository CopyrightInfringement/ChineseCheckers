package org.copinf.cc.view.waitingroompanel;

import org.copinf.cc.net.Message;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;


/**
 * The panel for the chat of the waiting room.
 */
@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements FocusListener {

	private final JList<String> messagesList;
	private final List<String> messages;
	/** The text field in which the user types the message. */
	public final JTextField messageField;
	/** The "Send" button. */
	public final JButton sendButton;

	private static final String DEFAULT_TEXT = "Write your message here";

	private static final int FIELD_WIDTH = 250;
	private static final int FIELD_HEIGHT = 30;

	/**
	 * Constructs a ChatPanel.
	 */
	public ChatPanel() {
		super();

		messages = new ArrayList<>();

		messagesList = new JList<>();
		messageField = new JTextField(DEFAULT_TEXT);
		messageField.addFocusListener(this);
		sendButton = new JButton("Send");

		final JScrollPane scrollPane = new JScrollPane(messagesList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		final SpringLayout layout = new SpringLayout();

		layout.getConstraints(messageField).setHeight(Spring.constant(FIELD_HEIGHT));

		layout.putConstraint(SpringLayout.EAST, this, 2, SpringLayout.EAST, scrollPane);
		layout.putConstraint(SpringLayout.SOUTH, this, 2, SpringLayout.SOUTH, messageField);

		layout.putConstraint(SpringLayout.WEST, scrollPane, 2, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 2, SpringLayout.NORTH, this);

		layout.getConstraints(messageField).setWidth(Spring.constant(FIELD_WIDTH));
		layout.putConstraint(SpringLayout.NORTH, messageField, 2, SpringLayout.SOUTH, scrollPane);
		layout.putConstraint(SpringLayout.WEST, messageField, 2, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.WEST, sendButton, 2, SpringLayout.EAST, messageField);
		layout.putConstraint(SpringLayout.NORTH, sendButton, 0, SpringLayout.NORTH, messageField);
		layout.putConstraint(SpringLayout.SOUTH, sendButton, 0, SpringLayout.SOUTH, messageField);
		layout.putConstraint(SpringLayout.EAST, sendButton, 0, SpringLayout.EAST, scrollPane);

		setLayout(layout);

		updateMessageList();

		add(scrollPane);
		add(messageField);
		add(sendButton);
	}

	/**
	 * Returns the content of the message text field.
	 * @return the message
	 */
	public String getMessage() {
		return messageField.getText();
	}

	/**
	 * Clears the message text field.
	 */
	public void clearField() {
		messageField.setText("");
	}

	@Override
	public void focusGained(final FocusEvent ev) {
		if (messageField.getText().equals(DEFAULT_TEXT)) {
			clearField();
		}
	}

	@Override
	public void focusLost(final FocusEvent ev) {
	}

	/**
	 * Update the message displayed.
	 */
	private void updateMessageList() {
		messagesList.setListData(messages.toArray(new String[messages.size()]));
	}

	/**
	 * Add a message to the chat.
	 * @param message a message
	 */
	public void addMessage(final Message message) {
		messages.add(message.playerName + " : " + message.message);
		updateMessageList();

		repaint();
	}
}
