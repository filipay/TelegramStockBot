package messaging.handlers.commands

import exchanges.Ticker
import exchanges.adapter.YahooFinanceAdapter
import messaging.Formatter
import messaging.handlers.ConditionalTelegramHandler
import org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class StockCommandTelegramHandler(
    private val messenger: AbsSender,
    private val yahooFinanceAdapter: YahooFinanceAdapter,
    private val formatter: Formatter<Ticker>
): ConditionalTelegramHandler {
    override fun handle(update: Update) {
        val (_, stockName) = update.message.text.split("\\s".toRegex())
        val ticker = yahooFinanceAdapter.ticker(stockName)
        val message = SendMessage.builder()
            .chatId(update.message.chatId.toString())
            .text(formatter.format(ticker))
            .parseMode(MARKDOWNV2)
            .build()
        messenger.execute(message)
    }
}