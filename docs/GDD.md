# Dames Chinoises

## Client-Serveur
Nous allons développer un programme suivant l'architecture clients-serveur.  
Nous fournirons un exécutable client qui contiendra aussi un serveur inclus pour jouer en localhost ainsi qu'un exécutable serveur pour créer un serveur dédié.  
Nous définirons un protocole de communication entre les clients et les serveurs qui pourrait être utilisé par une autre implémentation que la notre.

## Client
Le client s'ouvrira sur une fenêtre d'accueil présentant différentes options:
- Rejoindre un serveur. L'utilisateur entrera une adresse IP/URL vers un serveur et le rejoindra si le serveur l'accepte.
- Héberger un serveur. Celui-ci sera alors accessible en LAN. L'utilisateur se verra proposer des options de configuration de la partie.
Une fois qu'un serveur a été rejoint, la partie commence.

## Options de partie
Ces options peuvent être choisies lors de la création de parties:
- nombre de joueurs 2, 3, 4, 6
- type/taille de plateau
- sélection de l'IA:
    * complêter un plateau, jouer contre l'IA, IA vs IA, suggestion de mouvement, difficulté
- enregistrer/sauvegarder parties (pattern command)
- tchat, commandes dans le chat

[![GUI](dessin.png)](GUI)
