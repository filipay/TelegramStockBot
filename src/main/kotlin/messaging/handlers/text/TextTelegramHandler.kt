package messaging.handlers.text

import messaging.handlers.ConditionalTelegramHandler
import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.meta.api.objects.Update

class TextTelegramHandler(private val textHandlers: List<TelegramHandler>): ConditionalTelegramHandler {
    override fun accept(update: Update): Boolean = update.message.hasText() && !update.message.isCommand

    override fun handle(update: Update) = textHandlers.forEach { it.handle(update) }
}