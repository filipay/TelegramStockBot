package stock.listeners.telegram

import configuration.Config
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import stock.processor.StockEvent
import yahoofinance.Stock
import java.time.Duration
import java.time.Instant

internal class TelegramPeriodicEventTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val bot: TelegramLongPollingBot = mockk(relaxed = true)
    private val config: Config = mockk(relaxed = true)
    private val period: Duration = mockk(relaxed = true)
    private val stock: Stock = mockk(relaxed = true) {
        every { name } returns "GME"
    }
    private val telegramPeriodicEvent = TelegramPeriodicEvent(instant, period, bot, config)

    @Test
    fun `should accept if the event is outside period`() {
        every { period.toMillis() } returns 5
        assertTrue(telegramPeriodicEvent.accept(StockEvent(stock, Instant.ofEpochMilli(10))))
    }

    @Test
    fun `should reject if the event is inside period`() {
        every { period.toMillis() } returns 15
        assertFalse(telegramPeriodicEvent.accept(StockEvent(stock , Instant.ofEpochMilli(10))))
    }
}