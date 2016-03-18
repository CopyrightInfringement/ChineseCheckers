On part avec un serveur, dont le thread principal s'occupe de gérer le lobby. Un second thread accepte les connexions réseau et crée des nouveaux threads pour chaque socket qu'il ajoute dans la liste des threads clients du thread principal.
Lorsqu'un client crée une partie, un thread de partie est créé. Il est ajouté à la liste de threads de parties du serveur. Ce thread de partie contient un objet `GameInfo` et une liste de threads de clients.
Lorsqu'un client demande à rejoindre une partie, on ajoute son thread à la liste de threads clients de la partie en question.
Quand le nombre de threads clients d'une partie a atteint le nombre de joueurs de la partie, la partie commence. Un objet `Game` est créé à partir du `GameInfo`.



```java
public class Server {
	List<GameInfo> waitingGames;
	List<GameThread> playingGames;
	List<ClientThread> clients;

	void addClient(Socket client);
	void processRequest(ClientThread client, Request r);
}

public class ServerAcceptThread {
	ServerSocket serverSocket;
	Server server;
}

public class GameThread {
	GameInfo gameInfo;
	Game game;
	List<ClientThread> clients;

	void processRequest(ClientThread client, Request r);
}

public class ClientThread {
	String username;
	Socket client;
	Player player;
	Server server;
	GameThread game;
}
```

**Attention à ne pas oublier à fermer une socket après que le client se soit déconnecté**
