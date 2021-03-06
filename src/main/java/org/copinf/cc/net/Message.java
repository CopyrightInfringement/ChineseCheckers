package org.copinf.cc.net;

import java.io.Serializable;

/**
 * Messages players exchange in the chat.
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 42L;

	/** The content of this message. */
	private final String message;
	/** The name of the sender. */
	private final String playerName;
	/**
	 * true if this message was sent by a player or is an information message.
	 */
	private final boolean isChatMessage;

	/**
	 * Constructs a new chat Message.
	 * @param message The content of this message
	 * @param playerName The name of the sender.
	 */
	public Message(final String message, final String playerName) {
		this(message, playerName, true);
	}

	/**
	 * Constructs a new Message.
	 * @param message The content of this message
	 * @param playerName The name of the sender.
	 * @param isChatMessage If this message was sent by a player or is an
	 *            information message.
	 */
	public Message(final String message, final String playerName, final boolean isChatMessage) {
		this.message = message;
		this.playerName = playerName;
		this.isChatMessage = isChatMessage;
	}

	@Override
	public String toString() {
		return getPlayerName() + " : " + "\"" + getMessage() + "\"";
	}

	/**
	 * Returns the content of this message.
	 * @return the content of this message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the name of the sender.
	 * @return the name of the sender
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Indicates whether this message was sent by a player or not.
	 * @return true if this message was sent by a player
	 */
	public boolean isChatMessage() {
		return isChatMessage;
	}
}
