package org.copinf.cc.net.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.copinf.cc.net.Request;

public class GameTimer implements ActionListener{
	
	public final int initialTime;
	private int remainingTime;
	public final Timer timer;
	private ClientThread client;
	public final GameThread game;

	/**
	 * The maximum time in seconds for a turn
	 */
	public GameTimer(int time, GameThread game) {
		timer = new Timer(1000, this);
		initialTime = time;
		this.game = game;
	}
	
	public void startTurn(ClientThread ct) {
		client = ct;
		remainingTime = initialTime;
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(remainingTime == 0) {
			timer.stop();
			game.onNextTurn();
		}

		client.send(new Request("server.game.tick", remainingTime));
		remainingTime--;
	}
}
