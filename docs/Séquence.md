Jusqu'a ce qu'il y ait suffisament de joueurs :
- client.lobby.join
- server.lobby.join

Lorsqu'il y a suffisament de joueurs :
- server.game.start
- client.game.players.refresh
- server.game.players.refresh

Quand le dernier client rejoint la partie, le serveur envoie server.game.start, mais  si le client recoit ce message alors qu'il est encore sur le LobbyCOntroller, ce message est perdu.
