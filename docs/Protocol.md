# Protocole

## Format des requêtes

```java
class Request {
	private String identifier;
	private Object content;
}
```

## Liste des types de requête

### Lobby

- [x] `client.lobby.username`, `String`
  Envoie la demande de pseudonyme d'un joueur.

- [x] `server.lobby.username`, `Boolean`
  Réponse à la demande de pseudonyme.

- [x] `client.lobby.refresh`, `null`
  Demande la liste des parties du serveur.

- [x] `server.lobby.refresh`, `Set<GameInfo>`
  Renvoie la liste des parties du serveur.

- [ ] `client.lobby.quit`, `null`
  Quitte la partie que le client avait rejoint.

- [ ] `client.lobby.disconnect`, `null`
  Déconnexion.

- [x] `client.lobby.create`, `GameInfo`
  Crée une partie.

- [x] `server.lobby.create`, `Boolean`
  Renvoie `true` si la partie a bien été créée, sinon `false`.

- [ ] *`client.lobby.message`*, `String`
  Envoie un message sur le chat principal.

- [x] `client.lobby.join`, `GameInfo`
  Rejoindre une partie.

- [x] `server.lobby.join`, `Boolean`
  Contient `true` si la partie a bien été rejointe, sinon `false`.

- [x] `server.game.start`, `null`
  Débuter la partie (lancement du processus de répartition).

### Game
#### Préparation de la partie
- [x] `client.game.players.refresh`, `null`
  Demande une mise à jour de la liste de joueurs.

- [x] `server.game.players.refresh`, `List<String>`
  Envoie une mise à jour de la liste de joueurs.

- [ ] `client.game.teams.refresh`, `null`
  Demande une mise à jour de la liste des équipes.

- [ ] `server.game.teams.refresh`, `List<List<String>>`
  Envoie une mise à jour de la liste des équipes.

- [ ] `server.game.teams.leader`, `null`
  Indique au client qu'il est chef d'équipe et qu'il doit choisir ses équipiers.

- [ ] `client.game.teams.leader`, `List<String>`
  Communique au serveur ses équipiers.

- [ ] `server.game.begin`, `null`
  Débuter la partie.

#### Partie
- [ ] `client.game.move.request`, `Movement`
  Envoie un déplacement.

- [ ] `server.game.move.request`, `Boolean`
  Acccepte ou rejette un déplacement.

- [ ] `server.game.move`, `Movement`
  Notifie les joueurs d'un déplacement.

- [ ] `server.game.next`, `null`
  Fait passer au joueur suivant.

- [ ] `server.game.end`, `Integer`
  Indique que la partie est finie en spécifiant l'identifiant de l'équipe gagnante.

- [ ] `client.game.quit`
  Indique qu'un joueur a quitté la partie. Il est remplacé par une IA. *(NB : s'il ne reste plus que des IA, la partie s'arrête.)*

#### Chat
- [ ] `client.game.message`, `String`
  Envoie un message.

- [ ] `server.game.message`, `String`
  Fait passer le message sous le format `[<Nom du joueur>]<Contenu du message>`.
