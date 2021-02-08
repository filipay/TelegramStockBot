package messaging.handlers.commands

import messaging.handlers.ConditionalTelegramHandler
import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.meta.api.objects.Update

class CommandTelegramHandler(private val handlerStrategy: Map<String, TelegramHandler>): ConditionalTelegramHandler {
    override fun accept(update: Update): Boolean = update.message.isCommand

    override fun handle(update: Update) {
        val command = update.message.text.split("\\s".toRegex()).first()
        handlerStrategy[command]?.handle(update)
    }
}