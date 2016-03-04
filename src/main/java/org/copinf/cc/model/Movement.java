package org.copinf.cc.model;

import java.util.Stack;

@SuppressWarnings("serial")
public class Movement extends Stack<Coordinates> {

	public Movement() {
		super();
	}

	public Movement(final Coordinates coord) {
		super();
		push(coord);
	}

	public Movement(final Coordinates orig, final Coordinates dest) {
		super();
		push(orig);
		push(dest);
	}

	@Override
	public Coordinates push(final Coordinates item) {
		int index = search(item);
		if (index != -1) {
			removeRange(index - 1, size());
		}
		return !empty() && peek().equals(item) ? null : super.push(item);
	}

	public Coordinates getOrigin() {
		return get(0);
	}

	public Coordinates getDestination() {
		return peek();
	}

	public Movement getLastUnit() {
		if (size() == 1) {
			return new Movement(peek(), peek());
		}
		return new Movement(get(size() - 2), peek());
	}

	public Movement getCondensed() {
		return new Movement(getOrigin(), getDestination());
	}

	public Movement getReversedCondensed() {
		return new Movement(getDestination(), getOrigin());
	}
}
