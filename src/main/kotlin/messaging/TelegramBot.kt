package messaging

import configuration.Config
import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.BotOptions
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.util.WebhookUtils

class TelegramBot(
    private val config: Config,
    private val telegramHandlers: List<TelegramHandler>,
    private val messenger: TelegramBotMessenger
) : LongPollingBot {
    override fun getBotToken(): String = config.apiToken

    override fun getBotUsername(): String = "WSBot"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            telegramHandlers.forEach { it.handle(update) }
        }
    }

    override fun getOptions(): BotOptions = DefaultBotOptions()

    override fun clearWebhook() = WebhookUtils.clearWebhook(messenger)
}