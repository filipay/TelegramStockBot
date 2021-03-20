# Telegram Stock Bot [![Build Status](https://travis-ci.org/filipay/TelegramStockBot.svg?branch=master)](https://travis-ci.org/filipay/TelegramStockBot)
This bot allows you to get up to date exchanges info in your Telegram chat

## Setup
Clone the repo

### Setup configuration 
The easiest way to set up this project is to use **Docker**.

Start by creating an `.env` file in the root dir of the project.
Provide the following values in the `.env` file:

```shell
ARCH=amd64
API_TOKEN=your_bot_api_token
CHAT_ID=your_chat_id
INFLUXDB_TOKEN=your_chosen_token
INFLUXDB_ORG_NAME=your_chosen_org_name
INFLUXDB_BUCKET_NAME=your_chosen_bucket_name
INFLUXDB_USERNAME=your_chosen_username
INFLUXDB_PASSWORD=your_chosen_passowrd
```

Once you filled in/chosen the values you want, you're ready to start your docker environment.

### IntelliJ
If you want to setup this project in IntelliJ simply select
`File -> New -> Project From Exisiting Sources` and select the folder where you've cloned this project.

## Running 
You can run this in several ways:
- **RECOMMENDED**: Run `docker-compose up` to pull the necessary images and start the application.

The following methods work, however, the application will not be able to publish price data to InfluxDB.
For each of these methods, make sure you have the necessary environment variables defined.
- through IntelliJ by running in `main` in `App.kt`
- `./gradlew run` 
- `java -jar build/libs/TelegramStockBot-1.0-SNAPSHOT.jar`