# Chinese Checkers
[![License](http://img.shields.io/badge/License-MIT-brightgreen.svg)](LICENSE)
[![Build Status](https://travis-ci.org/CopyrightInfringement/ChineseCheckers.svg?branch=master)](https://travis-ci.org/CopyrightInfringement/ChineseCheckers)

A [Chinese checkers][wiki_cc] game, written in Java 8.

## Build & run
This project uses [Gradle][gradle] as its build automation system. You can build the project with the `./gradlew` command on UN\*X or with `gradle.bat` on Windows.

Gradle dependencies may take a while to be downloaded so you can simply run `make` to build & run the game.

### With Gradle

```sh
./gradlew --daemon run # builds & runs the game
./gradlew --daemon build # builds, checks, tests and distributes the game
./gradlew --daemon javadoc # generates the javadoc in build/docs/javadoc
```

Gradle uses the `build` directory to store the compiled classes, documentation and release archives.

### Execute a release
You can create a release with `./gradlew --daemon installDist` and the release will be available in `build/install/Chinese_checkers`.

```
Chinese_checkers
├── bin
│   ├── ChineseCheckers        # Bash script
│   └── ChineseCheckers.bat    # Batch script
└── lib
    └── ChineseCheckers-0.0.1.jar
```

```
Usage: ChineseCheckers [-h] [--help] [--version]

Options:
   --version             Show ChineseCheckers version and exit.
   -h, --help            Show this help message and exit.

   -s <port>,            Launch the game in standalone server mode.
      --server <port>    Default port is 25565.
```

[wiki_cc]: https://en.wikipedia.org/wiki/Chinese_checkers
[gradle]: https://gradle.org/
