package messaging.handlers

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class TextTelegramHandler(private val textHandlers: List<TelegramHandler>): ConditionalTelegramHandler {
    override fun accept(update: Update): Boolean = update.message.hasText()

    override fun handle(update: Update, bot: TelegramLongPollingBot) =
        textHandlers.forEach { it.handle(update, bot) }
}