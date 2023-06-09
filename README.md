# SETU Songs App [![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

SETU Songs App is a powerful and user-friendly song-tracking application built using Kotlin. This app allows you to manage your songs with ease and offers advanced features for organizing and searching through your songs.

_(Project for SETU Software Development Tools module.)_

> _"It is a revolutionary app for managing songs, I have never seen anything like it. I would say it stands among the giants in the industry such as Samsung Songs and Apple Songs."_ - Piotr Placzek (Creator and CEO)

## Features

- [x] CRUD
    - [x] (C) Add songs/artists
    - [x] (R) View songs/artists
        - [x] List songs/artists
    - [x] (U) Update songs/artists
    - [x] (D) Remove songs/artists
- [x] Advanced features
    - [x] Search songs/artists
    - [x] Sort songs/artists
    - [x] Filter songs/artists
    - [x] *Save songs/artists to file
    - [x] *Load songs/artists from file
- [x] Nice table-based UI courtesy of [Picnic](https://github.com/JakeWharton/picnic)

&ast; _Implemented in XML, JSON, and YAML_

## Run Locally

Clone the project

```bash
  git clone https://github.com/piotrpdev/songs-app
```

Then you can do the following:

- Run the `MainKt` or `MainKt (Debug)` run script in IntelliJ IDEA

> **Note**: Run with `-Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG` or `MainKt (Debug)` to see debug logs

> **Warning**: If not running one of the provided scripts, you need to manually use the `--add-opens=java.base/java.util=ALL-UNNAMED` flag for XStream to work.

## Authors

- [@piotrpdev](https://www.github.com/piotrpdev)

Code and some instructions taken from:
- [@sdrohan](https://github.com/sdrohan)
- [StackOverflow (See comments)](https://stackoverflow.com/)


## License

[MIT](https://choosealicense.com/licenses/mit/)



