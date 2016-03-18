package org.copinf.cc.net;

import java.util.List;
import java.net.Socket;

public class Server extends Thread {
	private List<GameInfo> waitingGames;
	private List<GameThread> playingGames;
	private List<ClientThread> clients;
	private ServerAcceptThread acceptThread;

	public Server(String address, int port){}

	public void addClient(Socket client){}
	//	If the request is about a game, this method transmits it to
	//	the corresponding GameThread gt through gt.processRequest(client, r)
	private void processRequest(ClientThread client, Request r){}

	@Override
	public void run(){}
}
