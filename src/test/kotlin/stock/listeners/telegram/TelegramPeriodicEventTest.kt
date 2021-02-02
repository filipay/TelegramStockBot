package stock.listeners.telegram

import configuration.Config
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

internal class TelegramPeriodicEventTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val bot: TelegramLongPollingBot = mockk(relaxed = true)
    private val config: Config = mockk(relaxed = true)
    private val period: Duration = mockk(relaxed = true)
    private val telegramPeriodicEvent = TelegramPeriodicEvent(instant, period, bot, config)

    @Test
    fun `should accept if the event is outside period`() {
        every { period.toMillis() } returns 5
        assertTrue(telegramPeriodicEvent.accept(StockEvent(mockk(), Instant.ofEpochMilli(10))))
    }

    @Test
    fun `should reject if the event is inside period`() {
        every { period.toMillis() } returns 15
        assertFalse(telegramPeriodicEvent.accept(StockEvent(mockk(), Instant.ofEpochMilli(10))))
    }
}