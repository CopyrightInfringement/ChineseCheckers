package org.copinf.cc.net.server;

import org.copinf.cc.net.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * A timer for a turn.
 */
public class GameTimer implements ActionListener {

	private final int initialTime;
	private int remainingTime;
	/** The Timer used by this class. */
	public final Timer timer;
	private ClientThread client;
	private final GameThread game;

	/**
	 * Constructs a new GameTimer object.
	 * @param time The maximum time in seconds for a turn.
	 * @param game The related game thread.
	 */
	public GameTimer(final int time, final GameThread game) {
		timer = new Timer(1000, this);
		initialTime = time;
		this.game = game;
		this.client = null;
	}

	/**
	 * Start a new turn.
	 * @param ct The playing client
	 */
	public void startTurn(final ClientThread ct) {
		client = ct;
		remainingTime = initialTime;
		timer.start();
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
		if (remainingTime == 0) {
			timer.stop();
			game.onNextTurn();
		}

		client.send(new Request("server.game.tick", remainingTime));
		remainingTime--;
	}
}
