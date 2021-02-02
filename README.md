# Telegram Stock Bot [![Build Status](https://travis-ci.org/filipay/TelegramStockBot.svg?branch=master)](https://travis-ci.org/filipay/TelegramStockBot)
This bot allows you to get up to date stock info in your Telegram chat

## Setup
Clone the repo

### Setup configuration 
In the `resources` folder create a `config.json` which should follow this format:
```json
{
  "apiToken": "1234567890:XXXXXXXXXXXXXXXXXX",
  "chatId": "12345678"
}
```
### IntelliJ
If you want to setup this project in IntelliJ simply select
`File -> New -> Project From Exisiting Sources` and select the folder where you've cloned this project.

## Running 
You can run this in several ways:
- through IntelliJ by running in `main` in `App.kt`
- `./gradlew run`
- `java -jar build/libs/TelegramStockBot-1.0-SNAPSHOT.jar`