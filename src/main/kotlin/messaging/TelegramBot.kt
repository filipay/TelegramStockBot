package messaging

import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class TelegramBot(private val telegramHandlers: List<TelegramHandler> ) : TelegramLongPollingBot() {
    override fun getBotToken(): String = BotConfig.API_TOKEN

    override fun getBotUsername(): String = "WSBot"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            telegramHandlers.forEach { it.handle(update, this) }
        }
    }
}