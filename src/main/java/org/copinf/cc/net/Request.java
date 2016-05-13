package org.copinf.cc.net;

import java.io.Serializable;

/**
 * A message sent through the network, from a client to the server, or the other
 * way around. It contains an identifier string describing the specific nature
 * of the request, and an object which may contain additional informations. The
 * identifier is under the format "A.B.C.D" describing a tree like hierarchy of
 * requests and sub-requests.
 */

public class Request implements Serializable {

	private static final long serialVersionUID = 42L;

	private final String identifier;
	private final Serializable content;

	private final transient String[] splitted;

	/**
	 * @param identifier The identifier of the request.
	 */
	public Request(final String identifier) {
		this(identifier, null);
	}

	/**
	 * @param identifier The identifier of the request.
	 * @param content The content of the request.
	 */
	public Request(final String identifier, final Serializable content) {
		this.identifier = identifier;
		this.content = content;
		this.splitted = identifier.split("\\.");
	}

	/**
	 * Gets the sub-request at a given 0-based level.
	 * @param level The level of the sub-request.
	 * @return The sub-request.
	 */
	public String getSubRequest(final int level) {
		if (level < splitted.length) {
			return splitted[level];
		} else {
			return null;
		}
	}

	/**
	 * Gets the number of sub-requests.
	 * @return the number of sub-requests.
	 */
	public int getSubRequestSize() {
		return splitted.length;
	}

	/**
	 * Reconstructs the Request.
	 * @return the reconstructed request.
	 * @throws java.io.ObjectStreamException if something wrong happens
	 */
	private Object readResolve() throws java.io.ObjectStreamException {
		return new Request(getIdentifier(), getContent());
	}

	@Override
	public String toString() {
		return getIdentifier() + '\n' + getContent();
	}

	/**
	 * Returns the identifier of the request.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Returns the content of this request.
	 */
	public Serializable getContent() {
		return content;
	}
}
