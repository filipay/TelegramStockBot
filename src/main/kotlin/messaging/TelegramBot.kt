package messaging

import configuration.Config
import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class TelegramBot(
    private val config: Config,
    private val telegramHandlers: List<TelegramHandler>
) : TelegramLongPollingBot() {
    override fun getBotToken(): String = config.apiToken

    override fun getBotUsername(): String = "WSBot"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            telegramHandlers.forEach { it.handle(update, this) }
        }
    }
}