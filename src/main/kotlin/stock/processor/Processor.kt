package stock.processor

interface Processor {
    fun process(event: Event)
}