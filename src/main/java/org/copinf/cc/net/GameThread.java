package org.copinf.cc.net;

import org.copinf.cc.model.Game;
import java.util.List;

public class GameThread extends Thread{
	GameInfo gameInfo;
	Game game;
	List<ClientThread> clients;

	public GameThread(GameInfo info){}

	public void processRequest(ClientThread client, Request r){}

	@Override
	public void run(){}
}
