package messaging

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class TelegramBot : TelegramLongPollingBot() {
    private val paperHands = setOf("sell","sold","thinking","selling")
    override fun getBotToken(): String = BotConfig.API_TOKEN

    override fun getBotUsername(): String = "tradeMaster"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val words = update.message.text.toLowerCase().split(" ")
            if (words.any { paperHands.contains(it) }) {
                val message = SendMessage.builder()
                    .chatId("${update.message.chatId}")
                    .text("\uD83E\uDDFB\uD83D\uDC50")
                    .build()
                execute(message)
            }
        }
    }
}