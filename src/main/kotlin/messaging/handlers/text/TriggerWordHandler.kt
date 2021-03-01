package messaging.handlers.text

import messaging.Formatter
import messaging.handlers.TelegramHandler
import org.apache.logging.log4j.LogManager
import org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random

class TriggerWordHandler(
    private val triggerWords: Set<String>,
    private val responses: List<String>,
    private val random: Random,
    private val messenger: AbsSender,
    private val formatter: Formatter<String>
): TelegramHandler {
    private val logger = LogManager.getLogger(TriggerWordHandler::class.java)

    override fun handle(update: Update) {
        val words = update.message.text.toLowerCase().split(" ")
        words.find { triggerWords.contains(it) }?.also {
            logger.info("Triggered by word: '$it', user: ${update.message.from.userName}")
            val message = SendMessage.builder()
                .chatId("${update.message.chatId}")
                .text(formatter.format(responses[random.nextInt(0, responses.size)].replaceFirst("<user>", update.message.from.firstName)))
                .parseMode(MARKDOWNV2)
                .build()
            messenger.execute(message)
        }
    }
}