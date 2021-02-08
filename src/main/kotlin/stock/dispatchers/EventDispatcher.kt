package stock.dispatchers

interface EventDispatcher<T> {
    fun onEvent(event: T)
}