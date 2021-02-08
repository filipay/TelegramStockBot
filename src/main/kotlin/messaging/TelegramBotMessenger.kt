package messaging

import configuration.Config
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.DefaultBotOptions

class TelegramBotMessenger(
    private val config: Config
): DefaultAbsSender(DefaultBotOptions()) {
    override fun getBotToken(): String = config.apiToken

    fun getChatId() = config.chatId
}