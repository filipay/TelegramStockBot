package messaging.handlers.commands

import messaging.handlers.TelegramHandler
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import stock.adapter.YahooFinanceAdapter

class StockCommandTelegramHandler(private val yahooFinanceAdapter: YahooFinanceAdapter): TelegramHandler {
    override fun handle(update: Update, bot: TelegramLongPollingBot) {
        val (command, stockName) = update.message.text.split("\\s".toRegex())
        if (command == "/stock") {
            val stock = yahooFinanceAdapter.stock(stockName)
            val message = SendMessage.builder()
                .chatId(update.message.chatId.toString())
                .text("${stock.name}: ${stock.quote}")
                .build()
            bot.execute(message)
        }
    }
}