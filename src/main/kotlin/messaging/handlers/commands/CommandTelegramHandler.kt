package messaging.handlers.commands

import ifTrue
import messaging.handlers.ConditionalTelegramHandler
import org.apache.logging.log4j.LogManager
import org.telegram.telegrambots.meta.api.objects.Update

class CommandTelegramHandler(private val handlerStrategy: Map<String, ConditionalTelegramHandler>): ConditionalTelegramHandler {
    private val logger = LogManager.getLogger(CommandTelegramHandler::class.java)
    override fun accept(update: Update): Boolean = update.message.isCommand

    override fun handle(update: Update) {
        val command = update.message.text.split("\\s".toRegex()).first()
        logger.info("Received command: $command")
        runCatching { handlerStrategy[command]?.let { it.accept(update).ifTrue { it.handle(update) } } }
            .onFailure { logger.error("Failed to execute command: $command", it) }
    }
}