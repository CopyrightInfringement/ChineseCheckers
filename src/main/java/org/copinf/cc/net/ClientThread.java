package org.copinf.cc.net;

import java.net.Socket;
import org.copinf.cc.model.Player;

public class ClientThread extends Thread {
	private String username;
	private Socket client;
	private Player player;
	private Server server;
	private GameThread game;

	@Override
	public void run(){}
}
