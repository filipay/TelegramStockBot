package stock.listeners.telegram

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

internal class TelegramDailySummaryEventTest {
    private val instant: Instant = Instant.ofEpochMilli(0)
    private val period: Duration = mockk(relaxed = true)
    private val telegramDailySummaryEvent = TelegramDailySummaryEvent(instant, period)

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