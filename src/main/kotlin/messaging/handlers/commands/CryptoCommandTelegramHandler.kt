package messaging.handlers.commands

import exchanges.Ticker
import exchanges.adapter.KrakenExchangeAdapter
import messaging.Formatter
import messaging.handlers.ConditionalTelegramHandler
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class CryptoCommandTelegramHandler(
    private val messenger: AbsSender,
    private val krakenExchangeAdapter: KrakenExchangeAdapter,
    private val formatter: Formatter<Ticker>
): ConditionalTelegramHandler {
    override fun handle(update: Update) {
        val (_, cryptoSymbol) = update.message.text.split("\\s".toRegex())
        val ticker = krakenExchangeAdapter.ticker(cryptoSymbol)
        val message = SendMessage.builder()
            .chatId(update.message.chatId.toString())
            .text(formatter.format(ticker))
            .parseMode(ParseMode.MARKDOWNV2)
            .build()
        messenger.execute(message)
    }
}
