package stock.listeners

interface EventListener<T> {
    fun onEvent(event: T)
}