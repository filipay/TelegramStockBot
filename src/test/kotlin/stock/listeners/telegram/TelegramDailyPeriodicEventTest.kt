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

internal class TelegramDailyPeriodicEventTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val telegramEvent: TelegramEvent<StockEvent> = mockk {
        every { accept(any()) } returns true
    }
    private val bot: TelegramLongPollingBot = mockk(relaxed = true)
    private val config: Config = mockk(relaxed = true)
    private val period: Duration = mockk(relaxed = true)
    private val telegramDailySummaryEvent = TelegramDailyPeriodicEvent(instant, period, bot, config, telegramEvent)

    @Test
    fun `should accept if the event is outside period`() {
        every { period.toMillis() } returns 5
        assertTrue(telegramDailySummaryEvent.accept(StockEvent(mockk(), Instant.ofEpochMilli(10))))
    }

    @Test
    fun `should reject if the event is inside period`() {
        every { period.toMillis() } returns 15
        assertFalse(telegramDailySummaryEvent.accept(StockEvent(mockk(), Instant.ofEpochMilli(10))))
    }
}