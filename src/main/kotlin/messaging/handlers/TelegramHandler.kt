package messaging.handlers

import org.telegram.telegrambots.meta.api.objects.Update

interface TelegramHandler {
    fun handle(update: Update)
}

interface ConditionalTelegramHandler: TelegramHandler {
    fun accept(update: Update): Boolean = true
}

