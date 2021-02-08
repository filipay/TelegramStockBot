package messaging.handlers.commands

import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import stock.adapter.YahooFinanceAdapter

class StockCommandTelegramHandler(
    private val messenger: AbsSender,
    private val yahooFinanceAdapter: YahooFinanceAdapter
    ): TelegramHandler {
    override fun handle(update: Update) {
        val (_, stockName) = update.message.text.split("\\s".toRegex())
        val stock = yahooFinanceAdapter.stock(stockName)
        val message = SendMessage.builder()
            .chatId(update.message.chatId.toString())
            .text("${stock.name}: ${stock.quote}")
            .build()
        messenger.execute(message)

    }
}