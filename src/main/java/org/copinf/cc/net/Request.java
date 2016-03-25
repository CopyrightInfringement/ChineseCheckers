package org.copinf.cc.net;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = 42L;

	private final String identifier;
	private final Serializable content;

	private final transient String[] splitted;

	public Request(final String identifier) {
		this(identifier, null);
	}

	public Request(final String identifier, final Serializable content) {
		this.identifier = identifier;
		this.content = content;
		this.splitted = identifier.split("\\.");
	}

	public boolean isClient() {
		return splitted[0].equals("client");
	}

	public boolean isServer() {
		return splitted[0].equals("server");
	}

	public String getSubRequest(final int level) {
		return splitted[level];
	}

	public int getSubRequestSize(){
		return splitted.length;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Object getContent() {
		return content;
	}

	private Object readResolve() throws java.io.ObjectStreamException {
		return new Request(identifier, content);
	}

	public String toString() {
		return identifier + '\n' + content;
	}
}
