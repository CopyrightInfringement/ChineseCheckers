package org.copinf.cc.net;

public class Request {

	private String identifier;
	private Object content;

	public Request(final String identifier) {
		this(identifier, null);
	}

	public Request(final String identifier, final Object content) {
		this.identifier = identifier;
		this.content = content;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Object getContent() {
		return content;
	}
}
