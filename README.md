# ChineseCheckers
[![License](http://img.shields.io/badge/License-MIT-brightgreen.svg)](LICENSE)
[![Build Status](https://travis-ci.org/CopyrightInfringement/ChineseCheckers.svg?branch=master)](https://travis-ci.org/CopyrightInfringement/ChineseCheckers)

A [Chinese checkers](https://en.wikipedia.org/wiki/Chinese_checkers) game, written in Java 8.

## TODO !

- [ ] Add a `toString()` to `Message`

### Game panel
- [ ] Implement the timer
- [ ] Prettier GUI
- [x] Display the main player at the bottom of the screen
- Improve the chat
    - [x] Clear the message bar after sending a message
    - [x] Send a message after pressing `Enter`
    - [x] Display more messages in the game Chat
    - [ ] Add emojis
- [x] Disable the `Reset` button if the movement is empty
- [ ] Display the movement suggestions

### Lobby
- [ ] Check that a submitted username isn't empty before validating it
- [ ] Go back to home-screen when disconnected from the server

### Waiting room
- Improve the chat
    - [ ] Clear the message bar after sending a message
    - [ ] Send a message after pressing `Enter`
- [ ] Not remove a game if a player leaves the waiting room
- [ ] Update the list of players when one of them disconnects

### Bugs to fix
- A player can join a game that is already full

## Build & run
This project uses [Gradle](https://gradle.org/).  

If you are on windows, use `gradlew.bat`. If you're on a \*nix, use `gradlew`.
You can use theses tasks `build`, `check`, `test`, `javadoc`. Get help with `gradlew help`.
Use `run` to run the project. Enabling the `--daemon` option of gradlew will speed up the build process.

You can also use the Makefile, but it's less efficient.

Run the server in standalone mode : `make server` (temporary)
