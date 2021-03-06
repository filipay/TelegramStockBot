package messaging.handlers.commands

import App
import messaging.handlers.ConditionalTelegramHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender


class StopStartCommandTelegramHandler(
    private val app: App,
    private val messenger: AbsSender
): ConditionalTelegramHandler {
    override fun handle(update: Update) {
        when(update.message.text.split("\\s".toRegex()).first()) {
            "/start" -> {
                if (!app.isRunning()) {
                    messenger.execute(SendMessage(update.message.chatId.toString(), "Starting polling task..."))
                    app.run()
                } else {
                    messenger.execute(SendMessage(update.message.chatId.toString(), "Polling task is already running!"))
                }
            }
            "/stop" -> {
                messenger.execute(SendMessage(update.message.chatId.toString(), "Stopping polling task..."))
                app.stop()
            }
        }
    }
}