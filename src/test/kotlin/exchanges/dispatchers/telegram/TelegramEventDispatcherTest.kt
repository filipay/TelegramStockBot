package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class TelegramEventDispatcherTest {
    private val eventDispatcher1: ConditionalEventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val eventDispatcher2: ConditionalEventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val listener = TelegramEventDispatcher(listOf(eventDispatcher1, eventDispatcher2))

    @Test
    fun `should call event1 but not event2`() {
        every { eventDispatcher1.accept(any()) } returns true
        every { eventDispatcher2.accept(any()) } returns false

        listener.onEvent(TickerEvent(mockk(), mockk()))

        verify(exactly = 1) { eventDispatcher1.onEvent(any()) }
        verify(exactly = 0) { eventDispatcher2.onEvent(any()) }
    }
}