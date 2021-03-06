package exchanges.dispatchers.telegram

import exchanges.Ticker
import exchanges.TickerEvent
import io.mockk.every
import io.mockk.mockk
import messaging.Formatter
import messaging.TelegramBotMessenger
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

internal class TelegramPeriodicEventDispatcherTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val messenger: TelegramBotMessenger = mockk(relaxed = true)
    private val period: Duration = mockk(relaxed = true)
    private val ticker: Ticker = mockk(relaxed = true)
    private val formatter: Formatter<Ticker> = mockk(relaxed = true)
    private val telegramPeriodicEventDispatcher = TelegramPeriodicEventDispatcher(instant, period, messenger, formatter)

    @Test
    fun `should accept if the event is outside period`() {
        every { period.toMillis() } returns 5
        assertTrue(telegramPeriodicEventDispatcher.accept(TickerEvent(ticker, Instant.ofEpochMilli(10))))
    }

    @Test
    fun `should reject if the event is inside period`() {
        every { period.toMillis() } returns 15
        assertFalse(telegramPeriodicEventDispatcher.accept(TickerEvent(ticker , Instant.ofEpochMilli(10))))
    }
}