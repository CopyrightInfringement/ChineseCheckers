package org.copinf.cc.model;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DefaultBoard extends AbstractBoard {

	/*
	private final static int[] BOARD_MAP = {
		-1, -1, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, -1, 14, 14, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, 14, 14, 14, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, 14, 14, 14, 14, -1, -1, -1, -1, -1,
		63, 63, 63, 63,  0,  0,  0,  0,  0, 25, 25, 25, 25,
		  63, 63, 63,  0,  0,  0,  0,  0,  0, 25, 25, 25, -1,
		-1, 63, 63,  0,  0,  0,  0,  0,  0,  0, 25, 25, -1,
		  -1, 63,  0,  0,  0,  0,  0,  0,  0,  0, 25, -1, -1,
		-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1,
		  -1, 52,  0,  0,  0,  0,  0,  0,  0,  0, 36, -1, -1,
		-1, 52, 52,  0,  0,  0,  0,  0,  0,  0, 36, 36, -1,
		  52, 52, 52,  0,  0,  0,  0,  0,  0, 36, 36, 36, -1,
		52, 52, 52, 52,  0,  0,  0,  0,  0, 36, 36, 36, 36,
		  -1, -1, -1, -1, 41, 41, 41, 41, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, 41, 41, 41, -1, -1, -1, -1, -1,
		  -1, -1, -1, -1, -1, 41, 41, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1
	};
	*/

	private static final int[][] BOARD_MAP = {
		{-1, -1, -1, -1, 63, 63, -1, -1, -1, -1, -1, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 63, 63, 63, -1, 52, 52, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 63, 63, 0, 0, 0, 52, 52, 52, -1, -1, -1, -1},
		{-1, -1, -1, -1, 63, 0, 0, 0, 0, 0, 0, 0, 52, -1, -1, -1, -1},
		{-1, -1, -1, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, -1, -1, -1},
		{-1, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, 41, -1},
		{14, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, 41, 41},
		{-1, -1, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 41, -1, -1},
		{-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 0, 0, 0, 0, 0, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 25, 25, 0, 36, 36, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, 25, 25, -1, -1, -1, 36, 36, 36, -1, -1, -1, -1},
		{-1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1}
	};

	private static final int BOARD_WIDTH = 13;
	private static final int BOARD_HEIGHT = 17;

	private static Square[][] makeBoard() {
		return new Square[BOARD_WIDTH][BOARD_HEIGHT];
	}

	public DefaultBoard() {
		super(makeBoard());
		throw new UnsupportedOperationException();
	}

	@Override
	public SortedSet<Integer> getPossiblePlayerNumbers() {
		final TreeSet<Integer> ts = new TreeSet<>();
		ts.add(2);
		ts.add(3);
		ts.add(4);
		ts.add(6);
		return ts;
	}

	@Override
	public void dispatchZones(final Set<Team> teams) {
		throw new UnsupportedOperationException();
	}
}
