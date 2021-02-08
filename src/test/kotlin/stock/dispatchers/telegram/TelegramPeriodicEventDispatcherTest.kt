package stock.dispatchers.telegram

import io.mockk.every
import io.mockk.mockk
import messaging.TelegramBotMessenger
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import stock.processor.StockEvent
import yahoofinance.Stock
import java.time.Duration
import java.time.Instant

internal class TelegramPeriodicEventDispatcherTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val messenger: TelegramBotMessenger = mockk(relaxed = true)
    private val period: Duration = mockk(relaxed = true)
    private val stock: Stock = mockk(relaxed = true) {
        every { name } returns "GME"
    }
    private val telegramPeriodicEventDispatcher = TelegramPeriodicEventDispatcher(instant, period, messenger)

    @Test
    fun `should accept if the event is outside period`() {
        every { period.toMillis() } returns 5
        assertTrue(telegramPeriodicEventDispatcher.accept(StockEvent(stock, Instant.ofEpochMilli(10))))
    }

    @Test
    fun `should reject if the event is inside period`() {
        every { period.toMillis() } returns 15
        assertFalse(telegramPeriodicEventDispatcher.accept(StockEvent(stock , Instant.ofEpochMilli(10))))
    }
}