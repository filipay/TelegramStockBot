package exchanges.processor

import exchanges.Event

interface Processor {
    suspend fun process(event: Event)
}