package org.copinf.cc.net;

import java.io.Serializable;

/**
 * Messages players exchange in the chat.
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 42L;

	public final String message;
	public final String playerName;
	public final boolean isChatMessage;

	/**
	 * Constructs a new Message.
	* @param message The content of the message
	* @param playerName The name of the sender.
	*/
	public Message(final String message, final String playerName) {
		this(message, playerName, true);
	}

	/**
	 * Constructs a new Message.
	 * @param message The content of the message
	 * @param playerName The name of the sender.
	 * @param isChatMessage If this message was sent by a player or is an information message.
	 */
	public Message(final String message, final String playerName, final boolean isChatMessage) {
		this.message = message;
		this.playerName = playerName;
		this.isChatMessage = isChatMessage;
	}
}
