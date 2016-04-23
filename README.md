# ChineseCheckers
[![License](http://img.shields.io/badge/License-MIT-brightgreen.svg)](LICENSE)
[![Build Status](https://travis-ci.org/CopyrightInfringement/ChineseCheckers.svg?branch=master)](https://travis-ci.org/CopyrightInfringement/ChineseCheckers)

A [Chinese checkers](https://en.wikipedia.org/wiki/Chinese_checkers) game, written in Java 8.

## TODO !
### Game panel
- Implement the timer
- Prettier GUI
- Display the main player at the bottom of the screen
- Improve the chat
    - Clear the message bar after sending a message
    - Send a message after pressing `Enter`
    - Display more messages in the game Chat
    - Add emojis

### Lobby
- Check that a submitted username isn't empty before    validating it
- Go back to home-screen when disconnected from the server

#### Waiting room
- Chat for the waiting room
- Not remove a game if a player leaves the waiting room
- Disable `Reset` when currentMovement is empty

## Build & run
This project uses [Gradle](https://gradle.org/).  

If you are on windows, use `gradlew.bat`. If you're on a \*nix, use `gradlew`.
You can use theses tasks `build`, `check`, `test`, `javadoc`. Get help with `gradlew help`.
Use `run` to run the project. Enabling the `--daemon` option of gradlew will speed up the build process.

You can also use the Makefile, but it's less efficient.

Run the server in standalone mode : `make server` (temporary)
