package messaging.handlers

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

interface TelegramHandler {
    fun handle(update: Update, bot: TelegramLongPollingBot)
}

interface ConditionalTelegramHandler: TelegramHandler {
    fun accept(update: Update): Boolean
}