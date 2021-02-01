package messaging.handlers

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.random.Random

class TriggerWordHandler(
    private val triggerWords: Set<String>,
    private val responses: List<String>,
    private val random: Random
): TelegramHandler {
    override fun handle(update: Update, bot: TelegramLongPollingBot) {
        val words = update.message.text.toLowerCase().split(" ")
        if (words.any { triggerWords.contains(it) }) {
            val message = SendMessage.builder()
                .chatId("${update.message.chatId}")
                .text(responses[random.nextInt(0, responses.size)])
                .build()
            bot.execute(message)
        }
    }
}