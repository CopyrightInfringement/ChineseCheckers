package org.copinf.cc.view.waitingroompanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class ChatPanel extends JPanel {
	private JList<String> messagesList;
	public final JTextField messageField;
	public final JButton sendButton;
	private JScrollPane scrollPane;
	private List<String> messages;
	
	private final static int BUTTON_WIDTH = 60;
	private final static int FIELD_HEIGHT = 30;
	
	public ChatPanel(int width, int height) {
		setPreferredSize(new Dimension(width, height));

		messages = new ArrayList<>();
		
		messagesList = new JList<>();
		messageField = new JTextField("Saisissez un message");
		sendButton = new JButton("Send");

		scrollPane = new JScrollPane(messagesList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane.setPreferredSize(new Dimension(width, height - FIELD_HEIGHT));
		messageField.setPreferredSize(new Dimension(width - BUTTON_WIDTH, FIELD_HEIGHT));
		sendButton.setPreferredSize(new Dimension(BUTTON_WIDTH, FIELD_HEIGHT));

		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, messageField, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, messageField, 0, SpringLayout.SOUTH, scrollPane);
		
		layout.putConstraint(SpringLayout.WEST, sendButton, 0, SpringLayout.EAST, messageField);
		layout.putConstraint(SpringLayout.NORTH, sendButton, 0, SpringLayout.SOUTH, scrollPane);
		
		setLayout(layout);

		updateMessageList();
		
		add(scrollPane);
		add(messageField);
		add(sendButton);
	}
	
	/**
	 * Update the message displayed
	 */
	private void updateMessageList() {
		messagesList.setListData(messages.toArray(new String[messages.size()]));
	}
}
