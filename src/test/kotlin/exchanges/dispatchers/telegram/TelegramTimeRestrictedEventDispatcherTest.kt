package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

internal class TelegramTimeRestrictedEventDispatcherTest {
    private val delegate: ConditionalEventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val telegramTimeRestrictedEventDispatcher = TelegramTimeRestrictedEventDispatcher(14..21, delegate)

    @Test
    fun `should accept if within day time trading hours and telegram event returns true`() {
        val now = Instant.ofEpochMilli(Duration.ofHours(14).toMillis())
        every { delegate.accept(any()) } returns true
        assertTrue(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), now)))
    }

    @Test
    fun `should reject if within day time trading hours and telegram event returns false`() {
        val now = Instant.ofEpochMilli(Duration.ofHours(14).toMillis())
        every { delegate.accept(any()) } returns true
        assertTrue(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), now)))
    }


    @Test
    fun `should reject if outside of day time trading hours but telegram event returns true`() {
        val now = Instant.ofEpochMilli(Duration.ofHours(23).toMillis())
        every { delegate.accept(any()) } returns true
        assertFalse(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), now)))
    }

    @Test
    fun `should reject if outside of day time trading hours but telegram event returns false`() {
        val now = Instant.ofEpochMilli(Duration.ofHours(11).toMillis())
        every { delegate.accept(any()) } returns true
        assertFalse(telegramTimeRestrictedEventDispatcher.accept(TickerEvent(mockk(), now)))
    }
}