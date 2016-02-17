package org.copinf.cc.model;

import java.util.ArrayList;
import java.util.List;
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

	private List<BoardZone> zones;

	public DefaultBoard() {
		super(13,17);
		this.zones = new ArrayList<>();
	}

	/*private void makeBoard(int[][] BOARD_MAP){
		for(int i=0;i<this.BOARD_WIDTH;i++){
			for(int j=0;j<this.BOARD_HEIGHT;j++){
				switch(BOARD_MAP[i][j]){
					case -1:
						this.board[i][j]=null;
						break;
					case 14:
						this.board[i][j] = new Square();

			}
		}
	}
	*/

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
