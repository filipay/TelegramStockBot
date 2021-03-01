package exchanges.processor

import exchanges.Event

interface Processor {
    fun process(event: Event)
}