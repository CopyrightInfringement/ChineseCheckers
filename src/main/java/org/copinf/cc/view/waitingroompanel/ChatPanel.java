package org.copinf.cc.view.waitingroompanel;

import org.copinf.cc.net.Message;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * The panel for the chat of the waiting room.
 */
@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements FocusListener {

	private final JList<String> messagesList;
	private final List<String> messages;
	/** The text field in which the user types the message. */
	private final JTextField messageField;

	private static final String DEFAULT_TEXT = "Write your message here";

	private static final int OFFSET_X = 10;
	private static final int OFFSET_Y = 10;

	/**
	 * Constructs a ChatPanel.
	 */
	public ChatPanel() {
		super();

		messages = new ArrayList<>();

		messagesList = new JList<>();
		messageField = new JTextField(DEFAULT_TEXT);
		getMessageField().addFocusListener(this);

		final JScrollPane scrollPane = new JScrollPane(messagesList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		final SpringLayout layout = new SpringLayout();
		setLayout(layout);

		layout.putConstraint(SpringLayout.NORTH, scrollPane, OFFSET_Y, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, -OFFSET_Y, SpringLayout.NORTH, messageField);
		layout.putConstraint(SpringLayout.WEST, scrollPane, OFFSET_X, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, -OFFSET_X, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.SOUTH, messageField, -OFFSET_Y, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, messageField, 0, SpringLayout.WEST, scrollPane);
		layout.putConstraint(SpringLayout.EAST, messageField, -OFFSET_X, SpringLayout.EAST, this);

		updateMessageList();

		add(scrollPane);
		add(getMessageField());
	}

	/**
	 * Returns the content of the message text field.
	 * @return the message
	 */
	public String getMessage() {
		return getMessageField().getText();
	}

	/**
	 * Clears the message text field.
	 */
	public void clearField() {
		getMessageField().setText("");
	}

	@Override
	public void focusGained(final FocusEvent ev) {
		if (getMessageField().getText().equals(DEFAULT_TEXT)) {
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
		messages.add(message.getPlayerName() + " : " + message.getMessage());
		updateMessageList();

		repaint();
	}

	/**
	 * Returns the message field.
	 */
	public JTextField getMessageField() {
		return messageField;
	}
}
