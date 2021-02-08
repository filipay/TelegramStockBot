package stock.dispatchers.telegram

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import stock.processor.StockEvent

internal class TelegramEventDispatcherTest {
    private val eventDispatcher1: ConditionalEventDispatcher<StockEvent> = mockk(relaxed = true)
    private val eventDispatcher2: ConditionalEventDispatcher<StockEvent> = mockk(relaxed = true)
    private val listener = TelegramEventDispatcher(listOf(eventDispatcher1, eventDispatcher2))

    @Test
    fun `should call event1 but not event2`() {
        every { eventDispatcher1.accept(any()) } returns true
        every { eventDispatcher2.accept(any()) } returns false

        listener.onEvent(StockEvent(mockk(), mockk()))

        verify(exactly = 1) { eventDispatcher1.onEvent(any()) }
        verify(exactly = 0) { eventDispatcher2.onEvent(any()) }
    }
}