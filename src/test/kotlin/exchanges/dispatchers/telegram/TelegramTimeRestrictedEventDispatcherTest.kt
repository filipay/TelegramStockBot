package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Calendar

internal class TelegramTimeRestrictedEventDispatcherTest {
    private val calendar: Calendar = mockk(relaxed = true)
    private val delegate: ConditionalEventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val telegramTimeRestrictedEventDispatcher = TelegramTimeRestrictedEventDispatcher(calendar, 14..21, delegate)

    @Test
    fun `should accept if within day time trading hours and telegram event returns true`() {
        every { calendar.get(any()) } returns 14
        every { delegate.accept(any()) } returns true
        assertTrue(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), mockk())))
    }

    @Test
    fun `should reject if within day time trading hours and telegram event returns false`() {
        every { calendar.get(any()) } returns 14
        every { delegate.accept(any()) } returns true
        assertTrue(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), mockk())))
    }


    @Test
    fun `should reject if outside of day time trading hours but telegram event returns true`() {
        every { delegate.accept(any()) } returns true
        every { calendar.get(any()) } returns 11
        assertFalse(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), mockk())))
    }

    @Test
    fun `should reject if outside of day time trading hours but telegram event returns false`() {
        every { delegate.accept(any()) } returns true
        every { calendar.get(any()) } returns 11
        assertFalse(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), mockk())))
    }
}