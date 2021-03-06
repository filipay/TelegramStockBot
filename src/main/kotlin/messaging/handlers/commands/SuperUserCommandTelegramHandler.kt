package messaging.handlers.commands

import ifFalse
import ifTrue
import messaging.handlers.ConditionalTelegramHandler
import org.apache.logging.log4j.LogManager
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class SuperUserCommandTelegramHandler(
    private val delegate: ConditionalTelegramHandler,
    private val superUsers: Set<Int>,
    private val messenger: AbsSender
): ConditionalTelegramHandler by delegate {
    private val logger = LogManager.getLogger(SuperUserCommandTelegramHandler::class.java)

    override fun accept(update: Update): Boolean {
        val user = update.message.from.userName
        return superUsers.contains(update.message.from.id).ifTrue {
            logger.info("ACCEPTED: user '$user' is a super user")
        }.ifFalse {
            logger.info("DENIED: user '$user' is not a super user")
            val message = SendMessage.builder()
                .replyToMessageId(update.message.messageId)
                .chatId(update.message.chatId.toString())
                .text("You do not have permissions to access this command!")
                .build()
            messenger.execute(message)
        } && delegate.accept(update)
    }
}