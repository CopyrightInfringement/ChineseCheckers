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
- `client.lobby.username`, `String`  
  Envoie la demande de pseudonyme d'un joueur.

- `server.lobby.username`, `Boolean`  
  Réponse à la demande de pseudonyme

- `client.lobby.refresh`, `null`  
  Demande la liste des parties du serveur.

- `server.lobby.refresh`, `List<GameInfo>`  
  Renvoie la liste des parties du serveur.

- `client.lobby.quit`, `null`  
  Quitte la partie que le client avait rejoint.

- `client.lobby.create`, `GameInfo`  
  Crée une partie.

- `server.lobby.create`, `Boolean`  
  Renvoie `true` si la partie a bien été créée, sinon `false`.

- *`client.lobby.message`*, `String`  
  Envoie un message sur le chat principal.

- `client.lobby.join`, `GameInfo`  
  Rejoindre une partie.

- `server.lobby.join`, `Boolean`  
  Renvoie `true` si la partie a bien été rejointe, sinon `false`.
