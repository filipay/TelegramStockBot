package stock.listeners.telegram

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import stock.processor.StockEvent

internal class TelegramEventListenerTest {
    private val event1: TelegramEvent<StockEvent> = mockk(relaxed = true)
    private val event2: TelegramEvent<StockEvent> = mockk(relaxed = true)
    private val listener = TelegramEventListener(listOf(event1, event2))

    @Test
    fun `should call event1 but not event2`() {
        every { event1.accept(any()) } returns true
        every { event2.accept(any()) } returns false

        listener.onEvent(StockEvent(mockk(), mockk()))

        verify(exactly = 1) { event1.onEvent(any()) }
        verify(exactly = 0) { event2.onEvent(any()) }
    }
}