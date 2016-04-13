package org.copinf.cc.net;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	public final String message;
	public final String playerName;
	public final boolean isChatMessage;

	public Message(final String message, final String playerName, final boolean isChatMessage) {
		this.message = message;
		this.playerName = playerName;
		this.isChatMessage = isChatMessage;
	}
	
	public Message(final String message, final String playerName) {
		this(message, playerName, true);
	}
}
